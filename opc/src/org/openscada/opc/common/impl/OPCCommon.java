package org.openscada.opc.common.impl;

import java.net.UnknownHostException;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.core.JICallObject;
import org.jinterop.dcom.core.JIFlags;
import org.jinterop.dcom.core.JIPointer;
import org.jinterop.dcom.core.JIString;

public class OPCCommon
{
    private IJIComObject _opcCommonObject = null;

    public OPCCommon ( IJIComObject opcObject ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        _opcCommonObject = (IJIComObject)opcObject.queryInterface ( org.openscada.opc.common.Constants.IOPCCommon_UUID );
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
        callObject.addOutParamAsObject ( new JIString ( JIFlags.FLAG_REPRESENTATION_STRING_BSTR ), JIFlags.FLAG_NULL );

        Object[] result = _opcCommonObject.call ( callObject );
        return ( (JIString)result[0] ).getString ();
    }

    public void setClientName ( String clientName ) throws JIException
    {
        JICallObject callObject = new JICallObject ( _opcCommonObject.getIpid (), true );
        callObject.setOpnum ( 4 );

        callObject.addInParamAsString ( clientName, JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR );

        _opcCommonObject.call ( callObject );
    }

}
