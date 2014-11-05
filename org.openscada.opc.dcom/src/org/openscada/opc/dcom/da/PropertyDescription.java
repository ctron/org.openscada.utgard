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

public class PropertyDescription
{
    private int _id = -1;

    private String _description = "";

    private short _varType = 0;

    public String getDescription ()
    {
        return this._description;
    }

    public void setDescription ( final String description )
    {
        this._description = description;
    }

    public int getId ()
    {
        return this._id;
    }

    public void setId ( final int id )
    {
        this._id = id;
    }

    public short getVarType ()
    {
        return this._varType;
    }

    public void setVarType ( final short varType )
    {
        this._varType = varType;
    }
}
