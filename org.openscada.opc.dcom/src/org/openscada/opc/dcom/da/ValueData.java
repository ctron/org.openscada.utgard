/*
 * This file is part of the OpenSCADA project
 * Copyright (C) 2006-2010 inavare GmbH (http://inavare.com)
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

package org.openscada.opc.dcom.da;

import java.util.Calendar;

import org.jinterop.dcom.core.JIVariant;

public class ValueData
{
    private JIVariant _value;

    private short _quality;

    private Calendar _timestamp;

    public short getQuality ()
    {
        return this._quality;
    }

    public void setQuality ( final short quality )
    {
        this._quality = quality;
    }

    public Calendar getTimestamp ()
    {
        return this._timestamp;
    }

    public void setTimestamp ( final Calendar timestamp )
    {
        this._timestamp = timestamp;
    }

    public JIVariant getValue ()
    {
        return this._value;
    }

    public void setValue ( final JIVariant value )
    {
        this._value = value;
    }
}
