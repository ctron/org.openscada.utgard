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

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.scada.utils.str.StringHelper;
import org.openscada.opc.xmlda.internal.Helper;

public class GetStatusResponse extends BaseResponse
{
    private String productVersion;

    private Calendar startTime;

    private String statusInformation;

    private String vendorInformation;

    private SortedSet<String> supportedLocales = new TreeSet<> ();

    private GetStatusResponse ()
    {
    }

    public String getProductVersion ()
    {
        return this.productVersion;
    }

    public Calendar getStartTime ()
    {
        return this.startTime;
    }

    public String getStatusInformation ()
    {
        return this.statusInformation;
    }

    public String getVendorInformation ()
    {
        return this.vendorInformation;
    }

    public SortedSet<String> getSupportedLocales ()
    {
        return this.supportedLocales;
    }

    static class Builder extends BaseResponse.Builder
    {
        private String productVersion;

        private Calendar startTime;

        private String statusInformation;

        private String vendorInformation;

        private Set<String> supportedLocales = new HashSet<> ();

        protected void apply ( final GetStatusResponse result )
        {
            super.apply ( result );

            result.productVersion = this.productVersion;
            result.startTime = this.startTime;
            result.statusInformation = this.statusInformation;
            result.vendorInformation = this.vendorInformation;
            result.supportedLocales = new TreeSet<> ( this.supportedLocales );
        }

        public void setProductVersion ( final String productVersion )
        {
            this.productVersion = productVersion;
        }

        public void setStartTime ( final Calendar startTime )
        {
            this.startTime = startTime;
        }

        public void setStatusInformation ( final String statusInformation )
        {
            this.statusInformation = statusInformation;
        }

        public void setSupportedLocales ( final Set<String> supportedLocales )
        {
            this.supportedLocales = supportedLocales;
        }

        public void setVendorInformation ( final String vendorInformation )
        {
            this.vendorInformation = vendorInformation;
        }

        public GetStatusResponse build ()
        {
            final GetStatusResponse result = new GetStatusResponse ();
            apply ( result );
            return result;
        }

    }

    @Override
    public String toString ()
    {
        final StringBuilder sb = new StringBuilder ();
        sb.append ( "GetStatusResponse" + NL );
        sb.append ( LINE + NL );

        sb.append ( "  ProductVersion: " ).append ( this.productVersion ).append ( NL );
        sb.append ( "  StartTime: " ).append ( Helper.toStringLocal ( this.startTime ) ).append ( NL );
        sb.append ( "  StatusInformation: " ).append ( this.statusInformation ).append ( NL );
        sb.append ( "  VendorInformation: " ).append ( this.vendorInformation ).append ( NL );

        sb.append ( "  SupportedLocales: " ).append ( StringHelper.join ( this.supportedLocales, ", " ) ).append ( NL );

        sb.append ( super.toString () );

        return sb.toString ();
    }
}
