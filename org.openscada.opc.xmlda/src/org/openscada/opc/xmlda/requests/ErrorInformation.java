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

public class ErrorInformation
{
    private final QName id;

    private final String message;

    public ErrorInformation ( final QName id, final String message )
    {
        this.id = id;
        this.message = message;
    }

    public QName getId ()
    {
        return this.id;
    }

    public String getStringId ()
    {
        return this.id.toString ();
    }

    public String getMessage ()
    {
        return this.message;
    }

    @Override
    public String toString ()
    {
        return String.format ( "[%s: %s]", this.id, this.message );
    }
}
