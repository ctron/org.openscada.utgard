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

import static java.util.Objects.requireNonNull;
import static org.openscada.opc.xmlda.ItemRequest.makeRequests;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import org.openscada.opc.xmlda.requests.ItemValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A common base class for {@link SubscriptionPoller} and
 * {@link ReadRequestPoller}
 */
public abstract class AbstractPoller implements Poller
{
    private final static Logger logger = LoggerFactory.getLogger ( AbstractPoller.class );

    protected final Connection connection;

    protected final SubscriptionListener listener;

    protected final Map<String, ItemRequest> handleMap = new HashMap<> ();

    private final Executor eventExecutor;

    public AbstractPoller ( final Connection connection, final SubscriptionListener listener, final Executor eventExecutor )
    {
        this.connection = connection;
        this.listener = listener;
        this.eventExecutor = eventExecutor;
    }

    @Override
    public synchronized void addItem ( final ItemRequest item )
    {
        logger.debug ( "Adding item: {}", item );

        requireNonNull ( item.getClientHandle (), "'ItemRequest.clientHandle' must not be null" );

        this.handleMap.put ( item.getClientHandle (), item );
    }

    @Override
    public synchronized void removeItem ( final ItemRequest item )
    {
        logger.debug ( "Removing item: {}", item );

        requireNonNull ( item.getClientHandle (), "'ItemRequest.clientHandle' must not be null" );

        this.handleMap.remove ( item.getClientHandle () );
    }

    @Override
    public synchronized void setItems ( final List<ItemRequest> items )
    {
        // validate items before applying changes
        for ( final ItemRequest req : items )
        {
            requireNonNull ( req.getClientHandle (), "'ItemRequest.clientHandle' must not be null" );
        }

        this.handleMap.clear ();
        for ( final ItemRequest req : items )
        {
            this.handleMap.put ( req.getClientHandle (), req );
        }
    }

    @Override
    public void setItems ( final String... items )
    {
        setItems ( makeRequests ( Arrays.asList ( items ) ) );
    }

    protected synchronized void fireStateUpdate ( final SubscriptionState state )
    {
        if ( this.listener == null )
        {
            return;
        }

        this.eventExecutor.execute ( new Runnable () {

            @Override
            public void run ()
            {
                AbstractPoller.this.listener.stateChange ( state );
            }
        } );
    }

    protected synchronized void fireDataUpdate ( final Map<String, ItemValue> values )
    {
        if ( this.listener == null )
        {
            return;
        }

        this.eventExecutor.execute ( new Runnable () {

            @Override
            public void run ()
            {
                AbstractPoller.this.listener.dataChange ( values );
            }
        } );
    }

}
