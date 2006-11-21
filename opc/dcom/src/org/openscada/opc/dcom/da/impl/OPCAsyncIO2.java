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

package org.openscada.opc.dcom.da.impl;

import java.net.UnknownHostException;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.core.JICallObject;
import org.jinterop.dcom.core.JIFlags;
import org.openscada.opc.dcom.common.impl.BaseCOMObject;
import org.openscada.opc.dcom.da.Constants;

public class OPCAsyncIO2 extends BaseCOMObject
{
    public OPCAsyncIO2 ( IJIComObject opcAsyncIO2 ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        super ( (IJIComObject)opcAsyncIO2.queryInterface ( Constants.IOPCAsyncIO2_IID ) );
    }

    public void setEnable ( boolean state ) throws JIException
    {
        JICallObject callObject = new JICallObject ( getCOMObject ().getIpid (), true );
        callObject.setOpnum ( 4 );

        callObject.addInParamAsInt ( state ? 1 : 0, JIFlags.FLAG_NULL );

        getCOMObject ().call ( callObject );
    }

    public int refresh ( short source, int transactionID ) throws JIException
    {
        JICallObject callObject = new JICallObject ( getCOMObject ().getIpid (), true );
        callObject.setOpnum ( 2 );

        callObject.addInParamAsShort ( source, JIFlags.FLAG_NULL );
        callObject.addInParamAsInt ( transactionID, JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( Integer.class, JIFlags.FLAG_NULL );

        Object result[] = getCOMObject ().call ( callObject );

        getCOMObject ().call ( callObject );

        return (Integer)result[0];
    }
}
