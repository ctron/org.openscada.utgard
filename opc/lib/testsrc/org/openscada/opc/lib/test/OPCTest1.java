package org.openscada.opc.lib.test;

import org.openscada.opc.lib.common.ConnectionInformation;
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
        server.connect ();
    }
}
