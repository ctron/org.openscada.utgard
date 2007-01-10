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

import java.util.LinkedList;
import java.util.List;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.common.JIInterfaceDefinition;
import org.jinterop.dcom.common.JIJavaCoClass;
import org.jinterop.dcom.common.JIMethodDescriptor;
import org.jinterop.dcom.core.JIArray;
import org.jinterop.dcom.core.JIFlags;
import org.jinterop.dcom.core.JIParameterObject;
import org.jinterop.dcom.core.JIStruct;
import org.jinterop.dcom.core.JIVariant;
import org.openscada.opc.dcom.common.FILETIME;
import org.openscada.opc.dcom.common.KeyedResult;
import org.openscada.opc.dcom.common.KeyedResultSet;
import org.openscada.opc.dcom.common.Result;
import org.openscada.opc.dcom.common.ResultSet;
import org.openscada.opc.dcom.common.impl.EventHandlerImpl;
import org.openscada.opc.dcom.da.Constants;
import org.openscada.opc.dcom.da.IOPCDataCallback;
import org.openscada.opc.dcom.da.ValueData;

public class OPCDataCallback extends EventHandlerImpl
{
    private IOPCDataCallback _callback = null;

    private JIJavaCoClass _coClass = null;

    public OPCDataCallback ()
    {
        super ();
    }

    public synchronized Object[] OnDataChange ( int transactionId, int serverGroupHandle, int masterQuality, int masterErrorCode, int count, JIArray clientHandles, JIArray values, JIArray qualities, JIArray timestamps, JIArray errors )
    {
        if ( _callback == null )
            return new Object[0];

        // get arrays for more readable code later ;-)
        Integer[] errorCodes = (Integer[])errors.getArrayInstance ();
        Integer[] itemHandles = (Integer[])clientHandles.getArrayInstance ();
        Short[] qualitiesArray = (Short[])qualities.getArrayInstance ();
        JIVariant[] valuesArray = (JIVariant[])values.getArrayInstance ();
        JIStruct[] timestampArray = (JIStruct[])timestamps.getArrayInstance ();

        // create result data
        KeyedResultSet<Integer, ValueData> result = new KeyedResultSet<Integer, ValueData> ();
        for ( int i = 0; i < count; i++ )
        {
            ValueData vd = new ValueData ();
            vd.setQuality ( qualitiesArray[i] );
            vd.setTimestamp ( FILETIME.fromStruct ( timestampArray[i] ).asCalendar () );
            vd.setValue ( valuesArray[i] );
            result.add ( new KeyedResult<Integer, ValueData> ( itemHandles[i], vd, errorCodes[i] ) );
        }

        // fire event
        try
        {
            _callback.dataChange ( transactionId, serverGroupHandle, masterQuality, masterErrorCode, result );
        }
        catch ( Throwable e )
        {
            e.printStackTrace ();
        }

        // The client must always return S_OK
        return new Object[] { Constants.S_OK };
    }

    public synchronized Object[] OnReadComplete ( int transactionId, int serverGroupHandle, int masterQuality, int masterErrorCode, int count, JIArray clientHandles, JIArray values, JIArray qualities, JIArray timestamps, JIArray errors )
    {
        if ( _callback == null )
            return new Object[0];

        // get arrays for more readable code later ;-)
        Integer[] errorCodes = (Integer[])errors.getArrayInstance ();
        Integer[] itemHandles = (Integer[])clientHandles.getArrayInstance ();
        Short[] qualitiesArray = (Short[])qualities.getArrayInstance ();
        JIVariant[] valuesArray = (JIVariant[])values.getArrayInstance ();
        JIStruct[] timestampArray = (JIStruct[])timestamps.getArrayInstance ();

        // create result data
        KeyedResultSet<Integer, ValueData> result = new KeyedResultSet<Integer, ValueData> ();
        for ( int i = 0; i < count; i++ )
        {
            ValueData vd = new ValueData ();
            vd.setQuality ( qualitiesArray[i] );
            vd.setTimestamp ( FILETIME.fromStruct ( timestampArray[i] ).asCalendar () );
            vd.setValue ( valuesArray[i] );
            result.add ( new KeyedResult<Integer, ValueData> ( itemHandles[i], vd, errorCodes[i] ) );
        }

        // fire event
        try
        {
            _callback.readComplete ( transactionId, serverGroupHandle, masterQuality, masterErrorCode, result );
        }
        catch ( Throwable e )
        {
            e.printStackTrace ();
        }

        // The client must always return S_OK
        return new Object[] { Constants.S_OK };
    }

