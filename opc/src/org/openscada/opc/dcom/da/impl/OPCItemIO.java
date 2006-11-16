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
