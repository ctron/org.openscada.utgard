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

package org.openscada.opc.dcom.common;

public class Result<T>
{
    private T _value = null;

    private int _errorCode = 0;

    public Result ()
    {
    }

    public Result ( T value, int errorCode )
    {
        _value = value;
        _errorCode = errorCode;
    }

    public int getErrorCode ()
    {
        return _errorCode;
    }

    public void setErrorCode ( int errorCode )
    {
        _errorCode = errorCode;
    }

    public T getValue ()
    {
        return _value;
    }

    public void setValue ( T value )
    {
        _value = value;
    }

    public boolean isFailed ()
    {
        return _errorCode != 0;
    }
}
