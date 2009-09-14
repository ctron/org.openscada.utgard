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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openscada.opc.dcom.da.OPCSERVERSTATUS;
import org.openscada.opc.dcom.da.impl.OPCServer;

/**
 * A server state operation which can be interruped
 * @author Jens Reimann
 *
 */
public class ServerStateOperation implements Runnable
{
    private static Logger _log = LoggerFactory.getLogger ( ServerStateOperation.class );
    
    public OPCSERVERSTATUS _serverStatus = null;
    public OPCServer _server;
    public Throwable _error;
    public Object _lock = new Object ();
    public boolean _running = false;

    public ServerStateOperation ( OPCServer server )
    {
        super ();
        _server = server;
    }
    
    /**
     * Perform the operation.
     * <p>
     * This method will block until either the serve state has been aquired or the
     * timeout triggers cancels the call.
     * </p>
     */
    public void run ()
    {
        synchronized ( _lock )
        {
            _running = true;
        }
        try
        {
            _serverStatus = _server.getStatus ();
            synchronized ( _lock )
            {
                _running = false;
                _lock.notify ();
            }
        }
        catch ( Throwable e )
        {
            _log.info ( "Failed to get server state", e );
            _error = e;
            _running = false;
            synchronized ( _lock )
            {
                _lock.notify ();
            }
        }

    }
    
    /**
     * Get the server state with a timeout.
     * @return the server state or <code>null</code> if the server is not set.
     * @param timeout the timeout in ms
     * @throws Throwable any error that occurred
     */
    public OPCSERVERSTATUS getServerState ( int timeout ) throws Throwable
    {
        if ( _server == null )
        {
            _log.debug ( "No connection to server. Skipping..." );
            return null;
        }
                
        Thread t = new Thread ( this, "OPCServerStateReader" );
        
        synchronized ( _lock )
        {
            t.start ();
            _lock.wait ( timeout );
            if ( _running )
            {
                _log.warn ( "State operation still running. Interrupting..." );
                t.interrupt ();
                throw new InterruptedException ( "Interrupted getting server state" );
            }
        }
        if ( _error != null )
        {
            _log.warn ( "An error occurred while getting server state", _error );
            throw _error;
        }
         
        return _serverStatus;
    }

}
