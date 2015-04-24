package org.openscada.opc.xmlda.tests;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import javax.xml.ws.Service;

import org.opcfoundation.webservices.xmlda._1.Browse;
import org.opcfoundation.webservices.xmlda._1.BrowseElement;
import org.opcfoundation.webservices.xmlda._1.BrowseFilter;
import org.opcfoundation.webservices.xmlda._1.BrowseResponse;
import org.opcfoundation.webservices.xmlda._1.GetStatus;
import org.opcfoundation.webservices.xmlda._1.GetStatusResponse;
import org.opcfoundation.webservices.xmlda._1.ItemProperty;
import org.opcfoundation.webservices.xmlda._1.ItemValue;
import org.opcfoundation.webservices.xmlda._1.OPCError;
import org.opcfoundation.webservices.xmlda._1.ReadRequestItem;
import org.opcfoundation.webservices.xmlda._1.ReadRequestItemList;
import org.opcfoundation.webservices.xmlda._1.ReplyBase;
import org.opcfoundation.webservices.xmlda._1.ReplyItemList;
import org.opcfoundation.webservices.xmlda._1.RequestOptions;
import org.opcfoundation.webservices.xmlda._1.ServerStatus;
import org.opcfoundation.webservices.xmlda._1.Subscribe;
import org.opcfoundation.webservices.xmlda._1.SubscribePolledRefreshReplyItemList;
import org.opcfoundation.webservices.xmlda._1.SubscribeRequestItem;
import org.opcfoundation.webservices.xmlda._1.SubscribeRequestItemList;
import org.opcfoundation.webservices.xmlda._1.SubscribeResponse;
import org.opcfoundation.webservices.xmlda._1.SubscriptionCancel;
import org.opcfoundation.webservices.xmlda._1.SubscriptionPolledRefresh;
import org.opcfoundation.webservices.xmlda._1.SubscriptionPolledRefreshResponse;

public class ConnectionTest implements AutoCloseable
{
    private final org.opcfoundation.webservices.xmlda._1.Service soap;

    private final Service service;

    private final String clientHandle;

    public ConnectionTest ( final String url, final String serviceName ) throws MalformedURLException
    {
        this ( new URL ( url ), new QName ( "http://opcfoundation.org/webservices/XMLDA/1.0/", serviceName ), serviceName + "Soap" );
        System.out.println ( "    ==== >>>> " + url );
    }

    public ConnectionTest ( final URL url, final QName serviceName, final String portName )
    {
        this.service = Service.create ( url, serviceName );
        this.soap = this.service.getPort ( new QName ( serviceName.getNamespaceURI (), portName ), org.opcfoundation.webservices.xmlda._1.Service.class );

        final BindingProvider bindingProvider = (BindingProvider)this.soap;
        final Map<String, Object> context = bindingProvider.getRequestContext ();

        context.put ( BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url.toString () );

        this.clientHandle = UUID.randomUUID ().toString ();
    }

    public void dumpState ()
    {
        final GetStatus params = new GetStatus ();
        params.setClientRequestHandle ( UUID.randomUUID ().toString () );

        final GetStatusResponse result = this.soap.getStatus ( params );
        final ServerStatus state = result.getStatus ();

        System.out.format ( "Status Info: %s (%tc)%n", state.getStatusInfo (), state.getStartTime ().toGregorianCalendar () );
        System.out.format ( "Vendor Info: %s%n", state.getVendorInfo () );
        System.out.format ( "Version: %s%n", state.getProductVersion () );

        {
            dumpList ( 1, this.soap.browse ( makeBrowseRequest ( null ) ) );
        }
    }

    private Browse makeBrowseRequest ( final String path )
    {
        final Browse b = new Browse ();

        b.setClientRequestHandle ( this.clientHandle );
        b.setReturnAllProperties ( true );
        b.setReturnPropertyValues ( true );

        if ( path != null )
        {
            b.setBrowseFilter ( BrowseFilter.ALL );
            b.setItemName ( path );
        }
        else
        {
            b.setBrowseFilter ( BrowseFilter.ALL );
        }

        return b;
    }

    private void addItem ( final SubscribeRequestItemList srl, final String name )
    {
        final SubscribeRequestItem sri = new SubscribeRequestItem ();
        sri.setItemName ( name );
        srl.getItems ().add ( sri );
    }

