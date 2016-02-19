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
package org.openscada.opc.xmlda;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.eclipse.scada.utils.concurrent.NamedThreadFactory;
import org.eclipse.scada.utils.concurrent.NotifyFuture;
import org.opcfoundation.webservices.xmlda._1.RequestOptions;
import org.openscada.opc.xmlda.requests.ReadRequest;
import org.openscada.opc.xmlda.requests.ReadRequest.Builder;
import org.openscada.opc.xmlda.requests.ReadResponse;

public class ReadRequestPoller extends AbstractPoller
{
    private final Builder requestBuilder;

    private final ScheduledExecutorService executor;

    private ReadRequest lastRequest;

    private SubscriptionState currentState;

    ReadRequestPoller ( final Connection connection, final SubscriptionListener listener, final Executor eventExecutor, final long period, final Integer maxAge )
    {
        this ( connection, listener, eventExecutor, period, createDefaultRequestBuilder ( maxAge ) );
    }

    ReadRequestPoller ( final Connection connection, final SubscriptionListener listener, final Executor eventExecutor, final long period, final ReadRequest.Builder requestBuilder )
    {
        super ( connection, listener, eventExecutor );

        this.requestBuilder = requestBuilder;

        this.executor = Executors.newScheduledThreadPool ( 1, new NamedThreadFactory ( "ReadRequestPoller/" + connection ) );

        fireStateUpdate ( SubscriptionState.INACTIVE );

        this.executor.scheduleAtFixedRate ( new Runnable () {
            @Override
            public void run ()
            {
                poll ();
            }
        }, 0, period, TimeUnit.MILLISECONDS );
    }

    private static Builder createDefaultRequestBuilder ( final Integer maxAge )
    {
        final ReadRequest.Builder requestBuilder = new ReadRequest.Builder ().setMaxAge ( maxAge );

        final RequestOptions options = new RequestOptions ();
        options.setReturnDiagnosticInfo ( Boolean.TRUE );
        options.setReturnErrorText ( Boolean.TRUE );
        options.setReturnItemName ( Boolean.TRUE );
        options.setReturnItemPath ( Boolean.FALSE );
        options.setReturnItemTime ( Boolean.TRUE );
        requestBuilder.setOptions ( options );

        return requestBuilder;
    }

    @Override
    public void close ()
    {
        this.executor.shutdown ();
    }

    @Override
    public synchronized void addItem ( final ItemRequest item )
    {
        super.addItem ( item );
        makeModified ();
    }

    @Override
    public synchronized void removeItem ( final ItemRequest item )
    {
        super.removeItem ( item );
        makeModified ();
    }

    @Override
    public synchronized void setItems ( final List<ItemRequest> items )
    {
        super.setItems ( items );
        makeModified ();
    }

    private void makeModified ()
    {
        this.lastRequest = null;
    }

    private void poll ()
    {
        final ReadRequest request;
        synchronized ( this )
        {
            if ( this.handleMap.isEmpty () )
            {
                setState ( SubscriptionState.WAITING );
                return;
            }

            if ( this.lastRequest == null )
            {
                this.lastRequest = makeRequest ();
            }
            request = this.lastRequest;
        }

        try
        {
            final ReadResponse result = performPoll ( request );
            synchronized ( this )
            {
                fireDataUpdate ( result.getValues () );
                setState ( SubscriptionState.ACTIVE );
            }
        }
        catch ( final Exception e )
        {
            synchronized ( this )
            {
                handleError ( e );
            }
        }
    }

    private void handleError ( final Exception e )
    {
        setState ( SubscriptionState.INACTIVE );
    }

    private synchronized void setState ( final SubscriptionState state )
    {
        if ( this.currentState != state )
        {
            this.currentState = state;
            fireStateUpdate ( state );
        }
    }

    private ReadResponse performPoll ( final ReadRequest request ) throws Exception
    {
        final NotifyFuture<ReadResponse> future = this.connection.scheduleTask ( request );
        return future.get ();
    }

    private ReadRequest makeRequest ()
    {
        final ReadRequest result = this.requestBuilder.setItems ( this.handleMap.values () ).build ();
        return result;
    }

}
