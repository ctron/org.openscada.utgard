package org.openscada.opc.lib.da;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.openscada.opc.dcom.da.OPCSERVERSTATUS;
import org.openscada.utils.timing.Scheduler.Job;


public class ServerStateReader
{
    private Server _server = null;
    
    private List<ServerStateListener> _listeners = new LinkedList<ServerStateListener> ();
    
    private Job _job = null;
    
    public ServerStateReader ( Server server )
    {
        super ();
        _server = server;
    }
    
    public synchronized void start ()
    {
        if ( _job != null )
            return;
        
        _job = _server.getScheduler ().addJob ( new Runnable () {

            public void run ()
            {
                once ();
            }}, 1000 );
    }
    
    public synchronized void stop ()
    {
        _server.getScheduler ().removeJob ( _job );
        _job = null;
    }

    protected synchronized void once ()
    {
        OPCSERVERSTATUS state = _server.getServerState ();
        
        for ( ServerStateListener listener : new ArrayList<ServerStateListener> ( _listeners ) )
        {
            listener.stateUpdate ( state );
        }
    }
    
    public synchronized void addListener ( ServerStateListener listener )
    {
        _listeners.add ( listener );
    }
    
    public synchronized void removeListener ( ServerStateListener listener )
    {
        _listeners.remove ( listener );
    }
}
