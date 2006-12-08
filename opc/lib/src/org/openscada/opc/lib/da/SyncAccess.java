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
import org.openscada.opc.lib.common.NotConnectedException;

public class SyncAccess implements Runnable, ServerConnectionStateListener
{
    private static Logger _log = Logger.getLogger ( SyncAccess.class );

    private Server _server = null;

    private Group _group = null;
    
    private Map<String, DataCallback> _itemSet = new HashMap<String,DataCallback> (); 

    private Map<Item, DataCallback> _items = new HashMap<Item, DataCallback> ();

    private Map<String, Item> _itemMap = new HashMap<String, Item> ();
    
    private Map<Item, ItemState> _itemCache = new HashMap<Item, ItemState> ();

    private boolean _active = false;

    private Thread _runner = null;

    private int _delay = 0;
    
    private List<SyncAccessStateListener> _stateListeners = new LinkedList<SyncAccessStateListener> ();
    
    private Throwable _lastError = null;
    
    private boolean _bound = false;
    
    public SyncAccess ( Server server, int delay ) throws IllegalArgumentException, UnknownHostException, NotConnectedException, JIException, DuplicateGroupException
    {
        _server = server;
        _delay = delay;
    }
    
    public boolean isBound ()
    {
        return _bound;
    }
    
    public synchronized void bind ()
    {
        if ( isBound () )
            return;
        
        _server.addStateListener ( this );
        _bound = true;
    }
    
    public synchronized void unbind () throws JIException
    {
        if ( !isBound () )
            return;
        
        _server.removeStateListener ( this );
        _bound = false;
        
        stop ();
    }
    
    public boolean isActive ()
    {
        return _active;
    }
  
    protected synchronized void start () throws JIException, IllegalArgumentException, UnknownHostException, NotConnectedException, DuplicateGroupException
    {
        if ( isActive () )
            return;
        
        _group = _server.addGroup ();
        _group.setActive ( true );
        _active = true;
        
        notifyStateListenersState ( true );
        
        _runner = new Thread ( this );
        _runner.setDaemon ( true );
        _runner.start ();
        
        realizeAll ();
    }

    protected synchronized void stop () throws JIException
    {
        if ( !isActive () )
            return;

        unrealizeAll ();
        
        _active = false;
        notifyStateListenersState ( false );
        
        _group.setActive ( false );
        _group = null;

        _runner = null;
        _items.clear ();
    }

    public synchronized void addItem ( String itemId, DataCallback dataCallback ) throws JIException, AddFailedException
    {
        if ( _itemSet.containsKey ( itemId ) )
            return;
        
        _itemSet.put ( itemId, dataCallback );
        
        if ( isActive () )
        {
            realizeItem ( itemId );
        }
    }

    public synchronized void removeItem ( String itemId )
    {
        if ( !_itemSet.containsKey ( itemId ) )
            return;

        _itemSet.remove ( itemId );
        
        if ( isActive () )
        {
            unrealizeItem ( itemId );
        }
    }
    
    protected void realizeItem ( String itemId ) throws JIException, AddFailedException
    {
        _log.debug ( String.format ( "Realizing item: %s", itemId ) );
        
        DataCallback dataCallback = _itemSet.get ( itemId );
        if ( dataCallback == null )
            return;
        
        Item item = _group.addItem ( itemId );
        _items.put ( item, dataCallback );
        _itemMap.put ( itemId, item );
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
            catch ( Exception e )
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    protected void unrealizeItem ( String itemId )
    {
        Item item = _itemMap.remove ( itemId );
        _items.remove ( item );
        _itemCache.remove ( item );
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
            }
            catch ( Exception e )
            {
                _log.error ( "Sync read failed", e );
                notifyStateListenersError ( e );
                _server.disconnect ();
            }
            
            try
            {
                Thread.sleep ( _delay );
            }
            catch ( InterruptedException e )
            {
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
        _itemSet.clear ();
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
            _log.error ( "Failed to change state", e );
        }
    }
}
