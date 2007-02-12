package org.openscada.opc.lib.da;

public class AutoReconnectController implements ServerConnectionStateListener
{
    private static final int DEFAULT_DELAY = 5 * 1000;
    
    private int _delay;
    private Server _server;
    private boolean _requested = false;
    
    public AutoReconnectController ( Server server )
    {
        this ( server, DEFAULT_DELAY );
    }
    
    public AutoReconnectController ( Server server, int delay )
    {
        super ();
        setDelay ( delay );
        
        _server = server;
        _server.addStateListener ( this );
    }

    public int getDelay ()
    {
        return _delay;
    }

    /**
     * Set the reconnect delay. If the delay less than or equal to zero it will be
     * the default delay time.
     * @param delay The delay to use
     */
    public void setDelay ( int delay )
    {
        if ( delay <= 0 )
        {
            delay = DEFAULT_DELAY; 
        }
        _delay = delay;
    }
    
    public synchronized void connect ()
    {
        if ( _requested )
        {
            return;
        }
        
        triggerReconnect ();
    }
    
    public synchronized void disconnect ()
    {
        if ( !_requested )
        {
            return;
        }
        _server.disconnect ();
    }
    
    public boolean isRequested ()
    {
        return _requested;
    }

    public synchronized void connectionStateChanged ( boolean connected )
    {
       if ( !connected )
       {
           if ( _requested )
           {
               triggerReconnect ();
           }
       }
       else
       {
           if ( !_requested )
           {
               _server.disconnect ();
           }
       }
    }

    private void triggerReconnect ()
    {
        Thread t = new Thread ( new Runnable () {

            public void run ()
            {
                performReconnect ();
            }} );
        t.setDaemon ( true );
        t.start ();
    }
    
    private void performReconnect ()
    {
        try
        {
            Thread.sleep ( _delay );
        }
        catch ( InterruptedException e )
        {
        }
        
        if ( !_requested )
        {
            return;
        }
        
        try
        {
            _server.connect ();
        }
        catch ( Exception e )
        {
            triggerReconnect ();
        }
    }
    
}
