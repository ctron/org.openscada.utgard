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

    private OPCENUMSCOPE ( final int id )
    {
        this._id = id;
    }

    public int id ()
    {
        return this._id;
    }

    public static OPCENUMSCOPE fromID ( final int id )
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
