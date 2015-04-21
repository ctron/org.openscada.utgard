/*******************************************************************************
 * Copyright (c) 2015 IBH SYSTEMS GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBH SYSTEMS GmbH - initial API and implementation
 *******************************************************************************/
package org.openscada.opc.xmlda.browse;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.scada.utils.concurrent.FutureListener;
import org.eclipse.scada.utils.concurrent.NotifyFuture;
import org.openscada.opc.xmlda.Connection;
import org.openscada.opc.xmlda.Task;
import org.openscada.opc.xmlda.requests.BrowseEntry;
import org.openscada.opc.xmlda.requests.BrowseRequest;
import org.openscada.opc.xmlda.requests.BrowseResponse;
import org.openscada.opc.xmlda.requests.BrowseType;

public class Browser
{
    private final String itemName;

    private final String itemPath;

    private final Connection connection;

    private final ScheduledExecutorService executor;

    private final Executor eventExecutor;

    private final BrowserListener listener;

    private final long scanDelay;

    private final int batchSize;

    private ScheduledFuture<?> job;

    private boolean disposed;

    private boolean scanning;

    private final boolean fullProperties;

    public Browser ( final String itemName, final String itemPath, final Connection connection, final ScheduledExecutorService executor, final Executor eventExecutor, final BrowserListener listener, final long scanDelay, final int batchSize, final boolean fullProperties )
    {
        this.itemName = itemName;
        this.itemPath = itemPath;

        this.connection = connection;
        this.executor = executor;
        this.eventExecutor = eventExecutor;
        this.listener = listener;

        this.scanDelay = scanDelay;
        this.batchSize = batchSize;

        this.fullProperties = fullProperties;

        startBrowse ();
    }

    private synchronized void startBrowse ()
    {
        this.job = null;
        this.scanning = true;

        fireStateChange ( BrowserState.BROWSING, null );
        scheduleBrowse ( new BrowseRequest ( this.itemName, this.itemPath, BrowseType.ALL, this.batchSize, this.fullProperties ) );
    }

    private void scheduleBrowse ( final Task<BrowseResponse> request )
    {
        final NotifyFuture<BrowseResponse> future = this.connection.scheduleTask ( request );
        future.addListener ( new FutureListener<BrowseResponse> () {

            @Override
            public void complete ( final Future<BrowseResponse> future )
            {
                handleResult ( future );
            }
        } );
    }

    protected void handleResult ( final Future<BrowseResponse> future )
    {
        synchronized ( this )
        {
            // this early abort may cause events to be fired after the dispose method had been called
            // but it will prevent further processing
            if ( this.disposed )
            {
                return;
            }
        }

        try
        {
            final BrowseResponse result = future.get ();
            handleSuccess ( result );
        }
        catch ( final Throwable e )
        {
            handleError ( e );
        }
    }

    private synchronized void handleSuccess ( final BrowseResponse result )
    {
        if ( this.disposed )
        {
            return;
        }

        fireDataChange ( result.getEntries () );
        final BrowseRequest cr = result.makeContinuationRequest ();
        if ( cr != null )
        {
            scheduleBrowse ( cr );
        }
        else
        {
            fireStateChange ( BrowserState.COMPLETE, null );
        }
    }

    private synchronized void handleError ( final Throwable e )
    {
        if ( this.disposed )
        {
            return;
        }

        fireStateChange ( BrowserState.ERROR, e );

        this.job = this.executor.schedule ( new Runnable () {

            @Override
            public void run ()
            {
                startBrowse ();
            }
        }, this.scanDelay, TimeUnit.MILLISECONDS );
    }

    private synchronized void fireStateChange ( final BrowserState state, final Throwable error )
    {
        if ( state != BrowserState.BROWSING )
        {
            this.scanning = false;
            notifyAll ();
        }

        if ( this.listener == null )
        {
            return;
        }

        this.eventExecutor.execute ( new Runnable () {

            @Override
            public void run ()
            {
                Browser.this.listener.stateChange ( state, error );
            }
        } );
    }

    private void fireDataChange ( final List<BrowseEntry> entries )
    {
        if ( this.listener == null )
        {
            return;
        }

        this.eventExecutor.execute ( new Runnable () {

            @Override
            public void run ()
            {
                Browser.this.listener.dataChange ( entries );
            }
        } );
    }

    /**
     * Dispose and await completion before
     */
    public synchronized void dispose ( final long timeout ) throws InterruptedException
    {
        awaitCompletion ( timeout );
        dispose ();
    }

    /**
     * Dispose without waiting for completion
     */
    public synchronized void dispose ()
    {
        if ( this.disposed )
        {
            return;
        }

        this.disposed = true;

        if ( this.job != null )
        {
            this.job.cancel ( false );
            this.job = null;
        }
    }

    /**
     * Wait until the browsing is complete or an error occurred
     *
     * @throws InterruptedException
     */
    public synchronized void awaitCompletion ( final long timeout ) throws InterruptedException
    {
        while ( this.scanning )
        {
            wait ( timeout );
        }
    }
}
