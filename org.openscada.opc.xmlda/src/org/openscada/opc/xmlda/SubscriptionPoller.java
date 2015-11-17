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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicLong;

import javax.xml.namespace.QName;

import org.opcfoundation.webservices.xmlda._1.RequestOptions;
import org.opcfoundation.webservices.xmlda._1.Service;
import org.opcfoundation.webservices.xmlda._1.Subscribe;
import org.opcfoundation.webservices.xmlda._1.SubscribeItemValue;
import org.opcfoundation.webservices.xmlda._1.SubscribePolledRefreshReplyItemList;
import org.opcfoundation.webservices.xmlda._1.SubscribeRequestItem;
import org.opcfoundation.webservices.xmlda._1.SubscribeRequestItemList;
import org.opcfoundation.webservices.xmlda._1.SubscribeResponse;
import org.opcfoundation.webservices.xmlda._1.SubscriptionCancel;
import org.opcfoundation.webservices.xmlda._1.SubscriptionPolledRefresh;
import org.opcfoundation.webservices.xmlda._1.SubscriptionPolledRefreshResponse;
import org.openscada.opc.xmlda.internal.Helper;
import org.openscada.opc.xmlda.requests.ItemValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubscriptionPoller extends AbstractPoller
{
    private final static Logger logger = LoggerFactory.getLogger ( SubscriptionPoller.class );

    private static final AtomicLong POLLER_COUNTER = new AtomicLong ();

    private final int waitTime;

    private final String name;

    private final AtomicLong threadCounter = new AtomicLong ();

    private Thread thread;

    private volatile String subscriptionHandle;

    private volatile boolean running;

    private final ExecutorService executor;

    private final AtomicLong changeCounter = new AtomicLong ();

    private final Integer samplingRate;

    SubscriptionPoller ( final Connection connection, final ExecutorService executor, final SubscriptionListener listener, final int waitTime, final Integer samplingRate )
    {
        super ( connection, listener, executor );

        this.executor = executor;
        this.waitTime = waitTime;

        this.samplingRate = samplingRate;

        this.name = String.format ( "Poller/%s/%s", connection, POLLER_COUNTER.incrementAndGet () );

        start ();
    }

    protected void setup ()
    {
        final long current = this.changeCounter.get ();

        final Service soap = this.connection.unwrap ( Service.class );

        final RequestOptions options = makeDefautOptions ();

        final SubscribeRequestItemList itemList = new SubscribeRequestItemList ();

        synchronized ( this )
        {
            this.subscriptionHandle = null;

            if ( this.handleMap.isEmpty () )
            {
                logger.debug ( "No items registered. Skipping ..." );
                return;
            }

            for ( final ItemRequest item : this.handleMap.values () )
            {
                final SubscribeRequestItem itemRequest = new SubscribeRequestItem ();
                itemRequest.setItemName ( item.getItemName () );
                itemRequest.setClientItemHandle ( item.getClientHandle () );
                itemRequest.setEnableBuffering ( true );
                itemRequest.setRequestedSamplingRate ( this.samplingRate );
                itemList.getItems ().add ( itemRequest );
            }
        }

        final Subscribe subscribe = new Subscribe ();
        subscribe.setOptions ( options );
        subscribe.setReturnValuesOnReply ( true );

        subscribe.setItemList ( itemList );
        subscribe.setSubscriptionPingRate ( this.waitTime * 4 );

        final SubscribeResponse result = soap.subscribe ( subscribe );

        // we can always send out what we have
        updateValues ( makeValues ( result ) );

        if ( this.changeCounter.get () != current )
        {
            return; // re-try
        }

        synchronized ( this )
        {
            this.subscriptionHandle = result.getServerSubHandle ();
            fireStateUpdate ( SubscriptionState.ACTIVE );
        }
    }

    @Override
    public synchronized void addItem ( final ItemRequest item )
    {
        logger.debug ( "Adding item: {}", item );

        super.addItem ( item );

        markSubscriptionChange ();
        notifyAll ();
    }

    @Override
    public synchronized void removeItem ( final ItemRequest item )
    {
        logger.debug ( "Removing item: {}", item );

        super.removeItem ( item );

        markSubscriptionChange ();
        notifyAll ();
    }

    private void markSubscriptionChange ()
    {
        if ( this.executor.isShutdown () )
        {
            return;
        }

        this.executor.execute ( new Runnable () {

            @Override
            public void run ()
            {
                performAbortPoll ();
            }

        } );
    }

    private void performAbortPoll ()
    {
        this.changeCounter.incrementAndGet ();

        final String subscriptionHandle;

        synchronized ( this )
        {
            subscriptionHandle = this.subscriptionHandle;
            this.subscriptionHandle = null;
        }

        if ( subscriptionHandle != null )
        {
            performCancelSubscription ( subscriptionHandle );
        }
    }

    @Override
    public synchronized void setItems ( final List<ItemRequest> items )
    {
        super.setItems ( items );

        markSubscriptionChange ();
        notifyAll ();
    }

    protected void runPoller ()
    {
        logger.info ( "Starting poller" );
        try
        {
            long lastValue = -1;
            fireStateUpdate ( SubscriptionState.INACTIVE );
            while ( this.running )
            {
                try
                {
                    final long current = this.changeCounter.get ();
                    if ( current != lastValue || this.subscriptionHandle == null )
                    {
                        logger.info ( "Performing setup - changeCounter: {}, last: {}, handle: {}", current, lastValue, this.subscriptionHandle );
                        setup ();
                        lastValue = current;
                        logger.info ( "Setup complete" );
                    }
                    if ( this.subscriptionHandle != null )
                    {
                        logger.debug ( "Performing poll" );
                        performPollOnce ();
                    }
                    synchronized ( this )
                    {
                        if ( this.handleMap.isEmpty () )
                        {
                            logger.info ( "Waiting for items" );
                            fireStateUpdate ( SubscriptionState.WAITING );
                            wait ();
                        }
                    }
                }
                catch ( final Throwable e )
                {
                    logger.warn ( "Failed to poll", e );
                    invalidate ();
                    try
                    {
                        Thread.sleep ( this.waitTime );
                    }
                    catch ( final InterruptedException e1 )
                    {
                        // got woken up
                    }
                }
            }
        }
        finally
        {
            logger.info ( "Exit poll loop" );
        }
    }

    protected void performPollOnce ()
    {
        synchronized ( this )
        {
            if ( this.subscriptionHandle == null )
            {
                return;
            }
        }

        final Service soap = this.connection.unwrap ( Service.class );

        final RequestOptions options = makeDefautOptions ();

        final SubscriptionPolledRefresh parameters = new SubscriptionPolledRefresh ();
        parameters.setOptions ( options );
        parameters.setWaitTime ( this.waitTime );
        parameters.getServerSubHandles ().add ( this.subscriptionHandle );

        logger.debug ( "Enter poll" );
        final SubscriptionPolledRefreshResponse result = soap.subscriptionPolledRefresh ( parameters );
        logger.debug ( "Poll returned" );

        synchronized ( this )
        {
            if ( this.subscriptionHandle == null )
            {
                // someone canceled our subscription from outside
                return;
            }

            if ( result.getInvalidServerSubHandles () != null && result.getInvalidServerSubHandles ().contains ( this.subscriptionHandle ) )
            {
                invalidate ();
            }
            else
            {
                updateValues ( makeValues ( result ) );
            }
        }
    }

    private void updateValues ( final Map<String, ItemValue> values )
    {
        fireDataUpdate ( values );
    }

    private Map<String, ItemValue> makeValues ( final SubscribeResponse result )
    {
        final Map<QName, String> errorMap = Helper.mapErrors ( result.getErrors () );
        final Map<String, ItemValue> values = new HashMap<> ();

        makeValuesSubscription ( values, errorMap, result.getRItemList ().getItems () );

        return values;
    }

    private Map<String, ItemValue> makeValues ( final SubscriptionPolledRefreshResponse result )
    {
        final Map<String, ItemValue> values = new HashMap<> ();

        final Map<QName, String> errorMap = Helper.mapErrors ( result.getErrors () );

        for ( final SubscribePolledRefreshReplyItemList list : result.getRItemList () )
        {
            logger.debug ( "{} items in reply", list.getItems ().size () );
            makeValues ( values, errorMap, list.getItems () );
        }

        return values;
    }

    private void makeValuesSubscription ( final Map<String, ItemValue> values, final Map<QName, String> errorMap, final List<SubscribeItemValue> list )
    {
        for ( final org.opcfoundation.webservices.xmlda._1.SubscribeItemValue value : list )
        {
            final ItemRequest req = this.handleMap.get ( value.getItemValue ().getClientItemHandle () );

            final ItemValue iv = Helper.convertValue ( value.getItemValue (), req.getItemName (), errorMap );
            values.put ( value.getItemValue ().getClientItemHandle (), iv );
        }
    }

    private void makeValues ( final Map<String, ItemValue> values, final Map<QName, String> errorMap, final List<org.opcfoundation.webservices.xmlda._1.ItemValue> list )
    {
        for ( final org.opcfoundation.webservices.xmlda._1.ItemValue value : list )
        {
            final ItemRequest req = this.handleMap.get ( value.getClientItemHandle () );

            final ItemValue iv = Helper.convertValue ( value, req.getItemName (), errorMap );
            values.put ( value.getClientItemHandle (), iv );
        }
    }

    private void invalidate ()
    {
        logger.info ( "Invalidating: {}", this.subscriptionHandle );
        fireStateUpdate ( SubscriptionState.INACTIVE );
        this.subscriptionHandle = null;
    }

    private RequestOptions makeDefautOptions ()
    {
        final RequestOptions options = new RequestOptions ();
        options.setReturnErrorText ( true );
        options.setReturnDiagnosticInfo ( true );
        options.setReturnItemTime ( true );
        return options;
    }

    protected synchronized void start ()
    {
        if ( this.thread != null )
        {
            return;
        }

        this.running = true;

        this.thread = new Thread () {
            @Override
            public void run ()
            {
                runPoller ();
            }
        };

        this.thread.setName ( this.name + "/" + this.threadCounter.incrementAndGet () );
        this.thread.start ();
    }

    @Override
    public void close ()
    {
        logger.info ( "Disposing" );
        this.running = false;

        String subscriptionHandle;
        synchronized ( this )
        {
            subscriptionHandle = this.subscriptionHandle;
            this.subscriptionHandle = null;
        }

        if ( subscriptionHandle != null )
        {
            logger.info ( "Disposing at server..." );

            performCancelSubscription ( subscriptionHandle );

            logger.info ( "Disposing at server...done!" );
        }
    }

    private void performCancelSubscription ( final String subscriptionHandle )
    {
        try
        {
            logger.info ( "Canceling subscription: {}", subscriptionHandle );

            final Service soap = this.connection.unwrap ( Service.class );

            final SubscriptionCancel parameters = new SubscriptionCancel ();
            parameters.setServerSubHandle ( subscriptionHandle );
            soap.subscriptionCancel ( parameters );
        }
        catch ( final Exception e )
        {
            logger.info ( "Failed to dispose at server", e );
        }
    }

}
