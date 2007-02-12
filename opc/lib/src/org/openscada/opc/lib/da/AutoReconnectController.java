package org.openscada.opc.lib.da;

import org.apache.log4j.Logger;

public class AutoReconnectController implements ServerConnectionStateListener
{
    private static Logger _log = Logger.getLogger ( AutoReconnectController.class );

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

        _log.debug ( "Requesting connection" );

        _requested = true;
        triggerReconnect ();
    }

    public synchronized void disconnect ()
    {
        if ( !_requested )
        {
            return;
        }

        _log.debug ( "Un-Requesting connection" );

        _requested = false;
        _server.disconnect ();
    }

    public boolean isRequested ()
    {
        return _requested;
    }

    public synchronized void connectionStateChanged ( boolean connected )
    {
        _log.debug ( "Connection state changed: " + connected );

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
        _log.debug ( "Trigger reconnect" );
        
        Thread t = new Thread ( new Runnable () {

            public void run ()
            {
                performReconnect ();
            }
        } );
        t.setDaemon ( true );
        t.start ();
    }

    private void performReconnect ()
    {
        try
        {
            _log.debug ( "Delaying..." );
            Thread.sleep ( _delay );
        }
        catch ( InterruptedException e )
        {
        }

        if ( !_requested )
        {
            _log.debug ( "Request canceled during delay" );
            return;
        }

        try
        {
            _log.debug ( "Connecting to server" );
            _server.connect ();
        }
        catch ( Exception e )
        {
            _log.info ( "Re-connect failed", e  );
            triggerReconnect ();
        }
    }

}
