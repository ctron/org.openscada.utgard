/*
 * This file is part of the OpenSCADA project
 * Copyright (C) 2006-2009 inavare GmbH (http://inavare.com)
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openscada.opc.dcom.da.OPCSERVERSTATUS;
import org.openscada.utils.timing.Scheduler;
import org.openscada.utils.timing.Scheduler.Job;

public class ServerStateReader
{
    private static Logger _log = LoggerFactory.getLogger ( ServerStateReader.class );
    
    private Server _server = null;

    private Scheduler _scheduler = null;

    private List<ServerStateListener> _listeners = new CopyOnWriteArrayList<ServerStateListener> ();

    private Job _job = null;

    public ServerStateReader ( Server server )
    {
        super ();
        _server = server;
        _scheduler = _server.getScheduler ();
    }

    /**
     * Create a new server state reader. Please note that the scheduler might get
     * blocked for a short period of time in case of a connection failure!
     * @param server the server to check
     * @param scheduler the scheduler to use
     */
    public ServerStateReader ( Server server, Scheduler scheduler )
    {
        super ();
        _server = server;
        _scheduler = scheduler;
    }

    public synchronized void start ()
    {
        if ( _job != null )
        {
            return;
        }

        _job = _scheduler.addJob ( new Runnable () {

            public void run ()
            {
                once ();
            }
        }, 1000 );
    }

    public synchronized void stop ()
    {
        _scheduler.removeJob ( _job );
        _job = null;
    }

    protected void once ()
    {
        _log.debug ( "Reading server state" );
        
        OPCSERVERSTATUS state = _server.getServerState ();
        
        for ( ServerStateListener listener : new ArrayList<ServerStateListener> ( _listeners ) )
        {
            listener.stateUpdate ( state );
        }
    }

    public void addListener ( ServerStateListener listener )
    {
        _listeners.add ( listener );
    }

    public void removeListener ( ServerStateListener listener )
    {
        _listeners.remove ( listener );
    }
}
