/*
 * This file is part of the OpenSCADA project
 * Copyright (C) 2006-2010 TH4 SYSTEMS GmbH (http://th4-systems.com)
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openscada.opc.dcom.da.impl;

import java.net.UnknownHostException;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.core.JIArray;
import org.jinterop.dcom.core.JICallBuilder;
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
    public OPCItemIO ( final IJIComObject opcItemIO ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        super ( opcItemIO.queryInterface ( Constants.IOPCItemIO_IID ) );
    }

    public void read ( final IORequest[] requests ) throws JIException
    {
        if ( requests.length == 0 )
        {
            return;
        }

        JICallBuilder callObject = new JICallBuilder ( true );
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

        getCOMObject ().call ( callObject );
    }
}
