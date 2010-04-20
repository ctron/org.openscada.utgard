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
import java.util.Map;

import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.NotConnectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SyncAccess extends AccessBase implements Runnable
{
    private static Logger _log = LoggerFactory.getLogger ( SyncAccess.class );

    private Thread _runner = null;

    private Throwable _lastError = null;

    public SyncAccess ( final Server server, final int period ) throws IllegalArgumentException, UnknownHostException, NotConnectedException, JIException, DuplicateGroupException
    {
        super ( server, period );
    }

    public SyncAccess ( final Server server, final int period, final String logTag ) throws IllegalArgumentException, UnknownHostException, NotConnectedException, JIException, DuplicateGroupException
    {
        super ( server, period, logTag );
    }

    public void run ()
    {
        while ( this._active )
        {
            try
            {
                runOnce ();
                if ( this._lastError != null )
                {
                    this._lastError = null;
                    handleError ( null );
                }
            }
            catch ( Throwable e )
            {
                _log.error ( "Sync read failed", e );
                handleError ( e );
                this._server.disconnect ();
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

    protected void runOnce () throws JIException
    {
        if ( !this._active || this._group == null )
        {
            return;
        }

        Map<Item, ItemState> result;

        // lock only this section since we could get into a deadlock otherwise
        // calling updateItem
        synchronized ( this )
        {
            Item[] items = this._items.keySet ().toArray ( new Item[this._items.size ()] );
            result = this._group.read ( false, items );
        }

        for ( Map.Entry<Item, ItemState> entry : result.entrySet () )
        {
            updateItem ( entry.getKey (), entry.getValue () );
        }

    }

    @Override
    protected synchronized void start () throws JIException, IllegalArgumentException, UnknownHostException, NotConnectedException, DuplicateGroupException
    {
        super.start ();

        this._runner = new Thread ( this, "UtgardSyncReader" );
        this._runner.setDaemon ( true );
        this._runner.start ();
    }

    @Override
    protected synchronized void stop () throws JIException
    {
        super.stop ();

        this._runner = null;
        this._items.clear ();
    }
}
