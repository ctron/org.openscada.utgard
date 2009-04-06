/*
 * This file is part of the OpenSCADA project
 * Copyright (C) 2006-2007 inavare GmbH (http://inavare.com)
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

package org.openscada.opc.lib.da.browser;

public class Leaf
{
    private Branch _parent = null;

    private String _name = "";

    private String _itemId = null;

    public Leaf ( Branch parent, String name )
    {
        _parent = parent;
        _name = name;
    }

    public Leaf ( Branch parent, String name, String itemId )
    {
        _parent = parent;
        _name = name;
        _itemId = itemId;
    }

    public String getItemId ()
    {
        return _itemId;
    }

    public void setItemId ( String itemId )
    {
        _itemId = itemId;
    }

    public String getName ()
    {
        return _name;
    }

    public void setName ( String name )
    {
        _name = name;
    }

    public Branch getParent ()
    {
        return _parent;
    }

}
