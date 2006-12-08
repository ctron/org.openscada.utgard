/*
 * This file is part of the OpenSCADA project
 * Copyright (C) 2006 inavare GmbH (http://inavare.com)
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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIVariant;
import org.openscada.opc.lib.common.NotConnectedException;

public class SyncAccess implements Runnable
{
    private static Logger _log = Logger.getLogger ( SyncAccess.class );

    private Server _server = null;

    private Group _group = null;

    private Map<Item, DataCallback> _items = new HashMap<Item, DataCallback> ();

    private Map<String, Item> _itemMap = new HashMap<String, Item> ();
    
    private Map<Item, ItemState> _itemCache = new HashMap<Item, ItemState> ();

    private boolean _active = false;

    private Thread _runner = null;

    private int _delay = 0;
    
    private List<SyncAccessStateListener> _stateListeners = new LinkedList<SyncAccessStateListener> ();
    
    private Throwable _lastError = null;

    public SyncAccess ( Server server, int delay ) throws IllegalArgumentException, UnknownHostException, NotConnectedException, JIException, DuplicateGroupException
    {
        _server = server;
        _group = _server.addGroup ();
        _group.setActive ( false );
        _delay = delay;
    }

    public synchronized void start () throws JIException
    {
        if ( _active )
            return;
        
        _group.setActive ( true );
        _active = true;
        
        notifyStateListenersState ( true );
        
        _runner = new Thread ( this );
        _runner.setDaemon ( true );
        _runner.start ();
    }

    public synchronized void stop () throws JIException
    {
        if ( !_active )
            return;
        
        _active = false;
        notifyStateListenersState ( false );
        
        _group.setActive ( false );

        _runner = null;
        _itemCache.clear ();
    }

    public synchronized void addItem ( String itemId, DataCallback dataCallback ) throws JIException, AddFailedException
    {
        if ( _items.containsKey ( itemId ) )
            return;

        Item item = _group.addItem ( itemId );
        _items.put ( item, dataCallback );
        _itemMap.put ( itemId, item );
    }

    public synchronized void removeItem ( String itemId )
    {
        if ( !_items.containsKey ( itemId ) )
            return;

        Item item = _itemMap.remove ( itemId );
        _items.remove ( item );
        _itemCache.remove ( item );
    }

    public void run ()
    {
        while ( _active )
        {
            try
            {
                runOnce ();
                if ( _lastError != null )
                {
                    _lastError = null;
                    notifyStateListenersError ( null );
                }
                Thread.sleep ( _delay );
            }
            catch ( Exception e )
            {
                _log.error ( "Sync read failed", e );
                notifyStateListenersError ( e );
                try
                {
                    stop ();
                }
                catch ( Exception e1 )
                {
                    _log.fatal ( "Failed to recover sync read error", e1 );
                }
            }
        }
    }

    protected synchronized void runOnce () throws JIException
    {
        if ( !_active )
            return;

        Item[] items = _items.keySet ().toArray ( new Item[_items.size ()] );

        Map<Item, ItemState> result = _group.read ( false, items );
        for ( Map.Entry<Item, ItemState> entry : result.entrySet () )
        {
            updateItem ( entry.getKey (), entry.getValue () );
        }

    }
    
    protected void updateItem ( Item item, ItemState itemState )
    {
        DataCallback dataCallback = _items.get ( item);
        if ( dataCallback == null )
            return;
        
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
    
    public synchronized void clear ()
    {
        _items.clear ();
        _itemMap.clear ();
        _itemCache.clear ();
    }
    
    public synchronized void addStateListener ( SyncAccessStateListener listener )
    {
        _stateListeners.add ( listener );
    }
    
    public synchronized void removeStateListener ( SyncAccessStateListener listener )
    {
        _stateListeners.remove ( listener );
    }
    
    protected synchronized void notifyStateListenersState ( boolean state )
    {
        List<SyncAccessStateListener> list = new ArrayList<SyncAccessStateListener> ( _stateListeners );
        
        for ( SyncAccessStateListener listener : list )
        {
            listener.stateChanged ( state );
        }
    }
    
    protected synchronized void notifyStateListenersError ( Throwable t )
    {
        List<SyncAccessStateListener> list = new ArrayList<SyncAccessStateListener> ( _stateListeners );
        
        for ( SyncAccessStateListener listener : list )
        {
            listener.errorOccured ( t );
        }
    }
}
