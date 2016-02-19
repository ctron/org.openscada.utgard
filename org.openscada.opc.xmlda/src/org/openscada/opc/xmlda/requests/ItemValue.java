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

import java.util.GregorianCalendar;

import org.openscada.opc.xmlda.OpcType;
import org.openscada.opc.xmlda.internal.Helper;

public class ItemValue
{
    private final String itemName;

    private final String itemPath;

    private final Object value;

    private final State state;

    private final GregorianCalendar timestamp;

    private final ErrorInformation errorInformation;

    private final OpcType opcType;

    public ItemValue ( final String itemName, final String itemPath, final Object value, final State state, final GregorianCalendar timestamp, final ErrorInformation errorInformation )
    {
        this ( itemName, itemPath, value, OpcType.UNDEFINED, state, timestamp, errorInformation );
    }

    public ItemValue ( final String itemName, final String itemPath, final Object value, final OpcType opcType, final State state, final GregorianCalendar timestamp, final ErrorInformation errorInformation )
    {
        this.itemName = itemName;
        this.itemPath = itemPath;
        this.value = value;
        this.opcType = opcType;
        this.state = state;
        this.timestamp = timestamp;
        this.errorInformation = errorInformation;
    }

    public String getItemName ()
    {
        return this.itemName;
    }

    public String getItemPath ()
    {
        return this.itemPath;
    }

    public State getState ()
    {
        return this.state;
    }

    public GregorianCalendar getTimestamp ()
    {
        return this.timestamp;
    }

    public Object getValue ()
    {
        return this.value;
    }

    public OpcType getOpcType ()
    {
        return opcType;
    }

    public ErrorInformation getErrorInformation ()
    {
        return this.errorInformation;
    }

    @Override
    public String toString ()
    {
        if ( this.errorInformation != null )
        {
            final StringBuilder sb = new StringBuilder ( toBaseString () );

            sb.append ( System.lineSeparator () );
            sb.append ( "  " ).append ( this.errorInformation );

            return sb.toString ();
        }
        else
        {
            return toBaseString ();
        }
    }

    private String toBaseString ()
    {
        return String.format ( "[ItemValue - name: %s, value: %s, timestamp: %s, state: %s ]", this.itemName, this.value, Helper.toStringLocal ( this.timestamp ), this.state );
    }
}
