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

public enum OPCNAMESPACETYPE
{
    OPC_NS_HIERARCHIAL ( 1 ),
    OPC_NS_FLAT ( 2 ),
    OPC_NS_UNKNOWN ( 0 );

    private int _id;

    private OPCNAMESPACETYPE ( final int id )
    {
        this._id = id;
    }

    public int id ()
    {
        return this._id;
    }

    public static OPCNAMESPACETYPE fromID ( final int id )
    {
        switch ( id )
        {
        case 1:
            return OPC_NS_HIERARCHIAL;
        case 2:
            return OPC_NS_FLAT;
        default:
            return OPC_NS_UNKNOWN;
        }
    }
}
