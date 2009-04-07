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

public class PropertyDescription
{
    private int _id = -1;

    private String _description = "";

    private short _varType = 0;

    public String getDescription ()
    {
        return _description;
    }

    public void setDescription ( String description )
    {
        _description = description;
    }

    public int getId ()
    {
        return _id;
    }

    public void setId ( int id )
    {
        _id = id;
    }

    public short getVarType ()
    {
        return _varType;
    }

    public void setVarType ( short varType )
    {
        _varType = varType;
    }
}
