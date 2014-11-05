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

import java.net.UnknownHostException;
import java.util.Map;

import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.NotConnectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SyncAccess extends AccessBase implements Runnable
{
    private static Logger logger = LoggerFactory.getLogger ( SyncAccess.class );

    private Thread runner = null;

    private Throwable lastError = null;

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
        while ( this.active )
        {
            try
            {
                runOnce ();
                if ( this.lastError != null )
                {
                    this.lastError = null;
                    handleError ( null );
                }
            }
            catch ( Throwable e )
            {
                logger.error ( "Sync read failed", e );
                handleError ( e );
                this.server.disconnect ();
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
        if ( !this.active || this.group == null )
        {
            return;
        }

        Map<Item, ItemState> result;

        // lock only this section since we could get into a deadlock otherwise
        // calling updateItem
        synchronized ( this )
        {
            Item[] items = this.items.keySet ().toArray ( new Item[this.items.size ()] );
            result = this.group.read ( false, items );
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

        this.runner = new Thread ( this, "UtgardSyncReader" );
        this.runner.setDaemon ( true );
        this.runner.start ();
    }

    @Override
    protected synchronized void stop () throws JIException
    {
        super.stop ();

        this.runner = null;
        this.items.clear ();
    }
}
