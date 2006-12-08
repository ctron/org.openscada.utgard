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
import org.jinterop.dcom.core.JIVariant;
import org.openscada.opc.dcom.common.FILETIME;
import org.openscada.opc.dcom.common.impl.EventHandlerImpl;
import org.openscada.opc.dcom.da.Constants;
import org.openscada.opc.dcom.da.IOPCDataCallback;

public class OPCDataCallback extends EventHandlerImpl
{
    private IOPCDataCallback _callback = null;
    private JIJavaCoClass _coClass = null;

    public OPCDataCallback ()
    {
        super ();
    }

    public Object[] OnDataChange ( int transactionId, int serverGroupHandle, int masterQuality, int masterErrorCode, int count, JIArray clientHandles, JIArray values, JIArray quantities, JIArray timestamps, JIArray errors )
    {
        System.out.println ( "OnDataChange" );
        return new Object[0];
    }

    public Object[] OnReadComplete ( int transactionId, int serverGroupHandle, int masterQuality, int masterErrorCode, int count, JIArray clientHandles, JIArray values, JIArray quantities, JIArray timestamps, JIArray errors )
    {
        System.out.println ( "OnReadComplete" );
        return new Object[0];
    }

    public Object[] OnWriteComplete ( int transactionId, int serverGroupHandle, int masterErrorCode, int count, JIArray clientHandles, JIArray errors )
    {
        //return new Object[0];
        return new Object[0];
    }

    public Object[] OnCancelComplete ( int transactionId, int serverGroupHandle )
    {
        //return new Object[0];
        return new Object[0];
    }

    public synchronized JIJavaCoClass getCoClass () throws JIException
    {
        if ( _coClass != null )
            return _coClass;
        
        _coClass = new JIJavaCoClass ( new JIInterfaceDefinition ( Constants.IOPCDataCallback_IID ), this );

        JIParameterObject params;
        JIMethodDescriptor method;

        // OnDataChange
        params = new JIParameterObject ();
        params.addInParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        params.addInParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        params.addInParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        params.addInParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        params.addInParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        params.addInParamAsObject ( new JIArray ( Integer.class, null, 1, true ), JIFlags.FLAG_NULL );
        params.addInParamAsObject ( new JIArray ( JIVariant.class, null, 1, true ), JIFlags.FLAG_NULL );
        params.addInParamAsObject ( new JIArray ( Integer.class, null, 1, true ), JIFlags.FLAG_NULL );
        params.addInParamAsObject ( new JIArray ( FILETIME.getStruct (), null, 1, true ), JIFlags.FLAG_NULL );
        params.addInParamAsObject ( new JIArray ( Integer.class, null, 1, true ), JIFlags.FLAG_NULL );
        method = new JIMethodDescriptor ( "OnDataChange", 0, params );
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
        params.addInParamAsObject ( new JIArray ( Integer.class, null, 1, true ), JIFlags.FLAG_NULL );
        params.addInParamAsObject ( new JIArray ( FILETIME.getStruct (), null, 1, true ), JIFlags.FLAG_NULL );
        params.addInParamAsObject ( new JIArray ( Integer.class, null, 1, true ), JIFlags.FLAG_NULL );
        method = new JIMethodDescriptor ( "OnReadComplete", 1, params );
        _coClass.getInterfaceDefinition ().addMethodDescriptor ( method );

        // OnWriteComplete
        params = new JIParameterObject ();
        params.addInParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        params.addInParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        params.addInParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        params.addInParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        params.addInParamAsObject ( new JIArray ( Integer.class, null, 1, true ), JIFlags.FLAG_NULL );
        params.addInParamAsObject ( new JIArray ( Integer.class, null, 1, true ), JIFlags.FLAG_NULL );
        method = new JIMethodDescriptor ( "OnWriteComplete", 2, params );
        _coClass.getInterfaceDefinition ().addMethodDescriptor ( method );

        // OnCancelComplete
        params = new JIParameterObject ();
        params.addInParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        params.addInParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        method = new JIMethodDescriptor ( "OnCancelComplete", 3, params );
        _coClass.getInterfaceDefinition ().addMethodDescriptor ( method );

        // Add supported event interfaces
        List<String> eventInterfaces = new LinkedList<String> ();
        eventInterfaces.add ( Constants.IOPCDataCallback_IID );
        _coClass.setSupportedEventInterfaces ( eventInterfaces );

        return _coClass;
    }

    public void setCallback ( IOPCDataCallback callback )
    {
        _callback = callback;
    }

    public IOPCDataCallback getCallback ()
    {
        return _callback;
    }
}
