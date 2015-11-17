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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

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
import org.openscada.opc.xmlda.Task;
import org.openscada.opc.xmlda.internal.Helper;
import org.openscada.opc.xmlda.requests.ReadResponse.Builder;

public class ReadRequest implements Task<ReadResponse>
{
    private final Integer maxAge;

    private final Set<String> itemNames;

    public ReadRequest ( final String... itemNames )
    {
        this ( null, itemNames );
    }

    public ReadRequest ( final Integer maxAge, final String... itemNames )
    {
        this.maxAge = maxAge;
        this.itemNames = new HashSet<> ( Arrays.asList ( itemNames ) );
    }

    public ReadRequest ( final Set<String> itemNames )
    {
        this ( null, itemNames );
    }

    public ReadRequest ( final Integer maxAge, final Set<String> itemNames )
    {
        this.maxAge = maxAge;
        this.itemNames = new CopyOnWriteArraySet<> ( itemNames );
    }

    public Set<String> getItemNames ()
    {
        return this.itemNames;
    }

    @Override
    public ReadResponse process ( final Connection connection )
    {
        // get service

        final Service service = connection.unwrap ( Service.class );

        // prepare request

        final RequestOptions options = new RequestOptions ();
        options.setReturnItemName ( true );
        options.setReturnItemTime ( true );
        options.setReturnItemPath ( true );
        options.setReturnErrorText ( true );
        options.setReturnDiagnosticInfo ( true );

        final ReadRequestItemList itemList = new ReadRequestItemList ();

        for ( final String itemName : this.itemNames )
        {
            final ReadRequestItem rri = new ReadRequestItem ();
            rri.setItemName ( itemName );
            rri.setMaxAge ( this.maxAge );
            itemList.getItems ().add ( rri );
        }

        // prepare holders

        final Holder<ReplyBase> readResult = new Holder<> ();
        final Holder<ReplyItemList> rItemList = new Holder<> ();
        final Holder<List<OPCError>> errors = new Holder<> ();

        // call

        service.read ( options, itemList, readResult, rItemList, errors );

        // build response

        final Builder builder = new ReadResponse.Builder ();

        builder.setBase ( readResult.value );

        final Map<QName, String> errorMap = Helper.mapErrors ( errors.value );

        for ( final ItemValue value : rItemList.value.getItems () )
        {
            builder.addValue ( Helper.convertValue ( value, errorMap ) );
        }

        // return

        return builder.build ();
    }
}
