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

package org.openscada.opc.lib.da.browser;

import java.net.UnknownHostException;
import java.util.Collection;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIVariant;
import org.openscada.opc.dcom.da.OPCBROWSETYPE;
import org.openscada.opc.dcom.da.impl.OPCBrowseServerAddressSpace;

/**
 * Browse through the flat server namespace 
 * @author Jens Reimann <jens.reimann@inavare.net>
 *
 */
public class FlatBrowser
{
    private OPCBrowseServerAddressSpace _browser = null;

    public FlatBrowser ( OPCBrowseServerAddressSpace browser )
    {
        _browser = browser;
    }

    public Collection<String> browse ( String filterCriteria ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        return _browser.browse ( OPCBROWSETYPE.OPC_FLAT, filterCriteria, 0, JIVariant.VT_EMPTY ).asCollection ();
    }

}
