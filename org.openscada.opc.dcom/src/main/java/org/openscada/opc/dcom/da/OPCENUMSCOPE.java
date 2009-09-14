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

public enum OPCENUMSCOPE
{
    OPC_ENUM_PRIVATE_CONNECTIONS ( 1 ),
    OPC_ENUM_PUBLIC_CONNECTIONS ( 2 ),
    OPC_ENUM_ALL_CONNECTIONS ( 3 ),
    OPC_ENUM_PRIVATE ( 4 ),
    OPC_ENUM_PUBLIC ( 5 ),
    OPC_ENUM_ALL ( 6 ),
    OPC_ENUM_UNKNOWN ( 0 );

    private int _id;

    private OPCENUMSCOPE ( int id )
    {
        _id = id;
    }

    public int id ()
    {
        return _id;
    }

    public static OPCENUMSCOPE fromID ( int id )
    {
        switch ( id )
        {
        case 1:
            return OPC_ENUM_PRIVATE_CONNECTIONS;
        case 2:
            return OPC_ENUM_PUBLIC_CONNECTIONS;
        case 3:
            return OPC_ENUM_ALL_CONNECTIONS;
        case 4:
            return OPC_ENUM_PRIVATE;
        case 5:
            return OPC_ENUM_PUBLIC;
        case 6:
            return OPC_ENUM_ALL;
        default:
            return OPC_ENUM_UNKNOWN;
        }
    }
}
