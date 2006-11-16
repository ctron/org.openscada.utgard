package org.openscada.opc.common.impl;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.core.JIArray;
import org.jinterop.dcom.core.JICallObject;
import org.jinterop.dcom.core.JIFlags;
import org.jinterop.dcom.core.JIPointer;
import org.jinterop.dcom.core.JIString;

public class OPCCommon
{
    private IJIComObject _opcCommonObject = null;

    public OPCCommon ( IJIComObject opcObject ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        _opcCommonObject = (IJIComObject)opcObject.queryInterface ( org.openscada.opc.common.Constants.IOPCCommon_IID );
    }

    public void setLocaleID ( int localeID ) throws JIException
    {
        JICallObject callObject = new JICallObject ( _opcCommonObject.getIpid (), true );
        callObject.setOpnum ( 0 );

        callObject.addInParamAsInt ( localeID, JIFlags.FLAG_NULL );

        _opcCommonObject.call ( callObject );
    }

    public int getLocaleID () throws JIException
    {
        JICallObject callObject = new JICallObject ( _opcCommonObject.getIpid (), true );
        callObject.setOpnum ( 1 );

        callObject.addOutParamAsObject ( Integer.class, JIFlags.FLAG_NULL );

        Object[] result = _opcCommonObject.call ( callObject );
        return (Integer)result[0];
    }

    public String getErrorString ( int errorCode, int localeID ) throws JIException
    {
        JICallObject callObject = new JICallObject ( _opcCommonObject.getIpid (), true );
        callObject.setOpnum ( 3 );

        callObject.addInParamAsInt ( errorCode, JIFlags.FLAG_NULL );
        callObject.addInParamAsInt ( localeID, JIFlags.FLAG_NULL );
        callObject.addOutParamAsObject ( new JIPointer ( new JIString ( JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR ) ), JIFlags.FLAG_NULL );

        Object[] result = _opcCommonObject.call ( callObject );
        return ( (JIString) ( ( (JIPointer)result[0] ).getReferent () ) ).getString ();
    }

    public void setClientName ( String clientName ) throws JIException
    {
        JICallObject callObject = new JICallObject ( _opcCommonObject.getIpid (), true );
        callObject.setOpnum ( 4 );

        callObject.addInParamAsString ( clientName, JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR );

        _opcCommonObject.call ( callObject );
    }

    public Collection<Integer> queryAvailableLocaleIDs () throws JIException
    {
        JICallObject callObject = new JICallObject ( _opcCommonObject.getIpid (), true );
        callObject.setOpnum ( 2 );

        callObject.addOutParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        callObject.addOutParamAsObject ( new JIPointer ( new JIArray ( Integer.class, null, 1, true ) ), JIFlags.FLAG_NULL );

        Object[] result = _opcCommonObject.call ( callObject );

        JIArray resultArray = (JIArray) ( (JIPointer)result[1] ).getReferent ();
        Integer[] intArray = (Integer[])resultArray.getArrayInstance ();

        return Arrays.asList ( intArray );
    }

}
