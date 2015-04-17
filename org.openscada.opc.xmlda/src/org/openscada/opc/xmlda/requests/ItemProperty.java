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

import javax.xml.namespace.QName;

public class ItemProperty
{
    private final QName name;

    private final Object value;

    private final String description;

    private final String itemName;

    private final String itemPath;

    private final QName errorId;

    private final String errorMessage;

    public ItemProperty ( final QName name, final Object value, final String description, final String itemName, final String itemPath, final QName errorId, final String errorMessage )
    {
        this.name = name;
        this.value = value;
        this.description = description;
        this.itemName = itemName;
        this.itemPath = itemPath;
        this.errorId = errorId;
        this.errorMessage = errorMessage;
    }

    public QName getName ()
    {
        return this.name;
    }

    public String getNameAsString ()
    {
        if ( this.name == null )
        {
            return null;
        }
        return this.name.toString ();
    }

    public Object getValue ()
    {
        return this.value;
    }

    public String getDescription ()
    {
        return this.description;
    }

    public String getItemName ()
    {
        return this.itemName;
    }

    public String getItemPath ()
    {
        return this.itemPath;
    }

    public QName getErrorId ()
    {
        return this.errorId;
    }

    public String getErrorMessage ()
    {
        return this.errorMessage;
    }

    @Override
    public String toString ()
    {
        if ( this.errorId == null )
        {
            return String.format ( "[%s = %s]", this.name, this.value );
        }
        else
        {
            return String.format ( "[%s = %s - %s: %s]", this.name, this.value, this.errorId, this.errorMessage );
        }
    }
}
