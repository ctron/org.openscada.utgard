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
    private org.opcfoundation.webservices.xmlda._1.Service soap;

    private final ScheduledExecutorService executor;

    private final ExecutorService eventExecutor;

    private final String name;

    private final int requestTimeout;

    private final QName serviceName;

    private final String localPortName;

    private final int connectTimeout;

    private final URL wsdlUrl;

    private final URL serverUrl;

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

    /**
     * Create a connection
     * <p>
     * This constructor takes a default connection timeout of 5 seconds, and
     * request timeout of 10 seconds
     * </p>
     *
     * @param url
     *            the endpoint and WSDL URL
     * @param serviceName
     *            the name of the service
     * @throws MalformedURLException
     *             thrown in case the URL has an invalid syntax
     */
    public Connection ( final String url, final String serviceName ) throws MalformedURLException
    {
        this ( new URL ( url + "?wsdl" ), new URL ( url ), new QName ( "http://opcfoundation.org/webservices/XMLDA/1.0/", serviceName ), serviceName + "Soap", 5_000, 10_000 );
    }

    /**
     * Create a connection with more control over the connection parameters
     *
     * @param wsdlUrl
     *            the URL of the WSDL file. This may be <code>null</code> in
     *            which case the serverUrl is used. This URL may point to a file
     *            system local resource.
     * @param serverUrl
     *            The URL to the server endpoint. This URL will override any
     *            endpoint in the WSDL file.
     * @param serviceName
     *            The service name
     * @param localPortName
     *            The local port name
     * @param connectTimeout
     *            The connection timeout (in milliseconds)
     * @param requestTimeout
     *            The request timeout (in millisedonds)
     */
    public Connection ( final URL wsdlUrl, final URL serverUrl, final QName serviceName, final String localPortName, final int connectTimeout, final int requestTimeout )
    {
        if ( serverUrl == null )
        {
            throw new NullPointerException ( "'serverUrl' must not be null" );
        }

        this.wsdlUrl = wsdlUrl;
        this.serverUrl = serverUrl;
        this.name = serverUrl + "/" + localPortName;

        this.connectTimeout = connectTimeout;
        this.requestTimeout = requestTimeout;

        this.serviceName = serviceName;
        this.localPortName = localPortName;

        this.executor = Executors.newSingleThreadScheduledExecutor ( new NamedThreadFactory ( this.name + "/Requests" ) );
        this.eventExecutor = Executors.newSingleThreadExecutor ( new NamedThreadFactory ( this.name + "/Events" ) );
    }

    protected org.opcfoundation.webservices.xmlda._1.Service createPort ()
    {
        if ( this.soap != null )
        {
            return this.soap;
        }

        final QName portName = new QName ( this.serviceName.getNamespaceURI (), this.localPortName );

        final Service service = Service.create ( this.wsdlUrl == null ? this.serverUrl : this.wsdlUrl, this.serviceName );
        this.soap = service.getPort ( portName, org.opcfoundation.webservices.xmlda._1.Service.class );

        final BindingProvider bindingProvider = (BindingProvider)this.soap;

        final Map<String, Object> context = bindingProvider.getRequestContext ();

        context.put ( "javax.xml.ws.client.connectionTimeout", this.connectTimeout );
        context.put ( "javax.xml.ws.client.receiveTimeout", this.requestTimeout );
        context.put ( "com.sun.xml.internal.ws.connect.timeout", this.connectTimeout );
        context.put ( "com.sun.xml.internal.ws.request.timeout", this.requestTimeout );
        context.put ( BindingProvider.ENDPOINT_ADDRESS_PROPERTY, this.serverUrl.toString () );

        return this.soap;
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
            return clazz.cast ( createPort () );
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
