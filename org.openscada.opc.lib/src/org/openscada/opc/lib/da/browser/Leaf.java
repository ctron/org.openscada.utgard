/*
 * This file is part of the OpenSCADA project
 * Copyright (C) 2006-2010 TH4 SYSTEMS GmbH (http://th4-systems.com)
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openscada.opc.lib.da.browser;

public class Leaf
{
    private Branch _parent = null;

    private String _name = "";

    private String _itemId = null;

    public Leaf ( final Branch parent, final String name )
    {
        this._parent = parent;
        this._name = name;
    }

    public Leaf ( final Branch parent, final String name, final String itemId )
    {
        this._parent = parent;
        this._name = name;
        this._itemId = itemId;
    }

    public String getItemId ()
    {
        return this._itemId;
    }

    public void setItemId ( final String itemId )
    {
        this._itemId = itemId;
    }

    public String getName ()
    {
        return this._name;
    }

    public void setName ( final String name )
    {
        this._name = name;
    }

    public Branch getParent ()
    {
        return this._parent;
    }

}
