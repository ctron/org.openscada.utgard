/*
 * This file is part of the OpenSCADA project
 * Copyright (C) 2006 inavare GmbH (http://inavare.com)
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
        return _clientHandle;
    }

    public void setClientHandle ( int clientHandle )
    {
        _clientHandle = clientHandle;
    }

    public short getQuality ()
    {
        return _quality;
    }

    public void setQuality ( short quality )
    {
        _quality = quality;
    }

    public short getReserved ()
    {
        return _reserved;
    }

    public void setReserved ( short reserved )
    {
        _reserved = reserved;
    }

    public FILETIME getTimestamp ()
    {
        return _timestamp;
    }

    public void setTimestamp ( FILETIME timestamp )
    {
        _timestamp = timestamp;
    }

    public JIVariant getValue ()
    {
        return _value;
    }

    public void setValue ( JIVariant value )
    {
        _value = value;
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

    public static OPCITEMSTATE fromStruct ( JIStruct struct )
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
