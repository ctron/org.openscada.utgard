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

public enum OPCSERVERSTATE
{
    OPC_STATUS_RUNNING ( 1 ),
    OPC_STATUS_FAILED ( 2 ),
    OPC_STATUS_NOCONFIG ( 3 ),
    OPC_STATUS_SUSPENDED ( 4 ),
    OPC_STATUS_TEST ( 5 ),
    OPC_STATUS_COMM_FAULT ( 6 ),
    OPC_STATUS_UNKNOWN ( 0 );

    private int _id;

    private OPCSERVERSTATE ( int id )
    {
        _id = id;
    }

    public int id ()
    {
        return _id;
    }

    public static OPCSERVERSTATE fromID ( int id )
    {
        switch ( id )
        {
        case 1:
            return OPC_STATUS_RUNNING;
        case 2:
            return OPC_STATUS_FAILED;
        case 3:
            return OPC_STATUS_NOCONFIG;
        case 4:
            return OPC_STATUS_SUSPENDED;
        case 5:
            return OPC_STATUS_TEST;
        case 6:
            return OPC_STATUS_COMM_FAULT;
        default:
            return OPC_STATUS_UNKNOWN;
        }
    }
}