    public synchronized Object[] OnWriteComplete ( int transactionId, int serverGroupHandle, int masterErrorCode, int count, JIArray clientHandles, JIArray errors )
    {
        if ( _callback == null )
            return new Object[0];

        // get arrays for more readable code later ;-)
        Integer[] errorCodes = (Integer[])errors.getArrayInstance ();
        Integer[] itemHandles = (Integer[])clientHandles.getArrayInstance ();

        // create result data
        ResultSet<Integer> result = new ResultSet<Integer> ();
        for ( int i = 0; i < count; i++ )
        {
            result.add ( new Result<Integer> ( itemHandles[i], errorCodes[i] ) );
        }

        // fire event
        try
        {
            _callback.writeComplete ( transactionId, serverGroupHandle, masterErrorCode, result );
        }
        catch ( Throwable e )
        {
            e.printStackTrace ();
        }

        // The client must always return S_OK
        return new Object[] { Constants.S_OK };
    }

    public synchronized Object[] OnCancelComplete ( int transactionId, int serverGroupHandle )
    {
        if ( _callback == null )
            return new Object[0];

        _callback.cancelComplete ( transactionId, serverGroupHandle );

        // The client must always return S_OK
        return new Object[] { Constants.S_OK };
    }

    public synchronized JIJavaCoClass getCoClass () throws JIException
    {
        if ( _coClass != null )
            return _coClass;

        _coClass = new JIJavaCoClass ( new JIInterfaceDefinition ( Constants.IOPCDataCallback_IID, false ), this );

        JIParameterObject params;
        JIMethodDescriptor method;

        // OnDataChange
        params = new JIParameterObject ();
        params.addInParamAsType ( Integer.class, JIFlags.FLAG_NULL ); // trans id
        params.addInParamAsType ( Integer.class, JIFlags.FLAG_NULL ); // group handle
        params.addInParamAsType ( Integer.class, JIFlags.FLAG_NULL ); // master quality
        params.addInParamAsType ( Integer.class, JIFlags.FLAG_NULL ); // master error
        params.addInParamAsType ( Integer.class, JIFlags.FLAG_NULL ); // count
        params.addInParamAsObject ( ( new JIArray ( Integer.class, null, 1, true ) ), JIFlags.FLAG_NULL ); // item handles
        params.addInParamAsObject ( ( new JIArray ( JIVariant.class, null, 1, true ) ), JIFlags.FLAG_NULL ); // values
        params.addInParamAsObject ( ( new JIArray ( Short.class, null, 1, true ) ), JIFlags.FLAG_NULL ); // qualities
        params.addInParamAsObject ( ( new JIArray ( FILETIME.getStruct (), null, 1, true ) ), JIFlags.FLAG_NULL ); // timestamps
        params.addInParamAsObject ( ( new JIArray ( Integer.class, null, 1, true ) ), JIFlags.FLAG_NULL ); // errors

        method = new JIMethodDescriptor ( "OnDataChange", params );
        _coClass.getInterfaceDefinition ().addMethodDescriptor ( method );

        // OnReadComplete
        params = new JIParameterObject ();
        params.addInParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        params.addInParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        params.addInParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        params.addInParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        params.addInParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        params.addInParamAsObject ( new JIArray ( Integer.class, null, 1, true ), JIFlags.FLAG_NULL );
        params.addInParamAsObject ( new JIArray ( JIVariant.class, null, 1, true ), JIFlags.FLAG_NULL );
        params.addInParamAsObject ( new JIArray ( Short.class, null, 1, true ), JIFlags.FLAG_NULL );
        params.addInParamAsObject ( new JIArray ( FILETIME.getStruct (), null, 1, true ), JIFlags.FLAG_NULL );
        params.addInParamAsObject ( new JIArray ( Integer.class, null, 1, true ), JIFlags.FLAG_NULL );
        method = new JIMethodDescriptor ( "OnReadComplete", params );
        _coClass.getInterfaceDefinition ().addMethodDescriptor ( method );

        // OnWriteComplete
        params = new JIParameterObject ();
        params.addInParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        params.addInParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        params.addInParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        params.addInParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        params.addInParamAsObject ( new JIArray ( Integer.class, null, 1, true ), JIFlags.FLAG_NULL );
        params.addInParamAsObject ( new JIArray ( Integer.class, null, 1, true ), JIFlags.FLAG_NULL );
        method = new JIMethodDescriptor ( "OnWriteComplete", params );
        _coClass.getInterfaceDefinition ().addMethodDescriptor ( method );

        // OnCancelComplete
        params = new JIParameterObject ();
        params.addInParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        params.addInParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        method = new JIMethodDescriptor ( "OnCancelComplete", params );
        _coClass.getInterfaceDefinition ().addMethodDescriptor ( method );

        // Add supported event interfaces
        List<String> eventInterfaces = new LinkedList<String> ();
        eventInterfaces.add ( Constants.IOPCDataCallback_IID );
        _coClass.setSupportedEventInterfaces ( eventInterfaces );

        return _coClass;
    }

    public synchronized void setCallback ( IOPCDataCallback callback )
    {
        _callback = callback;
    }

    public IOPCDataCallback getCallback ()
    {
        return _callback;
    }
}