    private void dumpList ( final int level, final BrowseResponse r )
    {
        final StringBuilder sb = new StringBuilder ();
        for ( int i = 0; i < level; i++ )
        {
            sb.append ( '\t' );
        }
        final String prefix = sb.toString ();

        for ( final BrowseElement be : r.getElements () )
        {
            System.out.format ( prefix + "%s%s - %s - %s%n", be.getName (), be.isHasChildren () ? ">" : "", be.getItemName (), be.getItemPath () );

            if ( be.isHasChildren () )
            {
                dumpList ( level + 1, this.soap.browse ( makeBrowseRequest ( be.getItemName () ) ) );
            }

            for ( final ItemProperty ip : be.getProperties () )
            {
                System.out.format ( prefix + "\t%s (%s) = %s  - %s%n", ip.getName (), ip.getItemName (), ip.getValue (), ip.getDescription () );
            }
        }
    }

    public Object read ( final String itemName )
    {
        final RequestOptions options = new RequestOptions ();
        options.setClientRequestHandle ( this.clientHandle );

        final ReadRequestItemList itemList = new ReadRequestItemList ();
        itemList.getItems ().add ( makeReadRequest ( itemName ) );

        final Holder<ReplyBase> readResult = new Holder<> ();
        final Holder<ReplyItemList> rItemList = new Holder<> ();
        final Holder<List<OPCError>> errors = new Holder<> ();

        this.soap.read ( options, itemList, readResult, rItemList, errors );

        dumpResultList ( rItemList.value );
        final ItemValue i = rItemList.value.getItems ().get ( 0 );

        dumpErrors ( errors.value );

        return i.getValue ();
    }

    private ReadRequestItem makeReadRequest ( final String itemName )
    {
        final ReadRequestItem result = new ReadRequestItem ();

        result.setItemName ( itemName );

        return result;
    }

    public void subscribe ( final String itemName )
    {
        final Subscribe s = new Subscribe ();
        final RequestOptions ro = new RequestOptions ();
        ro.setClientRequestHandle ( this.clientHandle );
        ro.setReturnItemTime ( true );

        s.setOptions ( ro );
        s.setReturnValuesOnReply ( false );
        s.setSubscriptionPingRate ( 25_000 );

        final SubscribeRequestItemList srl = new SubscribeRequestItemList ();

        srl.setEnableBuffering ( true );
        addItem ( srl, itemName );
        s.setItemList ( srl );

        System.out.println ( "Subscribe" );

        final SubscribeResponse result = this.soap.subscribe ( s );
        final String sh = result.getServerSubHandle ();

        final long start = System.currentTimeMillis ();

        while ( System.currentTimeMillis () - start < 20_000 )
        {
            final SubscriptionPolledRefresh pr = new SubscriptionPolledRefresh ();
            pr.setOptions ( ro );
            System.out.println ( "Sub Handle: " + sh );
            pr.getServerSubHandles ().add ( result.getServerSubHandle () );
            pr.setWaitTime ( 10_000 );

            System.out.print ( "Polling..." );
            final long startPoll = System.currentTimeMillis ();

            final SubscriptionPolledRefreshResponse pollResult = this.soap.subscriptionPolledRefresh ( pr );

            System.out.format ( "done! Took: %ss%n", ( System.currentTimeMillis () - startPoll ) / 1000 );

            dumpInvalid ( pollResult.getInvalidServerSubHandles () );
            dumpErrors ( pollResult.getErrors () );
            dumpResultList ( pollResult.getRItemList () );
        }

        final SubscriptionCancel cancel = new SubscriptionCancel ();
        cancel.setServerSubHandle ( sh );
        cancel.setClientRequestHandle ( UUID.randomUUID ().toString () );
        this.soap.subscriptionCancel ( cancel );

    }

    private void dumpInvalid ( final List<String> invalidServerSubHandles )
    {
        System.out.println ( "Invalid: " + invalidServerSubHandles );
    }

    private void dumpErrors ( final List<OPCError> errors )
    {
        for ( final OPCError error : errors )
        {
            dumpError ( error );
        }
    }

    private void dumpError ( final OPCError error )
    {
        System.out.format ( "ERR: %s: %s%n", error.getID (), error.getText () );
    }

    private void dumpResultList ( final List<SubscribePolledRefreshReplyItemList> list )
    {
        for ( final SubscribePolledRefreshReplyItemList ll : list )
        {
            System.out.println ( "=== " + ll.getSubscriptionHandle () );
            dumpItems ( ll.getItems () );
        }
    }

    public void dumpResultList ( final ReplyItemList list )
    {
        dumpItems ( list.getItems () );
    }

    private void dumpItems ( final List<ItemValue> items )
    {
        for ( final ItemValue iv : items )
        {
            System.out.format ( "%s = %s%n", iv.getItemName (), iv.getValue () );
        }
    }

    @Override
    public void close () throws Exception
    {
    }
}
