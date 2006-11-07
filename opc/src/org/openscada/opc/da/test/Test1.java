package org.openscada.opc.da.test;

import java.net.UnknownHostException;

import org.jinterop.dcom.common.IJIAuthInfo;
import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.common.JISystem;
import org.jinterop.dcom.core.JIClsid;
import org.jinterop.dcom.core.JIProgId;
import org.jinterop.dcom.core.JISession;
import org.openscada.opc.da.impl.OPCServer;

public class Test1
{
    public static void main ( String[] args ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        JISession session = null;
        try
        {
            JISystem.setAutoRegisteration ( true );

            session = JISession.createSession ( args[1], args[2], args[3] );
            // OPCServer server = new OPCServer ( "127.0.0.1", JIProgId.valueOf
            // ( session, "Matrikon.OPC.Simulation.1" ),
            // session );
            OPCServer server = new OPCServer ( args[0], JIClsid.valueOf ( "F8582CF2-88FB-11D0-B850-00C0F0104305" ),
                    session );

            // server.GetStatus ();
            server.setLocaleID ( 1033 );
            System.out.println ( String.format ( "LCID: %d", server.getLocaleID () ) );
            //server.setClientName ( "test" );
            //server.getErrorString ( 0x80020001 );
        }
        finally
        {
            JISession.destroySession ( session );
        }
    }
}
