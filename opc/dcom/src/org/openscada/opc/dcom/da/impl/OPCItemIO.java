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
import org.jinterop.dcom.core.JIArray;
import org.jinterop.dcom.core.JICallObject;
import org.jinterop.dcom.core.JIFlags;
import org.jinterop.dcom.core.JIPointer;
import org.jinterop.dcom.core.JIString;
import org.jinterop.dcom.core.JIVariant;
import org.openscada.opc.dcom.common.FILETIME;
import org.openscada.opc.dcom.common.impl.BaseCOMObject;
import org.openscada.opc.dcom.da.Constants;
import org.openscada.opc.dcom.da.IORequest;

public class OPCItemIO extends BaseCOMObject
{
    public OPCItemIO ( IJIComObject opcItemIO ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        super ( (IJIComObject)opcItemIO.queryInterface ( Constants.IOPCItemIO_IID ) );
    }

    public void read ( IORequest[] requests ) throws JIException
    {
        if ( requests.length == 0 )
            return;

        JICallObject callObject = new JICallObject ( getCOMObject ().getIpid (), true );
        callObject.setOpnum ( 0 );

        JIString itemIDs[] = new JIString[requests.length];
        Integer maxAges[] = new Integer[requests.length];
        for ( int i = 0; i < requests.length; i++ )
        {
            itemIDs[i] = new JIString ( requests[i].getItemID (), JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR );
            maxAges[i] = new Integer ( requests[i].getMaxAge () );
        }

        callObject.addInParamAsInt ( requests.length, JIFlags.FLAG_NULL );
        callObject.addInParamAsArray ( new JIArray ( itemIDs, true ), JIFlags.FLAG_NULL );
        callObject.addInParamAsArray ( new JIArray ( maxAges, true ), JIFlags.FLAG_NULL );

        callObject.addOutParamAsObject ( new JIPointer ( new JIArray ( JIVariant.class, null, 1, true ) ), JIFlags.FLAG_NULL );
        callObject.addOutParamAsObject ( new JIPointer ( new JIArray ( Integer.class, null, 1, true ) ), JIFlags.FLAG_NULL );
        callObject.addOutParamAsObject ( new JIPointer ( new JIArray ( FILETIME.getStruct (), null, 1, true ) ), JIFlags.FLAG_NULL );
        callObject.addOutParamAsObject ( new JIPointer ( new JIArray ( Integer.class, null, 1, true ) ), JIFlags.FLAG_NULL );

        Object result[] = getCOMObject ().call ( callObject );
    }
}
