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

import java.util.HashSet;

import org.opcfoundation.webservices.xmlda._1.GetStatus;
import org.opcfoundation.webservices.xmlda._1.GetStatusResponse;
import org.opcfoundation.webservices.xmlda._1.ServerStatus;
import org.opcfoundation.webservices.xmlda._1.Service;
import org.openscada.opc.xmlda.Connection;
import org.openscada.opc.xmlda.Task;
import org.openscada.opc.xmlda.internal.Helper;
import org.openscada.opc.xmlda.requests.GetStatusResponse.Builder;

public class GetStatusRequest implements Task<org.openscada.opc.xmlda.requests.GetStatusResponse>
{
    private final String localeId;

    public GetStatusRequest ()
    {
        this ( null );
    }

    public GetStatusRequest ( final String localeId )
    {
        this.localeId = localeId;
    }

    public String getLocaleId ()
    {
        return this.localeId;
    }

    @Override
    public org.openscada.opc.xmlda.requests.GetStatusResponse process ( final Connection connection )
    {
        // get service

        final Service service = connection.unwrap ( Service.class );

        // prepare request

        final GetStatus parameters = new GetStatus ();

        // call

        final GetStatusResponse result = service.getStatus ( parameters );

        // build response

        final Builder builder = new org.openscada.opc.xmlda.requests.GetStatusResponse.Builder ();

        builder.setBase ( result.getGetStatusResult () );

        if ( result.getStatus () != null )
        {
            final ServerStatus state = result.getStatus ();
            builder.setProductVersion ( state.getProductVersion () );
            builder.setStartTime ( Helper.convert ( state.getStartTime () ) );
            builder.setStatusInformation ( state.getStatusInfo () );
            builder.setVendorInformation ( state.getVendorInfo () );
            builder.setSupportedLocales ( new HashSet<> ( state.getSupportedLocaleIDs () ) );

            // FIXME: add interface versions
        }

        // return

        return builder.build ();
    }
}
