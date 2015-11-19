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
package org.openscada.opc.xmlda.requests;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.xml.namespace.QName;
import javax.xml.ws.Holder;

import org.opcfoundation.webservices.xmlda._1.ItemValue;
import org.opcfoundation.webservices.xmlda._1.OPCError;
import org.opcfoundation.webservices.xmlda._1.ReadRequestItem;
import org.opcfoundation.webservices.xmlda._1.ReadRequestItemList;
import org.opcfoundation.webservices.xmlda._1.ReplyBase;
import org.opcfoundation.webservices.xmlda._1.ReplyItemList;
import org.opcfoundation.webservices.xmlda._1.RequestOptions;
import org.opcfoundation.webservices.xmlda._1.Service;
import org.openscada.opc.xmlda.Connection;
import org.openscada.opc.xmlda.ItemRequest;
import org.openscada.opc.xmlda.Task;
import org.openscada.opc.xmlda.internal.Helper;

public class ReadRequest implements Task<ReadResponse>
{
    private final Integer maxAge;

    private final List<ItemRequest> items;

    private final RequestOptions options;

    public static class Builder
    {
        private Integer maxAge;

        private List<ItemRequest> items;

        private RequestOptions options;

        public Builder ()
        {
            this.options = new RequestOptions ();
            this.options.setReturnItemName ( true );
            this.options.setReturnItemTime ( true );
            this.options.setReturnItemPath ( true );
            this.options.setReturnErrorText ( true );
            this.options.setReturnDiagnosticInfo ( true );
        }

        public Builder ( final String... itemNames )
        {
            setItemNames ( itemNames );
        }

        public Builder setMaxAge ( final Integer maxAge )
        {
            this.maxAge = maxAge;
            return this;
        }

        public Builder setOptions ( final RequestOptions options )
        {
            this.options = options;
            return this;
        }

        public void setItemNames ( final String... itemNames )
        {
            setItemNames ( Arrays.asList ( itemNames ) );
        }

        public Builder setItemNames ( final Collection<String> items )
        {
            this.items = ItemRequest.makeRequests ( items );
            return this;
        }

        public Builder setItems ( final Collection<ItemRequest> items )
        {
            this.items = new CopyOnWriteArrayList<> ( items );
            return this;
        }

        public ReadRequest build ()
        {
            return new ReadRequest ( this.maxAge, this.items, this.options );
        }
    }

    public ReadRequest ( final Integer maxAge, final List<ItemRequest> items, final RequestOptions options )
    {
        this.maxAge = maxAge;
        this.items = new CopyOnWriteArrayList<> ( items );
        this.options = options;
    }

    @Override
    public ReadResponse process ( final Connection connection )
    {
        // get service

        final Service service = connection.unwrap ( Service.class );

        // prepare request

        final ReadRequestItemList itemList = new ReadRequestItemList ();

        for ( final ItemRequest item : this.items )
        {
            final ReadRequestItem rri = new ReadRequestItem ();
            rri.setItemName ( item.getItemName () );
            rri.setClientItemHandle ( item.getClientHandle () );
            rri.setMaxAge ( this.maxAge );
            itemList.getItems ().add ( rri );
        }

        // prepare holders

        final Holder<ReplyBase> readResult = new Holder<> ();
        final Holder<ReplyItemList> rItemList = new Holder<> ();
        final Holder<List<OPCError>> errors = new Holder<> ();

        // call

        service.read ( this.options, itemList, readResult, rItemList, errors );

        // build response

        final ReadResponse.Builder builder = new ReadResponse.Builder ();

        builder.setBase ( readResult.value );

        final Map<QName, String> errorMap = Helper.mapErrors ( errors.value );

        for ( final ItemValue value : rItemList.value.getItems () )
        {
            builder.addValue ( value.getClientItemHandle (), Helper.convertValue ( value, errorMap ) );
        }

        // return

        return builder.build ();
    }
}
