/*
 * This file is part of the OpenSCADA project
 * Copyright (C) 2006-2010 inavare GmbH (http://inavare.com)
 *
 * OpenSCADA is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License version 3
 * only, as published by the Free Software Foundation.
 *
 * OpenSCADA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License version 3 for more details
 * (a copy is included in the LICENSE file that accompanied this code).
 *
 * You should have received a copy of the GNU Lesser General Public License
 * version 3 along with OpenSCADA. If not, see
 * <http://opensource.org/licenses/lgpl-3.0.html> for a copy of the LGPLv3 License.
 */

package org.openscada.opc.lib.da;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.NotConnectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AccessBase implements ServerConnectionStateListener
{
    private static Logger _log = LoggerFactory.getLogger ( AccessBase.class );

    protected Server _server = null;

    protected Group _group = null;

    protected boolean _active = false;

    private final List<AccessStateListener> _stateListeners = new CopyOnWriteArrayList<AccessStateListener> ();

    private boolean _bound = false;

    /**
     * Holds the item to callback assignment
     */
    protected Map<Item, DataCallback> _items = new HashMap<Item, DataCallback> ();

    protected Map<String, Item> _itemMap = new HashMap<String, Item> ();

    protected Map<Item, ItemState> _itemCache = new HashMap<Item, ItemState> ();

    private int _period = 0;

    protected Map<String, DataCallback> _itemSet = new HashMap<String, DataCallback> ();

    protected String _logTag = null;

    protected Logger _dataLogger = null;

    public AccessBase ( final Server server, final int period ) throws IllegalArgumentException, UnknownHostException, NotConnectedException, JIException, DuplicateGroupException
    {
        super ();
        this._server = server;
        this._period = period;
    }

    public AccessBase ( final Server server, final int period, final String logTag )
    {
        super ();
        this._server = server;
        this._period = period;
        this._logTag = logTag;
        if ( this._logTag != null )
        {
            this._dataLogger = LoggerFactory.getLogger ( "opc.data." + logTag );
        }
    }

    public boolean isBound ()
    {
        return this._bound;
    }

    public synchronized void bind ()
    {
        if ( isBound () )
        {
            return;
        }

        this._server.addStateListener ( this );
        this._bound = true;
    }

    public synchronized void unbind () throws JIException
    {
        if ( !isBound () )
        {
            return;
        }

        this._server.removeStateListener ( this );
        this._bound = false;

        stop ();
    }

    public boolean isActive ()
    {
        return this._active;
    }

    public void addStateListener ( final AccessStateListener listener )
    {
        this._stateListeners.add ( listener );
        listener.stateChanged ( isActive () );
    }

    public void removeStateListener ( final AccessStateListener listener )
    {
        this._stateListeners.remove ( listener );
    }

    protected void notifyStateListenersState ( final boolean state )
    {
        List<AccessStateListener> list = new ArrayList<AccessStateListener> ( this._stateListeners );

        for ( AccessStateListener listener : list )
        {
            listener.stateChanged ( state );
        }
    }

    protected void notifyStateListenersError ( final Throwable t )
    {
        List<AccessStateListener> list = new ArrayList<AccessStateListener> ( this._stateListeners );

        for ( AccessStateListener listener : list )
        {
            listener.errorOccured ( t );
        }
    }

    public int getPeriod ()
    {
        return this._period;
    }

    public synchronized void addItem ( final String itemId, final DataCallback dataCallback ) throws JIException, AddFailedException
    {
        if ( this._itemSet.containsKey ( itemId ) )
        {
            return;
        }

        this._itemSet.put ( itemId, dataCallback );

        if ( isActive () )
        {
            realizeItem ( itemId );
        }
    }

    public synchronized void removeItem ( final String itemId )
    {
        if ( !this._itemSet.containsKey ( itemId ) )
        {
            return;
        }

        this._itemSet.remove ( itemId );

        if ( isActive () )
        {
            unrealizeItem ( itemId );
        }
    }

    public void connectionStateChanged ( final boolean connected )
    {
        try
        {
            if ( connected )
            {
                start ();
            }
            else
            {
                stop ();
            }
        }
        catch ( Exception e )
        {
            _log.error ( String.format ( "Failed to change state (%s)", connected ), e );
        }
    }

    protected synchronized void start () throws JIException, IllegalArgumentException, UnknownHostException, NotConnectedException, DuplicateGroupException
    {
        if ( isActive () )
        {
            return;
        }

        _log.debug ( "Create a new group" );
        this._group = this._server.addGroup ();
        this._group.setActive ( true );
        this._active = true;

        notifyStateListenersState ( true );

        realizeAll ();
    }

    protected void realizeItem ( final String itemId ) throws JIException, AddFailedException
    {
        _log.debug ( String.format ( "Realizing item: %s", itemId ) );

        DataCallback dataCallback = this._itemSet.get ( itemId );
        if ( dataCallback == null )
        {
            return;
        }

        Item item = this._group.addItem ( itemId );
        this._items.put ( item, dataCallback );
        this._itemMap.put ( itemId, item );
    }

    protected void unrealizeItem ( final String itemId )
    {
        Item item = this._itemMap.remove ( itemId );
        this._items.remove ( item );
        this._itemCache.remove ( item );

        try
        {
            this._group.removeItem ( itemId );
        }
        catch ( Throwable e )
        {
            _log.error ( String.format ( "Failed to unrealize item '%s'", itemId ), e );
        }
    }

    /*
     * FIXME: need some perfomance boost: subscribe all in one call
     */
    protected void realizeAll ()
    {
        for ( String itemId : this._itemSet.keySet () )
        {
            try
            {
                realizeItem ( itemId );
            }
            catch ( AddFailedException e )
            {
                Integer rc = e.getErrors ().get ( itemId );
                if ( rc == null )
                {
                    rc = -1;
                }
                _log.warn ( String.format ( "Failed to add item: %s (%08X)", itemId, rc ) );

            }
            catch ( Exception e )
            {
                _log.warn ( "Failed to realize item: " + itemId, e );
            }
        }
    }

    protected void unrealizeAll ()
    {
        this._items.clear ();
        this._itemCache.clear ();
        try
        {
            this._group.clear ();
        }
        catch ( JIException e )
        {
            _log.info ( "Failed to clear group. No problem if we already lost the connection", e );
        }
    }

    protected synchronized void stop () throws JIException
    {
        if ( !isActive () )
        {
            return;
        }

        unrealizeAll ();

        this._active = false;
        notifyStateListenersState ( false );

        try
        {
            this._group.remove ();
        }
        catch ( Throwable t )
        {
            _log.warn ( "Failed to disable group. No problem if we already lost connection" );
        }
        this._group = null;
    }

    public synchronized void clear ()
    {
        this._itemSet.clear ();
        this._items.clear ();
        this._itemMap.clear ();
        this._itemCache.clear ();
    }

    protected void updateItem ( final Item item, final ItemState itemState )
    {
        if ( this._dataLogger != null )
        {
            this._dataLogger.debug ( String.format ( "Update item: %s, %s", item.getId (), itemState ) );
        }

        DataCallback dataCallback = this._items.get ( item );
        if ( dataCallback == null )
        {
            return;
        }

        ItemState cachedState = this._itemCache.get ( item );
        if ( cachedState == null )
        {
            this._itemCache.put ( item, itemState );
            dataCallback.changed ( item, itemState );
        }
        else
        {
            if ( !cachedState.equals ( itemState ) )
            {
                this._itemCache.put ( item, itemState );
                dataCallback.changed ( item, itemState );
            }
        }
    }

    protected void handleError ( final Throwable e )
    {
        notifyStateListenersError ( e );
        this._server.dispose ();
    }

}