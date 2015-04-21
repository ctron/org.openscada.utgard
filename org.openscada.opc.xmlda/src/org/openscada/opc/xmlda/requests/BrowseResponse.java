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

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BrowseResponse extends BaseResponse
{
    private String continuationPoint;

    private BrowseRequest request;

    private List<BrowseEntry> entries = Collections.emptyList ();

    private BrowseResponse ()
    {
    }

    public String getContinuationPoint ()
    {
        return this.continuationPoint;
    }

    public BrowseRequest makeContinuationRequest ()
    {
        if ( this.continuationPoint == null || this.request == null )
        {
            return null;
        }

        return new BrowseRequest ( this.request.getItemName (), this.request.getItemPath (), this.request.getBrowserType (), this.request.getMaxElementsReturned (), this.request.isFullProperties (), this.continuationPoint );
    }

    public List<BrowseEntry> getEntries ()
    {
        return this.entries;
    }

    public static class Builder extends BaseResponse.Builder
    {
        private BrowseRequest request;

        private String continuationPoint;

        private final List<BrowseEntry> entries = new LinkedList<> ();

        protected void apply ( final BrowseResponse result )
        {
            super.apply ( result );

            result.continuationPoint = this.continuationPoint;
            result.request = this.request;
            result.entries = Collections.unmodifiableList ( new ArrayList<> ( this.entries ) );
        }

        public BrowseResponse build ()
        {
            final BrowseResponse result = new BrowseResponse ();

            apply ( result );

            return result;
        }

        public void setContinuationPoint ( final String continuationPoint )
        {
            this.continuationPoint = continuationPoint;
        }

        public void setRequest ( final BrowseRequest request )
        {
            this.request = request;
        }

        public void addEntry ( final BrowseEntry entry )
        {
            this.entries.add ( entry );
        }
    }
}
