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

package org.openscada.opc.dcom.common.impl;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.win32.ComFactory;
import org.openscada.opc.dcom.common.EventHandler;

public class EventHandlerImpl implements EventHandler
{
    private String _identifier = null;

    private IJIComObject _object = null;

    public String getIdentifier ()
    {
        return _identifier;
    }

    public synchronized IJIComObject getObject ()
    {
        return _object;
    }

    public synchronized void setInfo ( IJIComObject object, String identifier )
    {
        _object = object;
        _identifier = identifier;
    }

    public synchronized void detach () throws JIException
    {
        if ( _object != null && _identifier != null )
        {
            try
            {
                ComFactory.detachEventHandler ( _object, _identifier );
            }
            finally
            {
                _object = null;
                _identifier = null;
            }
        }
    }

}
