package org.openscada.opc.lib.da;

import org.openscada.opc.dcom.da.OPCSERVERSTATUS;


public class ServerStateReader
{
    private Server _server = null;
    
    private Thread _thread = null;
    
    public ServerStateReader ( Server server )
    {
        super ();
        _server = server;
        
        _server.getScheduler ().addJob ( new Runnable () {

            public void run ()
            {
                once ();
            }}, 1000 );
    }

    protected void once ()
    {
        OPCSERVERSTATUS state = _server.getServerState ();
    }
}
