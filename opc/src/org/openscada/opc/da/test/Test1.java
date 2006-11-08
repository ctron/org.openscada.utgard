package org.openscada.opc.da.test;

import java.net.UnknownHostException;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.common.JISystem;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.core.IJIUnknown;
import org.jinterop.dcom.core.JIClsid;
import org.jinterop.dcom.core.JIComServer;
import org.jinterop.dcom.core.JISession;
import org.openscada.opc.da.OPCGroupState;
import org.openscada.opc.da.impl.OPCBrowseServerAddressSpace;
import org.openscada.opc.da.impl.OPCGroup;
import org.openscada.opc.da.impl.OPCServer;

public class Test1
{
    public static void showError ( OPCServer server, int errorCode ) throws JIException
    {
        System.out.println ( String.format ( "Error (%X): '%s'", errorCode, server.getErrorString ( errorCode, 1033 ) ) );
    }
    
    public static void browse ( OPCBrowseServerAddressSpace browser ) throws JIException
    {
        System.out.println ( String.format ( "Organization: %s", browser.queryOrganization () ) );
    }
    
    public static void dumpGroupState ( OPCGroup group ) throws JIException
    {
        OPCGroupState state = group.getState ();
        
        System.out.println ( "Name: " + state.getName () );
        System.out.println ( "Active: " + state.isActive () );
        System.out.println ( "Update Rate: " + state.getUpdateRate () );
        System.out.println ( "Time Bias: " + state.getTimeBias () );
        System.out.println ( "Percent Deadband: " + state.getPercentDeadband () );
        System.out.println ( "Locale ID: " + state.getLocaleID () );
        System.out.println ( "Client Handle: " + state.getClientHandle () );
        System.out.println ( "Server Handle: " + state.getServerHandle () );
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
            IJIComObject serverObject = comServer.createInstance ();
            server = new OPCServer ( serverObject );
            
            // server.GetStatus ();
            server.setLocaleID ( 1033 );
            System.out.println ( String.format ( "LCID: %d", server.getLocaleID () ) );
            server.setClientName ( "test" );
            for ( Integer i : server.queryAvailableLocaleIDs () )
            {
                System.out.println ( String.format ( "Available LCID: %d", i ) );
            }
            OPCBrowseServerAddressSpace serverBrowser = new OPCBrowseServerAddressSpace (serverObject);
            browse ( serverBrowser );

           OPCGroup group = server.addGroup ( "test", true, 1000, 1234, 0, 0.0f, 1033 );
           group = server.getGroupByName ( "test" );
           dumpGroupState ( group );
           server.removeGroup ( group, true );
           //server.getGroupByName ( "test" );
           // server.getStatus ();
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
