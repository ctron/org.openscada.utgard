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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;
import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.NotConnectedException;

public abstract class AccessBase implements ServerConnectionStateListener
{
    private static Logger _log = Logger.getLogger ( AccessBase.class );

    protected Server _server = null;

    protected Group _group = null;

    protected boolean _active = false;

    private List<AccessStateListener> _stateListeners = new CopyOnWriteArrayList<AccessStateListener> ();

    private boolean _bound = false;

    /**
     * Holds the item to callback assignment
     */
    protected Map<Item, DataCallback> _items = new HashMap<Item, DataCallback> ();

    protected Map<String, Item> _itemMap = new HashMap<String, Item> ();

    protected Map<Item, ItemState> _itemCache = new HashMap<Item, ItemState> ();

    private int _period = 0;

    protected Map<String, DataCallback> _itemSet = new HashMap<String, DataCallback> ();

    public AccessBase ( Server server, int period ) throws IllegalArgumentException, UnknownHostException, NotConnectedException, JIException, DuplicateGroupException
    {
        super ();
        _server = server;
        _period = period;
    }

    public boolean isBound ()
    {
        return _bound;
    }

    public synchronized void bind ()
    {
        if ( isBound () )
        {
            return;
        }

        _server.addStateListener ( this );
        _bound = true;
    }

    public synchronized void unbind () throws JIException
    {
        if ( !isBound () )
        {
            return;
        }

        _server.removeStateListener ( this );
        _bound = false;

        stop ();
    }

    public boolean isActive ()
    {
        return _active;
    }

    public void addStateListener ( AccessStateListener listener )
    {
        _stateListeners.add ( listener );
        listener.stateChanged ( isActive () );
    }

    public void removeStateListener ( AccessStateListener listener )
    {
        _stateListeners.remove ( listener );
    }

    protected void notifyStateListenersState ( boolean state )
    {
        List<AccessStateListener> list = new ArrayList<AccessStateListener> ( _stateListeners );

        for ( AccessStateListener listener : list )
        {
            listener.stateChanged ( state );
        }
    }

    protected void notifyStateListenersError ( Throwable t )
    {
        List<AccessStateListener> list = new ArrayList<AccessStateListener> ( _stateListeners );

        for ( AccessStateListener listener : list )
        {
            listener.errorOccured ( t );
        }
    }

    public int getPeriod ()
    {
        return _period;
    }

    public synchronized void addItem ( String itemId, DataCallback dataCallback ) throws JIException, AddFailedException
    {
        if ( _itemSet.containsKey ( itemId ) )
        {
            return;
        }

        _itemSet.put ( itemId, dataCallback );

        if ( isActive () )
        {
            realizeItem ( itemId );
        }
    }

    public synchronized void removeItem ( String itemId )
    {
        if ( !_itemSet.containsKey ( itemId ) )
        {
            return;
        }

        _itemSet.remove ( itemId );

        if ( isActive () )
        {
            unrealizeItem ( itemId );
        }
    }

    public void connectionStateChanged ( boolean connected )
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
        _group = _server.addGroup ();
        _group.setActive ( true );
        _active = true;

        notifyStateListenersState ( true );

        realizeAll ();
    }

    protected void realizeItem ( String itemId ) throws JIException, AddFailedException
    {
        _log.debug ( String.format ( "Realizing item: %s", itemId ) );

        DataCallback dataCallback = _itemSet.get ( itemId );
        if ( dataCallback == null )
        {
            return;
        }

        Item item = _group.addItem ( itemId );
        _items.put ( item, dataCallback );
        _itemMap.put ( itemId, item );
    }

    protected void unrealizeItem ( String itemId )
    {
        Item item = _itemMap.remove ( itemId );
        _items.remove ( item );
        _itemCache.remove ( item );

        try
        {
            _group.removeItem ( itemId );
        }
        catch ( Throwable e )
        {
            _log.error ( String.format ( "Failed to unrealize item '%s'", itemId ) , e );
        }
    }

    /*
     * FIXME: need some perfomance boost: subscribe all in one call
     */
    protected void realizeAll ()
    {
        for ( String itemId : _itemSet.keySet () )
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
        _items.clear ();
        _itemCache.clear ();
        try
        {
            _group.clear ();
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

        _active = false;
        notifyStateListenersState ( false );

        try
        {
            _group.remove ();
        }
        catch ( Throwable t )
        {
            _log.warn ( "Failed to disable group. No problem if we already lost connection" );
        }
        _group = null;
    }

    public synchronized void clear ()
    {
        _itemSet.clear ();
        _items.clear ();
        _itemMap.clear ();
        _itemCache.clear ();
    }

    protected void updateItem ( Item item, ItemState itemState )
    {
        DataCallback dataCallback = _items.get ( item );
        if ( dataCallback == null )
        {
            return;
        }

        ItemState cachedState = _itemCache.get ( item );
        if ( cachedState == null )
        {
            _itemCache.put ( item, itemState );
            dataCallback.changed ( item, itemState );
        }
        else
        {
            if ( !cachedState.equals ( itemState ) )
            {
                _itemCache.put ( item, itemState );
                dataCallback.changed ( item, itemState );
            }
        }
    }

}