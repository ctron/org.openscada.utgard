/*
 * This file is part of the OpenSCADA project
 * Copyright (C) 2006-2010 TH4 SYSTEMS GmbH (http://th4-systems.com)
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openscada.opc.lib.da;

import org.openscada.opc.dcom.da.OPCSERVERSTATUS;
import org.openscada.opc.dcom.da.impl.OPCServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public ServerStateOperation ( final OPCServer server )
    {
        super ();
        this._server = server;
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
        synchronized ( this._lock )
        {
            this._running = true;
        }
        try
        {
            this._serverStatus = this._server.getStatus ();
            synchronized ( this._lock )
            {
                this._running = false;
                this._lock.notify ();
            }
        }
        catch ( Throwable e )
        {
            _log.info ( "Failed to get server state", e );
            this._error = e;
            this._running = false;
            synchronized ( this._lock )
            {
                this._lock.notify ();
            }
        }

    }

    /**
     * Get the server state with a timeout.
     * @return the server state or <code>null</code> if the server is not set.
     * @param timeout the timeout in ms
     * @throws Throwable any error that occurred
     */
    public OPCSERVERSTATUS getServerState ( final int timeout ) throws Throwable
    {
        if ( this._server == null )
        {
            _log.debug ( "No connection to server. Skipping..." );
            return null;
        }

        Thread t = new Thread ( this, "OPCServerStateReader" );

        synchronized ( this._lock )
        {
            t.start ();
            this._lock.wait ( timeout );
            if ( this._running )
            {
                _log.warn ( "State operation still running. Interrupting..." );
                t.interrupt ();
                throw new InterruptedException ( "Interrupted getting server state" );
            }
        }
        if ( this._error != null )
        {
            _log.warn ( "An error occurred while getting server state", this._error );
            throw this._error;
        }

        return this._serverStatus;
    }

}
