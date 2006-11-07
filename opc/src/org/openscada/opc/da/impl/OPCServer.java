package org.openscada.opc.da.impl;

import java.net.UnknownHostException;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.core.JICallObject;
import org.jinterop.dcom.core.JIClsid;
import org.jinterop.dcom.core.JIComServer;
import org.jinterop.dcom.core.JIFlags;
import org.jinterop.dcom.core.JIPointer;
import org.jinterop.dcom.core.JIProgId;
import org.jinterop.dcom.core.JISession;
import org.jinterop.dcom.core.JIString;
import org.jinterop.dcom.core.JIStruct;
import org.jinterop.dcom.core.JIVariant;
import org.jinterop.dcom.win32.ComFactory;
import org.jinterop.dcom.win32.IJIDispatch;
import org.openscada.opc.da.Constants;
import org.openscada.opc.da.OPCSERVERSTATUS;

public class OPCServer
{
    private JISession _session = null;

    private JIComServer _comServer = null;

    private IJIComObject _comObject = null;

    private IJIComObject _opcServerObject = null;

    private IJIComObject _opcCommonObject = null;

    public OPCServer ( String address, JIClsid clsid, JISession session ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        _comServer = new JIComServer ( clsid, address, session );
        _comObject = _comServer.createInstance ();
        _opcServerObject = (IJIComObject)_comObject.queryInterface ( Constants.IOPCServer_UUID );
        _opcCommonObject = (IJIComObject)_comObject
                .queryInterface ( org.openscada.opc.common.Constants.IOPCCommon_UUID );
    }

    public OPCServer ( String address, JIProgId progid, JISession session ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        _comServer = new JIComServer ( progid, address, session );
        _comObject = _comServer.createInstance ();
        _opcServerObject = (IJIComObject)_comObject.queryInterface ( Constants.IOPCServer_UUID );
        _opcCommonObject = (IJIComObject)_comObject
                .queryInterface ( org.openscada.opc.common.Constants.IOPCCommon_UUID );
    }

    public OPCSERVERSTATUS GetStatus () throws JIException
    {
        JICallObject callObject = new JICallObject ( _comObject.getIpid (), true );

        callObject.setOpnum ( 3 );

        JIStruct status = OPCSERVERSTATUS.getStruct ();

        callObject.addOutParamAsObject ( new JIString ( JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR ), JIFlags.FLAG_NULL );

        Object[] result = _comObject.call ( callObject );

        System.out.println ( result.toString () );

        return null;
    }

    public String getErrorString ( int errorCode ) throws JIException
    {
        JICallObject callObject = new JICallObject ( _opcCommonObject.getIpid (), true );
        callObject.setOpnum ( 4 );

        callObject.addInParamAsInt ( errorCode, JIFlags.FLAG_NULL );
        callObject.addInParamAsInt ( (Integer)1033, JIFlags.FLAG_NULL );
        callObject.addOutParamAsObject ( new JIString ( JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR ), JIFlags.FLAG_NULL );

        Object[] result = _opcCommonObject.call ( callObject );
        return ( (JIString)result[0] ).getString ();
    }

    public void setClientName ( String clientName ) throws JIException
    {
        JICallObject callObject = new JICallObject ( _opcCommonObject.getIpid (), true );
        callObject.setOpnum ( 5 );

        callObject.addInParamAsString ( clientName, JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR );

        _opcCommonObject.call ( callObject );
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

        Object [] result = _opcCommonObject.call ( callObject );
        return (Integer)result[0];
    }
}
