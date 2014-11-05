/*
 * This file is part of the OpenSCADA project
 * Copyright (C) 2006-2010 TH4 SYSTEMS GmbH (http://th4-systems.com)
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openscada.opc.lib.list;

public class Category
{
    private String _catId = null;

    public Category ( final String catId )
    {
        this._catId = catId;
    }

    @Override
    public String toString ()
    {
        return this._catId;
    }

    @Override
    public int hashCode ()
    {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ( this._catId == null ? 0 : this._catId.hashCode () );
        return result;
    }

    @Override
    public boolean equals ( final Object obj )
    {
        if ( this == obj )
        {
            return true;
        }
        if ( obj == null )
        {
            return false;
        }
        if ( getClass () != obj.getClass () )
        {
            return false;
        }
        final Category other = (Category)obj;
        if ( this._catId == null )
        {
            if ( other._catId != null )
            {
                return false;
            }
        }
        else if ( !this._catId.equals ( other._catId ) )
        {
            return false;
        }
        return true;
    }

}
