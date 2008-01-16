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

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.log4j.Logger;

public class AutoReconnectController implements ServerConnectionStateListener
{
    private static Logger _log = Logger.getLogger ( AutoReconnectController.class );

    private static final int DEFAULT_DELAY = 5 * 1000;

    private int _delay;

    private Server _server;

    private Set<AutoReconnectListener> _listeners = new CopyOnWriteArraySet<AutoReconnectListener> ();

    private AutoReconnectState _state = AutoReconnectState.DISABLED;

    private Thread _connectTask = null;

    public AutoReconnectController ( Server server )
    {
        this ( server, DEFAULT_DELAY );
    }

    public AutoReconnectController ( Server server, int delay )
    {
        super ();
        setDelay ( delay );

        _server = server;
        _server.addStateListener ( this );
    }

    public void addListener ( AutoReconnectListener listener )
    {
        if ( listener != null )
        {
            _listeners.add ( listener );
            listener.stateChanged ( _state );
        }
    }

    public void removeListener ( AutoReconnectListener listener )
    {
        _listeners.remove ( listener );
    }

    protected void notifyStateChange ( AutoReconnectState state )
    {
        _state = state;
        for ( AutoReconnectListener listener : _listeners )
        {
            listener.stateChanged ( state );
        }
    }

    public int getDelay ()
    {
        return _delay;
    }

    /**
     * Set the reconnect delay. If the delay less than or equal to zero it will be
     * the default delay time.
     * @param delay The delay to use
     */
    public void setDelay ( int delay )
    {
        if ( delay <= 0 )
        {
            delay = DEFAULT_DELAY;
        }
        _delay = delay;
    }

    public synchronized void connect ()
    {
        if ( isRequested () )
        {
            return;
        }

        _log.debug ( "Requesting connection" );
        notifyStateChange ( AutoReconnectState.DISCONNECTED );

        triggerReconnect ( false );
    }

    public synchronized void disconnect ()
    {
        if ( !isRequested () )
        {
            return;
        }

        _log.debug ( "Un-Requesting connection" );

        notifyStateChange ( AutoReconnectState.DISABLED );
        _server.disconnect ();
    }

    public boolean isRequested ()
    {
        return _state != AutoReconnectState.DISABLED;
    }

    public synchronized void connectionStateChanged ( boolean connected )
    {
        _log.debug ( "Connection state changed: " + connected );

        if ( !connected )
        {
            if ( isRequested () )
            {
                notifyStateChange ( AutoReconnectState.DISCONNECTED );
                triggerReconnect ( true );
            }
        }
        else
        {
            if ( !isRequested () )
            {
                _server.disconnect ();
            }
            else
            {
                notifyStateChange ( AutoReconnectState.CONNECTED );
            }
        }
    }

    private synchronized void triggerReconnect ( final boolean wait )
    {
        if ( _connectTask != null )
        {
            _log.debug ( "Connect thread already running" );
            return;
        }
        
        _log.debug ( "Trigger reconnect" );

        _connectTask = new Thread ( new Runnable () {

            public void run ()
            {
                boolean result = false;
                try
                {
                    result = performReconnect ( wait );
                }
                finally
                {
                    AutoReconnectController.this._connectTask = null;
                    _log.debug ( String.format ( "performReconnect completed : %s", result ) );
                    if ( !result )
                    {
                        triggerReconnect ( true );
                    }
                }
            }
        } );
        _connectTask.setDaemon ( true );
        _connectTask.start ();
    }

    private boolean performReconnect ( boolean wait )
    {
        try
        {
            if ( wait )
            {
                notifyStateChange ( AutoReconnectState.WAITING );
                _log.debug ( String.format ( "Delaying (%s)...", _delay ) );
                Thread.sleep ( _delay );
            }
        }
        catch ( InterruptedException e )
        {
        }

        if ( !isRequested () )
        {
            _log.debug ( "Request canceled during delay" );
            return true;
        }

        try
        {
            _log.debug ( "Connecting to server" );
            notifyStateChange ( AutoReconnectState.CONNECTING );
            synchronized ( this )
            {
                _server.connect ();
                return true;
            }
            // CONNECTED state will be set by server callback
        }
        catch ( Throwable e )
        {
            _log.info ( "Re-connect failed", e );
            notifyStateChange ( AutoReconnectState.DISCONNECTED );
            return false;
        }
    }

}
