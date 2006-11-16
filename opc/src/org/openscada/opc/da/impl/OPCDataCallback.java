package org.openscada.opc.da.impl;

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
import org.openscada.opc.common.FILETIME;
import org.openscada.opc.common.impl.EventHandlerImpl;
import org.openscada.opc.da.Constants;
import org.openscada.opc.da.IOPCDataCallback;

public class OPCDataCallback extends EventHandlerImpl
{
    private IOPCDataCallback _callback = null;
    
    public OPCDataCallback ()
    {
        super ();
    }
    
    public Object[] OnDataChange (
            int transactionId,
            int serverGroupHandle,
            int masterQuality,
            int masterErrorCode,
            int count,
            JIArray clientHandles,
            JIArray values,
            JIArray quantities,
            JIArray timestamps,
            JIArray errors
            )
    {
        System.out.println ( "OnDataChange" );
        return new Object[0];
    }
    
    public Object[] OnReadComplete (
            int transactionId,
            int serverGroupHandle,
            int masterQuality,
            int masterErrorCode,
            int count,
            JIArray clientHandles,
            JIArray values,
            JIArray quantities,
            JIArray timestamps,
            JIArray errors
            )
    {
        System.out.println ( "OnReadComplete" );
        return new Object[0];
    }
    
    public Object[] OnWriteComplete (
            int transactionId,
            int serverGroupHandle,
            int masterErrorCode,
            int count,
            JIArray clientHandles,
            JIArray errors
    )
    {
        //return new Object[0];
        return new Object[0];
    }
    
    public Object[] OnCancelComplete (
            int transactionId,
            int serverGroupHandle )
    {
        //return new Object[0];
        return new Object[0];
    }
    
    public JIJavaCoClass getCoClass () throws JIException
    {
        JIJavaCoClass coClass = new JIJavaCoClass ( new JIInterfaceDefinition ( "6E3AC3D2-8222-44E2-9347-D6DBDDCE122E" ), this );

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
        coClass.getInterfaceDefinition ().addMethodDescriptor ( method );
        
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
        coClass.getInterfaceDefinition ().addMethodDescriptor ( method );
        
        // OnWriteComplete
        params = new JIParameterObject ();
        params.addInParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        params.addInParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        params.addInParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        params.addInParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        params.addInParamAsObject ( new JIArray ( Integer.class, null, 1, true ), JIFlags.FLAG_NULL );
        params.addInParamAsObject ( new JIArray ( Integer.class, null, 1, true ), JIFlags.FLAG_NULL );
        method = new JIMethodDescriptor ( "OnWriteComplete", 2, params ); 
        coClass.getInterfaceDefinition ().addMethodDescriptor ( method );
        
        // OnCancelComplete
        params = new JIParameterObject ();
        params.addInParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        params.addInParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        method = new JIMethodDescriptor ( "OnCancelComplete", 3, params ); 
        coClass.getInterfaceDefinition ().addMethodDescriptor ( method );
        
        // Add supported event interfaces
        List<String> eventInterfaces = new LinkedList<String> ();
        eventInterfaces.add ( Constants.IOPCDataCallback_IID );
        coClass.setSupportedEventInterfaces ( eventInterfaces );
        
        return coClass;
    }

    public void setCallback ( IOPCDataCallback callback )
    {
        _callback  = callback;
    }

    public IOPCDataCallback getCallback ()
    {
        return _callback;
    }
}
