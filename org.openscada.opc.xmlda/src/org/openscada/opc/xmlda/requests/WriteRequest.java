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
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.namespace.QName;

import org.opcfoundation.webservices.xmlda._1.RequestOptions;
import org.opcfoundation.webservices.xmlda._1.Service;
import org.opcfoundation.webservices.xmlda._1.Write;
import org.opcfoundation.webservices.xmlda._1.WriteRequestItemList;
import org.openscada.opc.xmlda.Connection;
import org.openscada.opc.xmlda.OpcType;
import org.openscada.opc.xmlda.Task;
import org.openscada.opc.xmlda.internal.Helper;
import org.openscada.opc.xmlda.requests.WriteResponse.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WriteRequest implements Task<WriteResponse>
{
    private final static Logger logger = LoggerFactory.getLogger ( WriteRequest.class );

    private final List<ItemValue> values;

    private DatatypeFactory factory;

    public WriteRequest ( final ItemValue... values )
    {
        this ( Arrays.asList ( values ) );
    }

    public WriteRequest ( final List<ItemValue> values )
    {
        this.values = values;

        try
        {
            this.factory = DatatypeFactory.newInstance ();
        }
        catch ( final DatatypeConfigurationException e )
        {
            throw new IllegalStateException ( e );
        }
    }

    public List<ItemValue> getValues ()
    {
        return this.values;
    }

    @Override
    public WriteResponse process ( final Connection connection )
    {
        // get service

        final Service service = connection.unwrap ( Service.class );

        // prepare request

        final Write write = new Write ();

        final WriteRequestItemList list = new WriteRequestItemList ();

        for ( final ItemValue value : this.values )
        {
            final org.opcfoundation.webservices.xmlda._1.ItemValue iv = new org.opcfoundation.webservices.xmlda._1.ItemValue ();

            iv.setItemName ( value.getItemName () );
            iv.setItemPath ( value.getItemPath () );

            final GregorianCalendar ts = value.getTimestamp ();
            if ( ts != null )
            {
                iv.setTimestamp ( this.factory.newXMLGregorianCalendar ( ts ) );
            }

            if ( value.getValue () != null && value.getValue () instanceof String && value.getOpcType () != OpcType.UNDEFINED )
            {
                iv.setValue ( Helper.parseStringValue ( (String)value.getValue (), value.getOpcType () ) );
            }
            else
            {
                iv.setValue ( value.getValue () );
            }

            list.getItems ().add ( iv );
        }

        write.setItemList ( list );
        write.setReturnValuesOnReply ( false ); // on the next poll

        // set options

        final RequestOptions options = new RequestOptions ();
        options.setReturnErrorText ( true );
        options.setReturnDiagnosticInfo ( true );

        write.setOptions ( options );

        // call

        final org.opcfoundation.webservices.xmlda._1.WriteResponse result = service.write ( write );

        // build response

        final Builder builder = new WriteResponse.Builder ();

        builder.setBase ( result.getWriteResult () );

        final Map<QName, String> errorMap = Helper.mapErrors ( result.getErrors () );

        for ( final org.opcfoundation.webservices.xmlda._1.ItemValue value : result.getRItemList ().getItems () )
        {
            // TODO: fetch write errors
            logger.info ( "{}", value.getResultID () );
            builder.addValue ( Helper.convertValue ( value, errorMap ) );
        }

        // return

        return builder.build ();
    }
}
