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

public class SyncAccess extends AccessBase implements Runnable
{
    private static Logger _log = Logger.getLogger ( SyncAccess.class );

    private Thread _runner = null;

    private Throwable _lastError = null;
    
    public SyncAccess ( Server server, int period ) throws IllegalArgumentException, UnknownHostException, NotConnectedException, JIException, DuplicateGroupException
    {
        super ( server, period );
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
                Thread.sleep ( getPeriod () );
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

        Item [] items = _items.keySet ().toArray ( new Item[_items.size ()] );

        Map<Item, ItemState> result = _group.read ( false, items );
        for ( Map.Entry<Item, ItemState> entry : result.entrySet () )
        {
            updateItem ( entry.getKey (), entry.getValue () );
        }

    }
        
    @Override
    protected synchronized void start () throws JIException, IllegalArgumentException, UnknownHostException, NotConnectedException, DuplicateGroupException
    {
        super.start ();
        
        _runner = new Thread ( this );
        _runner.setDaemon ( true );
        _runner.start ();
    }
    
    @Override
    protected synchronized void stop () throws JIException
    {
        super.stop ();
        
        _runner = null;
        _items.clear ();
    }
}
