/*
 * This file is part of the OpenSCADA project
 * Copyright (C) 2006-2010 TH4 SYSTEMS GmbH (http://inavare.com)
 *
 * OpenSCADA is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License version 3
 * only, as published by the Free Software Foundation.
 *
 * OpenSCADA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License version 3 for more details
 * (a copy is included in the LICENSE file that accompanied this code).
 *
 * You should have received a copy of the GNU Lesser General Public License
 * version 3 along with OpenSCADA. If not, see
 * <http://opensource.org/licenses/lgpl-3.0.html> for a copy of the LGPLv3 License.
 */

package org.openscada.opc.dcom.common;

public class Result<T>
{
    private T _value = null;

    private int _errorCode = 0;

    public Result ()
    {
    }

    public Result ( final T value, final int errorCode )
    {
        this._value = value;
        this._errorCode = errorCode;
    }

    public int getErrorCode ()
    {
        return this._errorCode;
    }

    public void setErrorCode ( final int errorCode )
    {
        this._errorCode = errorCode;
    }

    public T getValue ()
    {
        return this._value;
    }

    public void setValue ( final T value )
    {
        this._value = value;
    }

    public boolean isFailed ()
    {
        return this._errorCode != 0;
    }
}
