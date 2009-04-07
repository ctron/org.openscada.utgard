/*
 * This file is part of the OpenSCADA project
 * Copyright (C) 2006-2009 inavare GmbH (http://inavare.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
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
        return _accessRights;
    }

    public void setAccessRights ( int accessRights )
    {
        _accessRights = accessRights;
    }

    public short getCanonicalDataType ()
    {
        return _canonicalDataType;
    }

    public void setCanonicalDataType ( short canonicalDataType )
    {
        _canonicalDataType = canonicalDataType;
    }

    public short getReserved ()
    {
        return _reserved;
    }

    public void setReserved ( short reserved )
    {
        _reserved = reserved;
    }

    public int getServerHandle ()
    {
        return _serverHandle;
    }

    public void setServerHandle ( int serverHandle )
    {
        _serverHandle = serverHandle;
    }

    public static JIStruct getStruct () throws JIException
    {
        JIStruct struct = new JIStruct ();

        struct.addMember ( Integer.class ); // Server handle
        struct.addMember ( Short.class );   // data type
        struct.addMember ( Short.class );   // reserved
        struct.addMember ( Integer.class ); // access rights
        struct.addMember ( Integer.class ); // blob size
        // grab the normally unused byte array
        struct.addMember ( new JIPointer ( new JIArray ( Byte.class, null, 1, true, false ) ) );

        return struct;
    }

    public static OPCITEMRESULT fromStruct ( JIStruct struct )
    {
        OPCITEMRESULT result = new OPCITEMRESULT ();

        result.setServerHandle ( new Integer ( (Integer)struct.getMember ( 0 ) ) );
        result.setCanonicalDataType ( new Short ( (Short)struct.getMember ( 1 ) ) );
        result.setReserved ( new Short ( (Short)struct.getMember ( 2 ) ) );
        result.setAccessRights ( new Integer ( (Integer)struct.getMember ( 3 ) ) );

        return result;
    }
}
