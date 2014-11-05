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

    private OPCSERVERSTATE ( final int id )
    {
        this._id = id;
    }

    public int id ()
    {
        return this._id;
    }

    public static OPCSERVERSTATE fromID ( final int id )
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
