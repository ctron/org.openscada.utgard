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
import org.openscada.opc.common.impl.OPCCommon;
import org.openscada.opc.da.Constants;
import org.openscada.opc.da.OPCSERVERSTATUS;

public class OPCServer extends OPCCommon
{
    private JISession _session = null;

    private JIComServer _comServer = null;

    private IJIComObject _opcServerObject = null;

    public OPCServer ( IJIComObject opcServer ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        super ( opcServer );
        _opcServerObject = (IJIComObject)opcServer.queryInterface ( Constants.IOPCServer_UUID );
    }

    public OPCSERVERSTATUS GetStatus () throws JIException
    {
        JICallObject callObject = new JICallObject ( _opcServerObject.getIpid (), true );

        callObject.setOpnum ( 3 );

        JIStruct status = OPCSERVERSTATUS.getStruct ();

        callObject.addOutParamAsObject ( new JIString ( JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR ), JIFlags.FLAG_NULL );

        Object[] result = _opcServerObject.call ( callObject );

        System.out.println ( result.toString () );

        return null;
    }

    
}
