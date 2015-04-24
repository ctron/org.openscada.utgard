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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;

import org.eclipse.scada.utils.concurrent.FutureTask;
import org.eclipse.scada.utils.concurrent.NamedThreadFactory;
import org.eclipse.scada.utils.concurrent.NotifyFuture;
import org.openscada.opc.xmlda.browse.Browser;
import org.openscada.opc.xmlda.browse.BrowserListener;
import org.openscada.opc.xmlda.requests.BrowseEntry;

public class Connection implements AutoCloseable
{
    private final Service service;

    private final org.opcfoundation.webservices.xmlda._1.Service soap;

    private final ScheduledExecutorService executor;

    private final ExecutorService eventExecutor;

    private final String name;

    private final int requestTimeout;

    protected class TaskRunner<T> extends FutureTask<T>
    {
        public TaskRunner ( final Task<T> serviceCall )
        {
            super ( new Callable<T> () {

                @Override
                public T call () throws Exception
                {
                    return serviceCall.process ( Connection.this );
                }
            } );
        }
    }

    public Connection ( final String url, final String serviceName ) throws MalformedURLException
    {
        this ( new URL ( url ), new QName ( "http://opcfoundation.org/webservices/XMLDA/1.0/", serviceName ), serviceName + "Soap", 5_000, 10_000 );
    }

    public Connection ( final URL url, final QName serviceName, final String localPortName, final int connectTimeout, final int requestTimeout )
    {
        this.name = url + "/" + localPortName;

        this.service = Service.create ( url, serviceName );
        this.requestTimeout = requestTimeout;

        final QName portName = new QName ( serviceName.getNamespaceURI (), localPortName );

        this.soap = this.service.getPort ( portName, org.opcfoundation.webservices.xmlda._1.Service.class );

        final BindingProvider bindingProvider = (BindingProvider)this.soap;

        final Map<String, Object> context = bindingProvider.getRequestContext ();

        context.put ( "javax.xml.ws.client.connectionTimeout", connectTimeout );
        context.put ( "javax.xml.ws.client.receiveTimeout", requestTimeout );
        context.put ( "com.sun.xml.internal.ws.connect.timeout", connectTimeout );
        context.put ( "com.sun.xml.internal.ws.request.timeout", requestTimeout );
        context.put ( BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url.toString () );

        this.executor = Executors.newSingleThreadScheduledExecutor ( new NamedThreadFactory ( this.name + "/Requests" ) );
        this.eventExecutor = Executors.newSingleThreadExecutor ( new NamedThreadFactory ( this.name + "/Events" ) );
    }

    public Poller createPoller ( final SubscriptionListener listener )
    {
        return new Poller ( this, this.eventExecutor, listener, (int) ( this.requestTimeout * 0.8 + 1.0 ) );
    }

    public Browser createBrowser ( final String itemName, final String itemPath, final BrowserListener listener, final long scanDelay, final int batchSize, final boolean fullProperties )
    {
        return new Browser ( itemName, itemPath, this, this.executor, this.eventExecutor, listener, scanDelay, batchSize, fullProperties );
    }

    public Browser createBrowser ( final BrowseEntry entry, final BrowserListener listener, final long scanDelay, final int batchSize, final boolean fullProperties )
    {
        return createBrowser ( entry.getItemName (), entry.getItemPath (), listener, scanDelay, batchSize, fullProperties );
    }

    public Browser createRootBrowser ( final BrowserListener listener, final long scanDelay, final int batchSize, final boolean fullProperties )
    {
        return createBrowser ( null, null, listener, scanDelay, batchSize, fullProperties );
    }

    public <S> S unwrap ( final Class<S> clazz )
    {
        if ( clazz.equals ( org.opcfoundation.webservices.xmlda._1.Service.class ) )
        {
            return clazz.cast ( this.soap );
        }
        return null;
    }

    public <T> NotifyFuture<T> scheduleTask ( final Task<T> serviceCall )
    {
        final TaskRunner<T> runner = new TaskRunner<> ( serviceCall );
        this.executor.execute ( runner );
        return runner;
    }

    @Override
    public void close () throws Exception
    {
        try
        {
            // shutdown requests now

            final List<Runnable> remaining;

            // FIXME: shut down pollers

            synchronized ( this )
            {
                remaining = this.executor.shutdownNow ();
            }

            for ( final Runnable runner : remaining )
            {
                if ( runner instanceof TaskRunner<?> )
                {
                    ( (TaskRunner<?>)runner ).cancel ( false );
                }
            }
        }
        finally
        {
            // shutdown events
            this.eventExecutor.shutdown ();
        }
    }

    @Override
    public String toString ()
    {
        return this.name;
    }
}
