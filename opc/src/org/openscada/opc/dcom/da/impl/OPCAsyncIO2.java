package org.openscada.opc.dcom.da.impl;

import java.net.UnknownHostException;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.core.JICallObject;
import org.jinterop.dcom.core.JIFlags;
import org.openscada.opc.dcom.common.impl.BaseCOMObject;
import org.openscada.opc.dcom.da.Constants;

public class OPCAsyncIO2 extends BaseCOMObject
{
    public OPCAsyncIO2 ( IJIComObject opcAsyncIO2 ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        super ( (IJIComObject)opcAsyncIO2.queryInterface ( Constants.IOPCAsyncIO2_IID ) );
    }

    public void setEnable ( boolean state ) throws JIException
    {
        JICallObject callObject = new JICallObject ( getCOMObject ().getIpid (), true );
        callObject.setOpnum ( 4 );

        callObject.addInParamAsInt ( state ? 1 : 0, JIFlags.FLAG_NULL );

        getCOMObject ().call ( callObject );
    }

    public int refresh ( short source, int transactionID ) throws JIException
    {
        JICallObject callObject = new JICallObject ( getCOMObject ().getIpid (), true );
        callObject.setOpnum ( 2 );

        callObject.addInParamAsShort ( source, JIFlags.FLAG_NULL );
        callObject.addInParamAsInt ( transactionID, JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( Integer.class, JIFlags.FLAG_NULL );

        Object result[] = getCOMObject ().call ( callObject );

        getCOMObject ().call ( callObject );

        return (Integer)result[0];
    }
}
