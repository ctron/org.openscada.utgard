/*
 * This file is part of the OpenSCADA project
 * Copyright (C) 2006-2012 TH4 SYSTEMS GmbH (http://th4-systems.com)
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openscada.opc.dcom.da;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIFlags;
import org.jinterop.dcom.core.JIPointer;
import org.jinterop.dcom.core.JIString;
import org.jinterop.dcom.core.JIStruct;
import org.jinterop.dcom.core.JIVariant;

public class OPCITEMDEF
{
    private String accessPath = "";

    private String itemID = "";

    private boolean active = true;

    private int clientHandle;

    private short requestedDataType = JIVariant.VT_EMPTY;

    private short reserved;

    public String getAccessPath ()
    {
        return this.accessPath;
    }

    public void setAccessPath ( final String accessPath )
    {
        this.accessPath = accessPath;
    }

    public int getClientHandle ()
    {
        return this.clientHandle;
    }

    public void setClientHandle ( final int clientHandle )
    {
        this.clientHandle = clientHandle;
    }

    public boolean isActive ()
    {
        return this.active;
    }

    public void setActive ( final boolean active )
    {
        this.active = active;
    }

    public String getItemID ()
    {
        return this.itemID;
    }

    public void setItemID ( final String itemID )
    {
        this.itemID = itemID;
    }

    public short getRequestedDataType ()
    {
        return this.requestedDataType;
    }

    public void setRequestedDataType ( final short requestedDataType )
    {
        this.requestedDataType = requestedDataType;
    }

    public short getReserved ()
    {
        return this.reserved;
    }

    public void setReserved ( final short reserved )
    {
        this.reserved = reserved;
    }

    /**
     * Convert to structure to a J-Interop structure
     * 
     * @return the j-interop structe
     * @throws JIException
     */
    public JIStruct toStruct () throws JIException
    {
        final JIStruct struct = new JIStruct ();
        struct.addMember ( new JIString ( getAccessPath (), JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR ) );
        struct.addMember ( new JIString ( getItemID (), JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR ) );
        struct.addMember ( new Integer ( isActive () ? 1 : 0 ) );
        struct.addMember ( Integer.valueOf ( getClientHandle () ) );

        struct.addMember ( Integer.valueOf ( 0 ) ); // blob size
        struct.addMember ( new JIPointer ( null ) ); // blob

        struct.addMember ( Short.valueOf ( getRequestedDataType () ) );
        struct.addMember ( Short.valueOf ( getReserved () ) );
        return struct;
    }
}
