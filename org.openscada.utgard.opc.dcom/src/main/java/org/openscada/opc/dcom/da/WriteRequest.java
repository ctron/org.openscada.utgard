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

import org.jinterop.dcom.core.JIVariant;

/**
 * Data for a write request to the server
 * @author Jens Reimann <jens.reimann@inavare.net>
 *
 */
public class WriteRequest
{
    private int _serverHandle = 0;

    private JIVariant _value = JIVariant.EMPTY();

    public WriteRequest ()
    {
    }

    public WriteRequest ( WriteRequest arg0 )
    {
        _serverHandle = arg0._serverHandle;
        _value = arg0._value;
    }

    /**
     * Create a new write request with pre-fille data
     * @param serverHandle the server handle of the item to write to
     * @param value the value to write.
     */
    public WriteRequest ( int serverHandle, JIVariant value )
    {
        _serverHandle = serverHandle;
        _value = value;
    }

    public int getServerHandle ()
    {
        return _serverHandle;
    }

    public void setServerHandle ( int serverHandle )
    {
        _serverHandle = serverHandle;
    }

    public JIVariant getValue ()
    {
        return _value;
    }

    public void setValue ( JIVariant value )
    {
        _value = value;
    }
}
