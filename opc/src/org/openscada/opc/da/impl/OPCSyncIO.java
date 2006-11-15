package org.openscada.opc.da.impl;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.core.JIArray;
import org.jinterop.dcom.core.JICallObject;
import org.jinterop.dcom.core.JIFlags;
import org.jinterop.dcom.core.JIPointer;
import org.jinterop.dcom.core.JIStruct;
import org.openscada.opc.common.KeyedResult;
import org.openscada.opc.common.KeyedResultSet;
import org.openscada.opc.common.impl.Helper;
import org.openscada.opc.da.Constants;
import org.openscada.opc.da.OPCITEMSOURCE;
import org.openscada.opc.da.OPCITEMSTATE;

public class OPCSyncIO
{
    private IJIComObject _opcSyncIO = null;

    public OPCSyncIO ( IJIComObject opcSyncIO ) throws JIException
    {
        _opcSyncIO = (IJIComObject)opcSyncIO.queryInterface ( Constants.IOPCSyncIO_IID );
    }

    public KeyedResultSet<Integer, OPCITEMSTATE> read ( OPCITEMSOURCE source, Integer... serverHandles ) throws JIException
    {
        if ( serverHandles == null || serverHandles.length == 0 )
            return new KeyedResultSet<Integer, OPCITEMSTATE> ();

        JICallObject callObject = new JICallObject ( _opcSyncIO.getIpid (), true );
        callObject.setOpnum ( 0 );

        callObject.addInParamAsShort ( (short)source.id (), JIFlags.FLAG_NULL );
        callObject.addInParamAsInt ( serverHandles.length, JIFlags.FLAG_NULL );
        callObject.addInParamAsArray ( new JIArray ( serverHandles, true ), JIFlags.FLAG_NULL );

        callObject.addOutParamAsObject ( new JIPointer ( new JIArray ( OPCITEMSTATE.getStruct (), null, 1, true ) ),
                JIFlags.FLAG_NULL );
        callObject.addOutParamAsObject ( new JIPointer ( new JIArray ( Integer.class, null, 1, true ) ),
                JIFlags.FLAG_NULL );

        Object result[] = Helper.callRespectSFALSE ( _opcSyncIO, callObject );

        KeyedResultSet<Integer, OPCITEMSTATE> results = new KeyedResultSet<Integer, OPCITEMSTATE> ();
        JIStruct[] states = (JIStruct[]) ( (JIArray) ( (JIPointer)result[0] ).getReferent () ).getArrayInstance ();
        Integer[] errorCodes = (Integer[]) ( (JIArray) ( (JIPointer)result[1] ).getReferent () ).getArrayInstance ();
        
        for ( int i = 0; i < serverHandles.length; i++ )
        {
            results.add ( new KeyedResult<Integer, OPCITEMSTATE> ( serverHandles[i], OPCITEMSTATE.fromStruct ( states[i] ),
                    errorCodes[i] ) );
        }

        return results;
    }
}
