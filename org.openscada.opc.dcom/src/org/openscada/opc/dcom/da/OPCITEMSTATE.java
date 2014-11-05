/*
 * This file is part of the OpenSCADA project
 * Copyright (C) 2006-2010 TH4 SYSTEMS GmbH (http://th4-systems.com)
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openscada.opc.dcom.da;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIStruct;
import org.jinterop.dcom.core.JIVariant;
import org.openscada.opc.dcom.common.FILETIME;

public class OPCITEMSTATE
{
    private int _clientHandle = 0;

    private FILETIME _timestamp = null;

    private short _quality = 0;

    private short _reserved = 0;

    private JIVariant _value = null;

    public int getClientHandle ()
    {
        return this._clientHandle;
    }

    public void setClientHandle ( final int clientHandle )
    {
        this._clientHandle = clientHandle;
    }

    public short getQuality ()
    {
        return this._quality;
    }

    public void setQuality ( final short quality )
    {
        this._quality = quality;
    }

    public short getReserved ()
    {
        return this._reserved;
    }

    public void setReserved ( final short reserved )
    {
        this._reserved = reserved;
    }

    public FILETIME getTimestamp ()
    {
        return this._timestamp;
    }

    public void setTimestamp ( final FILETIME timestamp )
    {
        this._timestamp = timestamp;
    }

    public JIVariant getValue ()
    {
        return this._value;
    }

    public void setValue ( final JIVariant value )
    {
        this._value = value;
    }

    public static JIStruct getStruct () throws JIException
    {
        JIStruct struct = new JIStruct ();

        struct.addMember ( Integer.class );
        struct.addMember ( FILETIME.getStruct () );
        struct.addMember ( Short.class );
        struct.addMember ( Short.class );
        struct.addMember ( JIVariant.class );

        return struct;
    }

    public static OPCITEMSTATE fromStruct ( final JIStruct struct )
    {
        OPCITEMSTATE itemState = new OPCITEMSTATE ();

        itemState.setClientHandle ( (Integer)struct.getMember ( 0 ) );
        itemState.setTimestamp ( FILETIME.fromStruct ( (JIStruct)struct.getMember ( 1 ) ) );
        itemState.setQuality ( (Short)struct.getMember ( 2 ) );
        itemState.setReserved ( (Short)struct.getMember ( 3 ) );
        itemState.setValue ( (JIVariant)struct.getMember ( 4 ) );

        return itemState;
    }
}
