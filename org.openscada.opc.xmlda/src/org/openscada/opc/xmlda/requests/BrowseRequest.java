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

import org.opcfoundation.webservices.xmlda._1.Browse;
import org.opcfoundation.webservices.xmlda._1.BrowseFilter;
import org.opcfoundation.webservices.xmlda._1.Service;
import org.openscada.opc.xmlda.Connection;
import org.openscada.opc.xmlda.Task;
import org.openscada.opc.xmlda.internal.Helper;

public class BrowseRequest implements Task<BrowseResponse>
{
    private final String itemName;

    private final String itemPath;

    private final BrowseType browserType;

    private final Integer maxElementsReturned;

    private final String continuationPoint;

    private final boolean fullProperties;

    public BrowseRequest ( final String itemName, final String itemPath, final BrowseType browserType, final Integer maxElementsReturned, final boolean fullProperties )
    {
        this ( itemName, itemPath, browserType, maxElementsReturned, fullProperties, null );
    }

    public BrowseRequest ( final String itemName, final String itemPath, final BrowseType browserType, final Integer maxElementsReturned, final boolean fullProperties, final String continuationPoint )
    {
        this.itemName = itemName;
        this.itemPath = itemPath;
        this.browserType = browserType;
        this.maxElementsReturned = maxElementsReturned;
        this.continuationPoint = continuationPoint;
        this.fullProperties = fullProperties;
    }

    public String getItemName ()
    {
        return this.itemName;
    }

    public String getItemPath ()
    {
        return this.itemPath;
    }

    public BrowseType getBrowserType ()
    {
        return this.browserType;
    }

    public Integer getMaxElementsReturned ()
    {
        return this.maxElementsReturned;
    }

    public String getContinuationPoint ()
    {
        return this.continuationPoint;
    }

    public boolean isFullProperties ()
    {
        return this.fullProperties;
    }

    @Override
    public BrowseResponse process ( final Connection connection ) throws Exception
    {
        // get service

        final Service service = connection.unwrap ( Service.class );

        // make the request

        final Browse request = new Browse ();

        // set parameters

        request.setItemName ( this.itemName );

        request.setContinuationPoint ( this.continuationPoint );
        if ( this.maxElementsReturned >= 0 )
        {
            request.setMaxElementsReturned ( this.maxElementsReturned );
        }

        if ( this.browserType != null )
        {
            switch ( this.browserType )
            {
                case ALL:
                    request.setBrowseFilter ( BrowseFilter.ALL );
                    break;

                case ITEM:
                    request.setBrowseFilter ( BrowseFilter.ITEM );
                    break;

                case BRANCH:
                    request.setBrowseFilter ( BrowseFilter.BRANCH );
                    break;
            }
        }

        // set options

        request.setReturnErrorText ( true );

        if ( this.fullProperties )
        {
            request.setReturnAllProperties ( true );
            request.setReturnPropertyValues ( true );
        }

        // make the call & return

        return Helper.convert ( service.browse ( request ), this );
    }
}
