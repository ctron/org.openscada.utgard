package org.openscada.opc.da.test;

import java.net.UnknownHostException;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.common.JISystem;
import org.jinterop.dcom.core.JIClsid;
import org.jinterop.dcom.core.JIComServer;
import org.jinterop.dcom.core.JISession;
import org.openscada.opc.da.impl.OPCServer;

public class Test1
{
    public static void showError ( OPCServer server, int errorCode ) throws JIException
    {
        System.out.println ( String.format ( "Error (%X): '%s'", errorCode, server.getErrorString ( errorCode, 1033 ) ) );
    }
    
    public static void main ( String[] args ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        JISession session = null;
        OPCServer server = null;
        try
        {
            JISystem.setAutoRegisteration ( true );

            session = JISession.createSession ( args[1], args[2], args[3] );
            // OPCServer server = new OPCServer ( "127.0.0.1", JIProgId.valueOf
            // ( session, "Matrikon.OPC.Simulation.1" ),
            // session );
            JIComServer comServer = new JIComServer ( JIClsid.valueOf ( "F8582CF2-88FB-11D0-B850-00C0F0104305" ),
                    args[0], session );
            server = new OPCServer ( comServer.createInstance () );

            // server.GetStatus ();
            server.setLocaleID ( 1033 );
            System.out.println ( String.format ( "LCID: %d", server.getLocaleID () ) );
            server.setClientName ( "test" );
            for ( Integer i : server.queryAvailableLocaleIDs () )
            {
                System.out.println ( String.format ( "Available LCID: %d", i ) );
            }
            //showError ( server, 0x800706F7 );
            //server.addGroup ( "", true, 1000, 1, 0, 0.0f, 1033 );
           //server.getGroupByName ( "test" );
            server.getStatus ();
        }
        catch ( JIException e )
        {
            e.printStackTrace ();
            showError ( server, e.getErrorCode () );
        }
        finally
        {
            JISession.destroySession ( session );
        }
    }
}
