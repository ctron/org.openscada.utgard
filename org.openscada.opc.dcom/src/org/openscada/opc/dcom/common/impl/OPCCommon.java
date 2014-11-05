/*
 * This file is part of the OpenSCADA project
 * Copyright (C) 2006-2010 TH4 SYSTEMS GmbH (http://th4-systems.com)
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openscada.opc.dcom.common.impl;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.core.JIArray;
import org.jinterop.dcom.core.JICallBuilder;
import org.jinterop.dcom.core.JIFlags;
import org.jinterop.dcom.core.JIPointer;
import org.jinterop.dcom.core.JIString;

public class OPCCommon extends BaseCOMObject
{
    public OPCCommon ( final IJIComObject opcObject ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        super ( opcObject.queryInterface ( org.openscada.opc.dcom.common.Constants.IOPCCommon_IID ) );
    }

    public void setLocaleID ( final int localeID ) throws JIException
    {
        JICallBuilder callObject = new JICallBuilder ( true );
        callObject.setOpnum ( 0 );

        callObject.addInParamAsInt ( localeID, JIFlags.FLAG_NULL );

        getCOMObject ().call ( callObject );
    }

    public int getLocaleID () throws JIException
    {
        JICallBuilder callObject = new JICallBuilder ( true );
        callObject.setOpnum ( 1 );

        callObject.addOutParamAsObject ( Integer.class, JIFlags.FLAG_NULL );

        Object[] result = getCOMObject ().call ( callObject );
        return (Integer)result[0];
    }

    public String getErrorString ( final int errorCode, final int localeID ) throws JIException
    {
        JICallBuilder callObject = new JICallBuilder ( true );
        callObject.setOpnum ( 3 );

        callObject.addInParamAsInt ( errorCode, JIFlags.FLAG_NULL );
        callObject.addInParamAsInt ( localeID, JIFlags.FLAG_NULL );
        callObject.addOutParamAsObject ( new JIPointer ( new JIString ( JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR ) ), JIFlags.FLAG_NULL );

        Object[] result = getCOMObject ().call ( callObject );
        return ( (JIString) ( (JIPointer)result[0] ).getReferent () ).getString ();
    }

    public void setClientName ( final String clientName ) throws JIException
    {
        JICallBuilder callObject = new JICallBuilder ( true );
        callObject.setOpnum ( 4 );

        callObject.addInParamAsString ( clientName, JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR );

        getCOMObject ().call ( callObject );
    }

    public Collection<Integer> queryAvailableLocaleIDs () throws JIException
    {
        JICallBuilder callObject = new JICallBuilder ( true );
        callObject.setOpnum ( 2 );

        callObject.addOutParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        callObject.addOutParamAsObject ( new JIPointer ( new JIArray ( Integer.class, null, 1, true ) ), JIFlags.FLAG_NULL );

        Object[] result = getCOMObject ().call ( callObject );

        JIArray resultArray = (JIArray) ( (JIPointer)result[1] ).getReferent ();
        Integer[] intArray = (Integer[])resultArray.getArrayInstance ();

        return Arrays.asList ( intArray );
    }

}
