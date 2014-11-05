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
import org.jinterop.dcom.core.JIArray;
import org.jinterop.dcom.core.JIPointer;
import org.jinterop.dcom.core.JIStruct;
import org.jinterop.dcom.core.JIVariant;

public class OPCITEMRESULT
{
    private int _serverHandle = 0;

    private short _canonicalDataType = JIVariant.VT_EMPTY;

    private short _reserved = 0;

    private int _accessRights = 0;

    public int getAccessRights ()
    {
        return this._accessRights;
    }

    public void setAccessRights ( final int accessRights )
    {
        this._accessRights = accessRights;
    }

    public short getCanonicalDataType ()
    {
        return this._canonicalDataType;
    }

    public void setCanonicalDataType ( final short canonicalDataType )
    {
        this._canonicalDataType = canonicalDataType;
    }

    public short getReserved ()
    {
        return this._reserved;
    }

    public void setReserved ( final short reserved )
    {
        this._reserved = reserved;
    }

    public int getServerHandle ()
    {
        return this._serverHandle;
    }

    public void setServerHandle ( final int serverHandle )
    {
        this._serverHandle = serverHandle;
    }

    public static JIStruct getStruct () throws JIException
    {
        JIStruct struct = new JIStruct ();

        struct.addMember ( Integer.class ); // Server handle
        struct.addMember ( Short.class ); // data type
        struct.addMember ( Short.class ); // reserved
        struct.addMember ( Integer.class ); // access rights
        struct.addMember ( Integer.class ); // blob size
        // grab the normally unused byte array
        struct.addMember ( new JIPointer ( new JIArray ( Byte.class, null, 1, true, false ) ) );

        return struct;
    }

    public static OPCITEMRESULT fromStruct ( final JIStruct struct )
    {
        OPCITEMRESULT result = new OPCITEMRESULT ();

        result.setServerHandle ( new Integer ( (Integer)struct.getMember ( 0 ) ) );
        result.setCanonicalDataType ( new Short ( (Short)struct.getMember ( 1 ) ) );
        result.setReserved ( new Short ( (Short)struct.getMember ( 2 ) ) );
        result.setAccessRights ( new Integer ( (Integer)struct.getMember ( 3 ) ) );

        return result;
    }
}
