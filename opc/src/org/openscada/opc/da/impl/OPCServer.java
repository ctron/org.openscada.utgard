package org.openscada.opc.da.impl;

import java.net.UnknownHostException;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.core.IJIUnknown;
import org.jinterop.dcom.core.JICallObject;
import org.jinterop.dcom.core.JIFlags;
import org.jinterop.dcom.core.JIInterfacePointer;
import org.jinterop.dcom.core.JIPointer;
import org.jinterop.dcom.core.JIString;
import org.jinterop.dcom.core.JIStruct;
import org.jinterop.dcom.core.JIVariant;
import org.jinterop.dcom.win32.ComFactory;
import org.openscada.opc.common.impl.OPCCommon;
import org.openscada.opc.da.Constants;
import org.openscada.opc.da.OPCSERVERSTATUS;

public class OPCServer extends OPCCommon
{
    private IJIComObject _opcServerObject = null;

    public OPCServer ( IJIComObject opcServer ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        super ( opcServer );
        _opcServerObject = (IJIComObject)opcServer.queryInterface ( Constants.IOPCServer_IID );
    }

    public OPCSERVERSTATUS getStatus () throws JIException
    {
        JICallObject callObject = new JICallObject ( _opcServerObject.getIpid (), true );
        callObject.setOpnum ( 3 );

        JIStruct status = OPCSERVERSTATUS.getStruct ();

        callObject.addOutParamAsObject ( new JIPointer ( status, false ), JIFlags.FLAG_NULL );

        Object[] result = _opcServerObject.call ( callObject );

        System.out.println ( result.toString () );

        return null;
    }
    
    public void addGroup ( String name, boolean active, int updateRate, int clientHandle,
            int timeBias, float percentDeadband, int localeID ) throws JIException
    {
        JICallObject callObject = new JICallObject ( _opcServerObject.getIpid (), true );
        callObject.setOpnum ( 0 );
        
        callObject.addInParamAsString ( name, JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR );
        callObject.addInParamAsInt ( active ? 1 : 0, JIFlags.FLAG_NULL );
        callObject.addInParamAsInt ( updateRate, JIFlags.FLAG_NULL );
        callObject.addInParamAsInt ( clientHandle, JIFlags.FLAG_NULL );
        callObject.addInParamAsInt ( timeBias, JIFlags.FLAG_NULL );
        callObject.addInParamAsFloat ( percentDeadband, JIFlags.FLAG_NULL );
        callObject.addInParamAsInt ( localeID, JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        callObject.addInParamAsUUID ( Constants.IOPCGroupStateMgt_IID, JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( JIInterfacePointer.class, JIFlags.FLAG_NULL );
        
        /*
        [in, string]        LPCWSTR    szName,
        [in]                BOOL       bActive,
        [in]                DWORD      dwRequestedUpdateRate,
        [in]                OPCHANDLE  hClientGroup,
        [unique, in]        LONG*      pTimeBias,
        [unique, in]        FLOAT*     pPercentDeadband,
        [in]                DWORD      dwLCID,
        [out]               OPCHANDLE* phServerGroup,
        [out]               DWORD*     pRevisedUpdateRate,
        [in]                REFIID     riid,
        [out, iid_is(riid)] LPUNKNOWN* ppUnk
         */
        
        Object [] result = _opcServerObject.call ( callObject );
    }
    
    /*
    public IJIUnknown createInstance(String riid) throws JIException
    {
        JICallObject callObject = new JICallObject(comObject.getIpid(),true);
        callObject.setOpnum(13);
        
        callObject.addInParamAsUUID(riid,JIFlags.FLAG_NULL);
        callObject.addOutParamAsType(JIInterfacePointer.class,JIFlags.FLAG_NULL);
        Object[] result = comObject.call(callObject);
        return ComFactory.createCOMInstance(comObject,(JIInterfacePointer)result[0]);
    }
    */
}
