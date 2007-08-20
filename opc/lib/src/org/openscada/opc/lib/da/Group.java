/*
 * This file is part of the OpenSCADA project
 * Copyright (C) 2006-2007 inavare GmbH (http://inavare.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
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

import org.apache.log4j.Logger;
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

public class Group
{
    private static Logger _log = Logger.getLogger ( Group.class );

    private static Random _random = new Random ();

    private Server _server = null;

    private int _serverHandle;
    
    private OPCGroupStateMgt _group = null;

    private OPCItemMgt _items = null;

    private OPCSyncIO _syncIO = null;

    private Map<String, Integer> _itemHandleMap = new HashMap<String, Integer> ();

    private Map<Integer, Item> _itemMap = new HashMap<Integer, Item> ();

    private Map<Integer, Item> _itemClientMap = new HashMap<Integer, Item> ();

    Group ( Server server, int serverHandle, OPCGroupStateMgt group ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        _log.debug ( "Creating new group instance with COM group " + group );
        _server = server;
        _serverHandle = serverHandle;
        _group = group;
        _items = group.getItemManagement ();
        _syncIO = group.getSyncIO ();
    }

    public void setActive ( boolean state ) throws JIException
    {
        _group.setState ( null, state, null, null, null, null );
    }

    /**
     * remove the group from the server
     * @throws JIException 
     *
     */
    public void remove () throws JIException
    {
        _server.removeGroup ( this, true );
    }
    
    public boolean isActive () throws JIException
    {
        return _group.getState ().isActive ();
    }

    /**
     * Get the group name from the server
     * @return The group name fetched from the server
     * @throws JIException
     */
    public String getName () throws JIException
    {
        return _group.getState ().getName ();
    }

    /**
     * Change the group name
     * @param name the new name of the group
     * @throws JIException
     */
    public void setName ( String name ) throws JIException
    {
        _group.setName ( name );
    }

    /**
     * Add a single item. Actually calls {@link #addItems(String[])} with only
     * one paraemter
     * @param item The item to add
     * @return The added item
     * @throws JIException The add operation failed
     * @throws AddFailedException The item was not added due to an error
     */
    public Item addItem ( String item ) throws JIException, AddFailedException
    {
        Map<String, Item> items = addItems ( item );
        return items.get ( item );
    }

    /**
     * Validate item ids and get additional information to them.
     * <br>
     * According to the OPC specification you should first <em>validate</em>
     * the items and the <em>add</em> them. The spec also says that when a server
     * lets the item pass validation it must also let them pass the add operation.
     * @param items The items to validate
     * @return A result map of item id to result information (including error code).
     * @throws JIException
     */
    public synchronized Map<String, Result<OPCITEMRESULT>> validateItems ( String... items ) throws JIException
    {
        OPCITEMDEF[] defs = new OPCITEMDEF[items.length];
        for ( int i = 0; i < items.length; i++ )
        {
            defs[i] = new OPCITEMDEF ();
            defs[i].setItemID ( items[i] );
        }

        KeyedResultSet<OPCITEMDEF, OPCITEMRESULT> result = _items.validate ( defs );

        Map<String, Result<OPCITEMRESULT>> resultMap = new HashMap<String, Result<OPCITEMRESULT>> ();
        for ( KeyedResult<OPCITEMDEF, OPCITEMRESULT> resultEntry : result )
        {
            resultMap.put ( resultEntry.getKey ().getItemID (), new Result<OPCITEMRESULT> ( resultEntry.getValue (),
                    resultEntry.getErrorCode () ) );
        }

        return resultMap;
    }

    /**
     * Add new items to the group
     * @param items The items (by string id) to add
     * @return A result map of id to item object
     * @throws JIException The add operation completely failed. No item was added.
     * @throws AddFailedException If one or more item could not be added. Item without error where added.
     */
    public synchronized Map<String, Item> addItems ( String... items ) throws JIException, AddFailedException
    {
        // Find which items we already have
        Map<String, Integer> handles = findItems ( items );

        List<Integer> foundItems = new ArrayList<Integer> ( items.length );
        List<String> missingItems = new ArrayList<String> ();

        // separate missing items from the found ones
        for ( Map.Entry<String, Integer> entry : handles.entrySet () )
        {
            if ( entry.getValue () == null )
                missingItems.add ( entry.getKey () );
            else
                foundItems.add ( entry.getValue () );
        }

        // now fetch missing items from OPC server
        Set<Integer> newClientHandles = new HashSet<Integer> ();
        OPCITEMDEF[] itemDef = new OPCITEMDEF[missingItems.size ()];
        for ( int i = 0; i < missingItems.size (); i++ )
        {
            OPCITEMDEF def = new OPCITEMDEF ();
            def.setItemID ( missingItems.get ( i ) );
            def.setActive ( true );

            Integer clientHandle;
            do
            {
                clientHandle = _random.nextInt ();
            } while ( _itemClientMap.containsKey ( clientHandle ) || newClientHandles.contains ( clientHandle ) );
            newClientHandles.add ( clientHandle );
            def.setClientHandle ( clientHandle );

            itemDef[i] = def;
        }

        // check the result and add new items
        Map<String, Integer> failedItems = new HashMap<String, Integer> ();
        KeyedResultSet<OPCITEMDEF, OPCITEMRESULT> result = _items.add ( itemDef );
        int i = 0;
        for ( KeyedResult<OPCITEMDEF, OPCITEMRESULT> entry : result )
        {
            if ( entry.getErrorCode () == 0 )
            {
                Item item = new Item ( this, entry.getValue ().getServerHandle (), itemDef[i].getClientHandle (),
                        entry.getKey ().getItemID () );
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

    private synchronized void addItem ( Item item )
    {
        _log.debug ( String.format ( "Adding item: '%s', %d", item.getId (), item.getServerHandle () ) );
        
        _itemHandleMap.put ( item.getId (), item.getServerHandle () );
        _itemMap.put ( item.getServerHandle (), item );
        _itemClientMap.put ( item.getClientHandle (), item );
    }

    private synchronized void removeItem ( Item item )
    {
        _itemHandleMap.remove ( item.getId () );
        _itemMap.remove ( item.getServerHandle () );
        _itemClientMap.remove ( item.getClientHandle () );
    }
    
    protected Item getItemByOPCItemId ( String opcItemId )
    {
        Integer serverHandle = _itemHandleMap.get ( opcItemId );
        if ( serverHandle == null )
        {
            _log.debug ( String.format ( "Failed to locate item with id '%s'", opcItemId ) );
            return null;
        }
        _log.debug ( String.format ( "Item '%s' has server id '%d'", opcItemId, serverHandle ) );
        return _itemMap.get ( serverHandle );
    }

    private synchronized Map<String, Integer> findItems ( String[] items )
    {
        Map<String, Integer> data = new HashMap<String, Integer> ();

        for ( int i = 0; i < items.length; i++ )
        {
            data.put ( items[i], _itemHandleMap.get ( items[i] ) );
        }

        return data;
    }

    private synchronized Map<String, Item> findItems ( Collection<Integer> handles )
    {
        Map<String, Item> itemMap = new HashMap<String, Item> ();
        for ( Integer i : handles )
        {
            Item item = _itemMap.get ( i );
            if ( item != null )
            {
                itemMap.put ( item.getId (), item );
            }
        }
        return itemMap;
    }

    protected void checkItems ( Item[] items )
    {
        for ( Item item : items )
        {
            if ( item.getGroup () != this )
                throw new IllegalArgumentException ( "Item does not belong to this group" );
        }
    }

    public void setActive ( boolean state, Item... items ) throws JIException
    {
        checkItems ( items );

        Integer[] handles = new Integer[items.length];
        for ( int i = 0; i < items.length; i++ )
        {
            handles[i] = items[i].getServerHandle ();
        }

        _items.setActiveState ( state, handles );
    }

    protected Integer[] getServerHandles ( Item[] items )
    {
        checkItems ( items );

        Integer[] handles = new Integer[items.length];

        for ( int i = 0; i < items.length; i++ )
        {
            handles[i] = items[i].getServerHandle ();
        }

        return handles;
    }

    public synchronized Map<Item, Integer> write ( WriteRequest... requests ) throws JIException
    {
        Item[] items = new Item[requests.length];

        for ( int i = 0; i < requests.length; i++ )
        {
            items[i] = requests[i].getItem ();
        }

        Integer[] handles = getServerHandles ( items );

        org.openscada.opc.dcom.da.WriteRequest[] wr = new org.openscada.opc.dcom.da.WriteRequest[items.length];
        for ( int i = 0; i < items.length; i++ )
        {
            wr[i] = new org.openscada.opc.dcom.da.WriteRequest ( handles[i], requests[i].getValue () );
        }

        ResultSet<org.openscada.opc.dcom.da.WriteRequest> resultSet = _syncIO.write ( wr );

        Map<Item, Integer> result = new HashMap<Item, Integer> ();
        for ( int i = 0; i < requests.length; i++ )
        {
            Result<org.openscada.opc.dcom.da.WriteRequest> entry = resultSet.get ( i );
            result.put ( requests[i].getItem (), entry.getErrorCode () );
        }

        return result;
    }

    public synchronized Map<Item, ItemState> read ( boolean device, Item... items ) throws JIException
    {
        Integer[] handles = getServerHandles ( items );

        KeyedResultSet<Integer, OPCITEMSTATE> states = _syncIO.read (
                device ? OPCDATASOURCE.OPC_DS_DEVICE : OPCDATASOURCE.OPC_DS_CACHE, handles );

        Map<Item, ItemState> data = new HashMap<Item, ItemState> ();
        for ( KeyedResult<Integer, OPCITEMSTATE> entry : states )
        {
            Item item = _itemMap.get ( entry.getKey () );
            ItemState state = new ItemState ( entry.getErrorCode (), entry.getValue ().getValue (),
                    entry.getValue ().getTimestamp ().asCalendar (), entry.getValue ().getQuality () );
            data.put ( item, state );
        }
        return data;
    }

    public Server getServer ()
    {
        return _server;
    }

    public synchronized void clear () throws JIException
    {
        Integer[] handles = _itemMap.keySet ().toArray ( new Integer[0] );
        try
        {
            _items.remove ( handles );
        }
        finally
        {
            // in any case clear our maps
            _itemHandleMap.clear ();
            _itemMap.clear ();
            _itemClientMap.clear ();
        }
    }

    public synchronized OPCAsyncIO2 getAsyncIO20 ()
    {
        return _group.getAsyncIO2 ();
    }

    public synchronized EventHandler attach ( IOPCDataCallback dataCallback ) throws JIException
    {
        return _group.attach ( dataCallback );
    }

    public Item findItemByClientHandle ( int clientHandle )
    {
        return _itemClientMap.get ( clientHandle );
    }
    
    public int getServerHandle ()
    {
        return _serverHandle;
    }

    public synchronized void removeItem ( String opcItemId ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        _log.debug ( String.format ( "Removing item '%s'", opcItemId ) );
        Item item = getItemByOPCItemId ( opcItemId );
        if ( item != null )
        {
            _group.getItemManagement ().remove ( item.getServerHandle () );
            removeItem ( item );
            _log.debug ( String.format ( "Removed item '%s'", opcItemId ) );
        }
        else
        {
            _log.warn ( String.format ( "Unable to find item '%s'", opcItemId ) );
        }
    }

}
