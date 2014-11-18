/*
 * This file is part of the openSCADA project
 * Copyright (C) 2006-2010 TH4 SYSTEMS GmbH (http://th4-systems.com)
 * Copyright (C) 2014 IBH SYSTEMS GmbH (http://ibh-systems.com)
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openscada.opc.lib.da;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.jinterop.dcom.common.JIException;
import org.openscada.opc.dcom.common.EventHandler;
import org.openscada.opc.dcom.common.KeyedResult;
import org.openscada.opc.dcom.common.KeyedResultSet;
import org.openscada.opc.dcom.common.Result;
import org.openscada.opc.dcom.common.ResultSet;
import org.openscada.opc.dcom.da.IOPCDataCallback;
import org.openscada.opc.dcom.da.OPCDATASOURCE;
import org.openscada.opc.dcom.da.OPCITEMDEF;
import org.openscada.opc.dcom.da.OPCITEMRESULT;
import org.openscada.opc.dcom.da.OPCITEMSTATE;
import org.openscada.opc.dcom.da.impl.OPCAsyncIO2;
import org.openscada.opc.dcom.da.impl.OPCGroupStateMgt;
import org.openscada.opc.dcom.da.impl.OPCItemMgt;
import org.openscada.opc.dcom.da.impl.OPCSyncIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Group
{
    private static Logger _log = LoggerFactory.getLogger ( Group.class );

    private static Random _random = new Random ();

    private Server _server = null;

    private final int _serverHandle;

    private OPCGroupStateMgt _group = null;

    private OPCItemMgt _items = null;

    private OPCSyncIO _syncIO = null;

    private final Map<String, Integer> _itemHandleMap = new HashMap<String, Integer> ();

    private final Map<Integer, Item> _itemMap = new HashMap<Integer, Item> ();

    private final Map<Integer, Item> _itemClientMap = new HashMap<Integer, Item> ();

    Group ( final Server server, final int serverHandle, final OPCGroupStateMgt group ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        _log.debug ( "Creating new group instance with COM group " + group );
        this._server = server;
        this._serverHandle = serverHandle;
        this._group = group;
        this._items = group.getItemManagement ();
        this._syncIO = group.getSyncIO ();
    }

    public void setActive ( final boolean state ) throws JIException
    {
        this._group.setState ( null, state, null, null, null, null );
    }

    public void setActive ( final boolean state, final int period ) throws JIException
    {
        this._group.setState ( period, state, null, null, null, null );
    }

    /**
     * remove the group from the server
     *
     * @throws JIException
     */
    public void remove () throws JIException
    {
        this._server.removeGroup ( this, true );
    }

    public boolean isActive () throws JIException
    {
        return this._group.getState ().isActive ();
    }

    /**
     * Get the group name from the server
     *
     * @return The group name fetched from the server
     * @throws JIException
     */
    public String getName () throws JIException
    {
        return this._group.getState ().getName ();
    }

    /**
     * Change the group name
     *
     * @param name
     *            the new name of the group
     * @throws JIException
     */
    public void setName ( final String name ) throws JIException
    {
        this._group.setName ( name );
    }

    /**
     * Add a single item. Actually calls {@link #addItems(String[])} with only
     * one paraemter
     *
     * @param item
     *            The item to add
     * @return The added item
     * @throws JIException
     *             The add operation failed
     * @throws AddFailedException
     *             The item was not added due to an error
     */
    public Item addItem ( final String item ) throws JIException, AddFailedException
    {
        final Map<String, Item> items = addItems ( item );
        return items.get ( item );
    }

    /**
     * Validate item ids and get additional information to them. <br>
     * According to the OPC specification you should first <em>validate</em> the
     * items and the <em>add</em> them. The spec also says that when a server
     * lets the item pass validation it must also let them pass the add
     * operation.
     *
     * @param items
     *            The items to validate
     * @return A result map of item id to result information (including error
     *         code).
     * @throws JIException
     */
    public synchronized Map<String, Result<OPCITEMRESULT>> validateItems ( final String... items ) throws JIException
    {
        final OPCITEMDEF[] defs = new OPCITEMDEF[items.length];
        for ( int i = 0; i < items.length; i++ )
        {
            defs[i] = new OPCITEMDEF ();
            defs[i].setItemID ( items[i] );
        }

        final KeyedResultSet<OPCITEMDEF, OPCITEMRESULT> result = this._items.validate ( defs );

        final Map<String, Result<OPCITEMRESULT>> resultMap = new HashMap<String, Result<OPCITEMRESULT>> ();
        for ( final KeyedResult<OPCITEMDEF, OPCITEMRESULT> resultEntry : result )
        {
            resultMap.put ( resultEntry.getKey ().getItemID (), new Result<OPCITEMRESULT> ( resultEntry.getValue (), resultEntry.getErrorCode () ) );
        }

        return resultMap;
    }

    /**
     * Add new items to the group
     *
     * @param items
     *            The items (by string id) to add
     * @return A result map of id to item object
     * @throws JIException
     *             The add operation completely failed. No item was added.
     * @throws AddFailedException
     *             If one or more item could not be added. Item without error
     *             where added.
     */
    public synchronized Map<String, Item> addItems ( final String... items ) throws JIException, AddFailedException
    {
        // Find which items we already have
        final Map<String, Integer> handles = findItems ( items );

        final List<Integer> foundItems = new ArrayList<Integer> ( items.length );
        final List<String> missingItems = new ArrayList<String> ();

        // separate missing items from the found ones
        for ( final Map.Entry<String, Integer> entry : handles.entrySet () )
        {
            if ( entry.getValue () == null )
            {
                missingItems.add ( entry.getKey () );
            }
            else
            {
                foundItems.add ( entry.getValue () );
            }
        }

        // now fetch missing items from OPC server
        final Set<Integer> newClientHandles = new HashSet<Integer> ();
        final OPCITEMDEF[] itemDef = new OPCITEMDEF[missingItems.size ()];
        for ( int i = 0; i < missingItems.size (); i++ )
        {
            final OPCITEMDEF def = new OPCITEMDEF ();
            def.setItemID ( missingItems.get ( i ) );
            def.setActive ( true );

            Integer clientHandle;
            do
            {
                clientHandle = _random.nextInt ();
            } while ( this._itemClientMap.containsKey ( clientHandle ) || newClientHandles.contains ( clientHandle ) );
            newClientHandles.add ( clientHandle );
            def.setClientHandle ( clientHandle );

            itemDef[i] = def;
        }

        // check the result and add new items
        final Map<String, Integer> failedItems = new HashMap<String, Integer> ();
        final KeyedResultSet<OPCITEMDEF, OPCITEMRESULT> result = this._items.add ( itemDef );
        int i = 0;
        for ( final KeyedResult<OPCITEMDEF, OPCITEMRESULT> entry : result )
        {
            if ( entry.getErrorCode () == 0 )
            {
                final Item item = new Item ( this, entry.getValue ().getServerHandle (), itemDef[i].getClientHandle (), entry.getKey ().getItemID () );
                addItem ( item );
                foundItems.add ( item.getServerHandle () );
            }
            else
            {
                failedItems.put ( entry.getKey ().getItemID (), entry.getErrorCode () );
            }
            i++;
        }

        // if we have failed items then throw an exception with the result
        if ( failedItems.size () != 0 )
        {
            throw new AddFailedException ( failedItems, findItems ( foundItems ) );
        }

        // simply return the result in case of success
        return findItems ( foundItems );
    }

    private synchronized void addItem ( final Item item )
    {
        _log.debug ( String.format ( "Adding item: '%s', %d", item.getId (), item.getServerHandle () ) );

        this._itemHandleMap.put ( item.getId (), item.getServerHandle () );
        this._itemMap.put ( item.getServerHandle (), item );
        this._itemClientMap.put ( item.getClientHandle (), item );
    }

    private synchronized void removeItem ( final Item item )
    {
        this._itemHandleMap.remove ( item.getId () );
        this._itemMap.remove ( item.getServerHandle () );
        this._itemClientMap.remove ( item.getClientHandle () );
    }

    protected Item getItemByOPCItemId ( final String opcItemId )
    {
        final Integer serverHandle = this._itemHandleMap.get ( opcItemId );
        if ( serverHandle == null )
        {
            _log.debug ( String.format ( "Failed to locate item with id '%s'", opcItemId ) );
            return null;
        }
        _log.debug ( String.format ( "Item '%s' has server id '%d'", opcItemId, serverHandle ) );
        return this._itemMap.get ( serverHandle );
    }

    private synchronized Map<String, Integer> findItems ( final String[] items )
    {
        final Map<String, Integer> data = new HashMap<String, Integer> ();

        for ( int i = 0; i < items.length; i++ )
        {
            data.put ( items[i], this._itemHandleMap.get ( items[i] ) );
        }

        return data;
    }

    private synchronized Map<String, Item> findItems ( final Collection<Integer> handles )
    {
        final Map<String, Item> itemMap = new HashMap<String, Item> ();
        for ( final Integer i : handles )
        {
            final Item item = this._itemMap.get ( i );
            if ( item != null )
            {
                itemMap.put ( item.getId (), item );
            }
        }
        return itemMap;
    }

    protected void checkItems ( final Item[] items )
    {
        for ( final Item item : items )
        {
            if ( item.getGroup () != this )
            {
                throw new IllegalArgumentException ( "Item does not belong to this group" );
            }
        }
    }

    public void setActive ( final boolean state, final Item... items ) throws JIException
    {
        checkItems ( items );

        final Integer[] handles = new Integer[items.length];
        for ( int i = 0; i < items.length; i++ )
        {
            handles[i] = items[i].getServerHandle ();
        }

        this._items.setActiveState ( state, handles );
    }

    protected Integer[] getServerHandles ( final Item[] items )
    {
        checkItems ( items );

        final Integer[] handles = new Integer[items.length];

        for ( int i = 0; i < items.length; i++ )
        {
            handles[i] = items[i].getServerHandle ();
        }

        return handles;
    }

    public synchronized Map<Item, Integer> write ( final WriteRequest... requests ) throws JIException
    {
        final Item[] items = new Item[requests.length];

        for ( int i = 0; i < requests.length; i++ )
        {
            items[i] = requests[i].getItem ();
        }

        final Integer[] handles = getServerHandles ( items );

        final org.openscada.opc.dcom.da.WriteRequest[] wr = new org.openscada.opc.dcom.da.WriteRequest[items.length];
        for ( int i = 0; i < items.length; i++ )
        {
            wr[i] = new org.openscada.opc.dcom.da.WriteRequest ( handles[i], requests[i].getValue () );
        }

        final ResultSet<org.openscada.opc.dcom.da.WriteRequest> resultSet = this._syncIO.write ( wr );

        final Map<Item, Integer> result = new HashMap<Item, Integer> ();
        for ( int i = 0; i < requests.length; i++ )
        {
            final Result<org.openscada.opc.dcom.da.WriteRequest> entry = resultSet.get ( i );
            result.put ( requests[i].getItem (), entry.getErrorCode () );
        }

        return result;
    }

    public synchronized Map<Item, ItemState> read ( final boolean device, final Item... items ) throws JIException
    {
        final Integer[] handles = getServerHandles ( items );

        final KeyedResultSet<Integer, OPCITEMSTATE> states = this._syncIO.read ( device ? OPCDATASOURCE.OPC_DS_DEVICE : OPCDATASOURCE.OPC_DS_CACHE, handles );

        final Map<Item, ItemState> data = new HashMap<Item, ItemState> ();
        for ( final KeyedResult<Integer, OPCITEMSTATE> entry : states )
        {
            final Item item = this._itemMap.get ( entry.getKey () );
            final ItemState state = new ItemState ( entry.getErrorCode (), entry.getValue ().getValue (), entry.getValue ().getTimestamp ().asCalendar (), entry.getValue ().getQuality () );
            data.put ( item, state );
        }
        return data;
    }

    public Server getServer ()
    {
        return this._server;
    }

    public synchronized void clear () throws JIException
    {
        final Integer[] handles = this._itemMap.keySet ().toArray ( new Integer[0] );
        try
        {
            this._items.remove ( handles );
        }
        finally
        {
            // in any case clear our maps
            this._itemHandleMap.clear ();
            this._itemMap.clear ();
            this._itemClientMap.clear ();
        }
    }

    public synchronized OPCAsyncIO2 getAsyncIO20 ()
    {
        return this._group.getAsyncIO2 ();
    }

    /**
     * Set part of the group state
     * <p>
     * Each of the parameters may be <code>null</code> in which the parameter is
     * not changed.
     * </p>
     *
     * @param requestedUpdateRate
     *            the requested update rate. <em>Note:</em> that the server may
     *            return a different update rate.
     * @param timeBias
     *            the new time bias value
     * @param percentDeadband
     *            the new percent deadband value
     * @throws JIException
     *             if anything in the OPC communication or on the server goes
     *             wrong
     * @since 1.3.0
     */
    public void setGroupState ( final Integer requestedUpdateRate, final Integer timeBias, final Float percentDeadband ) throws JIException
    {
        this._group.setState ( requestedUpdateRate, null, timeBias, percentDeadband, null, null );
    }

    public synchronized EventHandler attach ( final IOPCDataCallback dataCallback ) throws JIException
    {
        return this._group.attach ( dataCallback );
    }

    public Item findItemByClientHandle ( final int clientHandle )
    {
        return this._itemClientMap.get ( clientHandle );
    }

    public int getServerHandle ()
    {
        return this._serverHandle;
    }

    public synchronized void removeItem ( final String opcItemId ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        _log.debug ( String.format ( "Removing item '%s'", opcItemId ) );
        final Item item = getItemByOPCItemId ( opcItemId );
        if ( item != null )
        {
            this._group.getItemManagement ().remove ( item.getServerHandle () );
            removeItem ( item );
            _log.debug ( String.format ( "Removed item '%s'", opcItemId ) );
        }
        else
        {
            _log.warn ( String.format ( "Unable to find item '%s'", opcItemId ) );
        }
    }

}
