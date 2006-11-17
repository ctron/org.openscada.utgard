package org.openscada.opc.lib.test;

import java.util.Map;

import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.da.Group;
import org.openscada.opc.lib.da.Item;
import org.openscada.opc.lib.da.Server;

public class OPCTest1
{
    public static void main ( String[] args ) throws Throwable
    {
        ConnectionInformation ci = new ConnectionInformation ();
        ci.setHost ( args[0] );
        ci.setDomain ( args[1] );
        ci.setUser ( args[2] );
        ci.setPassword ( args[3] );
        ci.setClsid ( args[4] );
        
        Server server = new Server ( ci );
        try
        {
            server.connect ();

            Group group = server.addGroup ( "test" );
            group.setActive ( true );

            //Group group2 = server.addGroup ( "test" );
            Group group3 = server.findGroup ( "test" );
            
            Item item = group.addItem ( "Saw-toothed Waves.Int2" );
            
            Map<String,Item> items = group.addItems ( "Saw-toothed Waves.Int4" );
            items = group.addItems ( "Saw-toothed Waves.Int4" );
            
            
        }
        catch ( JIException e )
        {
            System.out.println ( String.format ( "%08X: %s", e.getErrorCode (), server.getErrorMessage ( e.getErrorCode () ) ) );
        }
    }
}
