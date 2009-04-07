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
    public OPCCommon ( IJIComObject opcObject ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        super ( (IJIComObject)opcObject.queryInterface ( org.openscada.opc.dcom.common.Constants.IOPCCommon_IID ) );
    }

    public void setLocaleID ( int localeID ) throws JIException
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

    public String getErrorString ( int errorCode, int localeID ) throws JIException
    {
    	JICallBuilder callObject = new JICallBuilder ( true );
        callObject.setOpnum ( 3 );

        callObject.addInParamAsInt ( errorCode, JIFlags.FLAG_NULL );
        callObject.addInParamAsInt ( localeID, JIFlags.FLAG_NULL );
        callObject.addOutParamAsObject ( new JIPointer ( new JIString ( JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR ) ),
                JIFlags.FLAG_NULL );

        Object[] result = getCOMObject ().call ( callObject );
        return ( (JIString) ( ( (JIPointer)result[0] ).getReferent () ) ).getString ();
    }

    public void setClientName ( String clientName ) throws JIException
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
        callObject.addOutParamAsObject ( new JIPointer ( new JIArray ( Integer.class, null, 1, true ) ),
                JIFlags.FLAG_NULL );

        Object[] result = getCOMObject ().call ( callObject );

        JIArray resultArray = (JIArray) ( (JIPointer)result[1] ).getReferent ();
        Integer[] intArray = (Integer[])resultArray.getArrayInstance ();

        return Arrays.asList ( intArray );
    }

}
