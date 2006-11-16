package org.openscada.opc.da.impl;

import java.net.UnknownHostException;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.core.JICallObject;
import org.jinterop.dcom.core.JIFlags;
import org.openscada.opc.da.Constants;

public class OPCAsyncIO2
{
    private IJIComObject _opcAsyncIO2 = null;

    public OPCAsyncIO2 ( IJIComObject opcAsyncIO2 ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        _opcAsyncIO2 = (IJIComObject)opcAsyncIO2.queryInterface ( Constants.IOPCAsyncIO2_IID );
    }

    public void setEnable ( boolean state ) throws JIException
    {
        JICallObject callObject = new JICallObject ( _opcAsyncIO2.getIpid (), true );
        callObject.setOpnum ( 4 );

        callObject.addInParamAsInt ( state ? 1 : 0, JIFlags.FLAG_NULL );

        _opcAsyncIO2.call ( callObject );
    }

    public int refresh ( short source, int transactionID ) throws JIException
    {
        JICallObject callObject = new JICallObject ( _opcAsyncIO2.getIpid (), true );
        callObject.setOpnum ( 2 );

        callObject.addInParamAsShort ( source, JIFlags.FLAG_NULL );
        callObject.addInParamAsInt ( transactionID, JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( Integer.class, JIFlags.FLAG_NULL );

        Object result[] = _opcAsyncIO2.call ( callObject );

        _opcAsyncIO2.call ( callObject );

        return (Integer)result[0];
    }
}
