package org.openscada.opc.da.impl;

import java.net.UnknownHostException;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.core.JIArray;
import org.jinterop.dcom.core.JICallObject;
import org.jinterop.dcom.core.JIFlags;
import org.jinterop.dcom.core.JIPointer;
import org.jinterop.dcom.core.JIStruct;
import org.openscada.opc.common.KeyedResult;
import org.openscada.opc.common.KeyedResultSet;
import org.openscada.opc.da.Constants;
import org.openscada.opc.da.OPCITEMDEF;
import org.openscada.opc.da.OPCITEMRESULT;

public class OPCItemMgt
{
    private IJIComObject _opcItemMgt = null;

    public OPCItemMgt ( IJIComObject opcGroup ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        _opcItemMgt = (IJIComObject)opcGroup.queryInterface ( Constants.IOPCItemMgt_IID );
    }
    
    public KeyedResultSet<OPCITEMDEF, OPCITEMRESULT> validate ( OPCITEMDEF ... items ) throws JIException
    {
        if ( items.length == 0 )
            return new KeyedResultSet<OPCITEMDEF, OPCITEMRESULT> ();
        
        JICallObject callObject = new JICallObject ( _opcItemMgt.getIpid (), true );
        callObject.setOpnum ( 1 );
        
        JIStruct struct[] = new JIStruct[items.length];
        for ( int i = 0; i < items.length; i++ )
        {
            struct[i] = items[i].toStruct ();
        }
        JIArray itemArray = new JIArray ( struct, true);
        
        callObject.addInParamAsInt ( items.length, JIFlags.FLAG_NULL );
        callObject.addInParamAsArray ( itemArray, JIFlags.FLAG_NULL );
        callObject.addInParamAsInt ( 0, JIFlags.FLAG_NULL ); // don't update blobs
        callObject.addOutParamAsObject ( new JIPointer ( new JIArray ( OPCITEMRESULT.getStruct (), null, 1, true ) ), JIFlags.FLAG_NULL );
        callObject.addOutParamAsObject ( new JIPointer ( new JIArray ( Integer.class, null, 1, true ) ), JIFlags.FLAG_NULL );
        
        Object result [] = _opcItemMgt.call ( callObject );
        
        JIStruct[] results = (JIStruct[]) ( (JIArray) ( (JIPointer)result[0] ).getReferent () ).getArrayInstance ();
        Integer[] errorCodes = (Integer[]) ( (JIArray) ( (JIPointer)result[1] ).getReferent () ).getArrayInstance ();
        
        KeyedResultSet<OPCITEMDEF, OPCITEMRESULT> resultList = new KeyedResultSet<OPCITEMDEF, OPCITEMRESULT> ( items.length );
        for ( int i = 0; i < items.length ; i++ )
        {
            OPCITEMRESULT itemResult = OPCITEMRESULT.fromStruct ( results[i] );
            KeyedResult<OPCITEMDEF,OPCITEMRESULT> resultEntry = new KeyedResult<OPCITEMDEF,OPCITEMRESULT> ( items[i], itemResult, errorCodes[i] );
            resultList.add ( resultEntry );
        }
        
        return resultList;
    }
    
    public KeyedResultSet<OPCITEMDEF, OPCITEMRESULT> add ( OPCITEMDEF ... items ) throws JIException
    {
        if ( items.length == 0 )
            return new KeyedResultSet<OPCITEMDEF, OPCITEMRESULT> ();
        
        JICallObject callObject = new JICallObject ( _opcItemMgt.getIpid (), true );
        callObject.setOpnum ( 1 );
        
        JIStruct struct[] = new JIStruct[items.length];
        for ( int i = 0; i < items.length; i++ )
        {
            struct[i] = items[i].toStruct ();
        }
        JIArray itemArray = new JIArray ( struct, true);
        
        callObject.addInParamAsInt ( items.length, JIFlags.FLAG_NULL );
        callObject.addInParamAsArray ( itemArray, JIFlags.FLAG_NULL );
        callObject.addOutParamAsObject ( new JIPointer ( new JIArray ( OPCITEMRESULT.getStruct (), null, 1, true ) ), JIFlags.FLAG_NULL );
        callObject.addOutParamAsObject ( new JIPointer ( new JIArray ( Integer.class, null, 1, true ) ), JIFlags.FLAG_NULL );
        
        Object result [] = _opcItemMgt.call ( callObject );
        
        JIStruct[] results = (JIStruct[]) ( (JIArray) ( (JIPointer)result[0] ).getReferent () ).getArrayInstance ();
        Integer[] errorCodes = (Integer[]) ( (JIArray) ( (JIPointer)result[1] ).getReferent () ).getArrayInstance ();
        
        KeyedResultSet<OPCITEMDEF, OPCITEMRESULT> resultList = new KeyedResultSet<OPCITEMDEF, OPCITEMRESULT> ( items.length );
        for ( int i = 0; i < items.length ; i++ )
        {
            OPCITEMRESULT itemResult = OPCITEMRESULT.fromStruct ( results[i] );
            KeyedResult<OPCITEMDEF,OPCITEMRESULT> resultEntry = new KeyedResult<OPCITEMDEF,OPCITEMRESULT> ( items[i], itemResult, errorCodes[i] );
            resultList.add ( resultEntry );
        }
        
        return resultList;
    }

}
