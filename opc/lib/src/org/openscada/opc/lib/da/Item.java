/*
 * This file is part of the OpenSCADA project
 * Copyright (C) 2006 inavare GmbH (http://inavare.com)
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

package org.openscada.opc.lib.da;

import org.apache.log4j.Logger;
import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIVariant;

public class Item
{
    private static Logger _log = Logger.getLogger ( Item.class );
    
    private Group _group = null;
    private int _serverHandle = 0;
    private String _id = null;
    
    public Item ( Group group, int serverHandle, String id )
    {
        super ();
        _log.debug ( String.format ( "Adding new item '%s' (0x%08X) for group %s", id, serverHandle, group.toString () ) );
        _group = group;
        _serverHandle = serverHandle;
        _id = id;
    }
    
    public Group getGroup ()
    {
        return _group;
    }
    
    public int getServerHandle ()
    {
        return _serverHandle;
    }

    public String getId ()
    {
        return _id;
    }
    
    public void setActive ( boolean state ) throws JIException
    {
        _group.setActive ( state, this );
    }
    
    public ItemState read ( boolean device ) throws JIException
    {
        return _group.read ( device, this ).get ( this );
    }
    
    public Integer write ( JIVariant value ) throws JIException
    {
        return _group.write ( new WriteRequest[] {
                new WriteRequest ( this, value )
        } ).get ( this );
    }
}
