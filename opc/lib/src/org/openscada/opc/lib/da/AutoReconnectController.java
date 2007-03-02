package org.openscada.opc.lib.da;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

public class AutoReconnectController implements ServerConnectionStateListener
{
    private static Logger _log = Logger.getLogger ( AutoReconnectController.class );

    private static final int DEFAULT_DELAY = 5 * 1000;

    private int _delay;

    private Server _server;

    private Set<AutoReconnectListener> _listeners = new HashSet<AutoReconnectListener> ();

    private AutoReconnectState _state = AutoReconnectState.DISABLED;
    
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

    public synchronized void addListener ( AutoReconnectListener listener )
    {
        if ( listener != null )
        {
            _listeners.add ( listener );
            listener.stateChanged ( _state );
        }
    }
    
    public synchronized void removeListener ( AutoReconnectListener listener )
    {
        _listeners.remove ( listener );
    }
    
    protected synchronized void notifyStateChange ( AutoReconnectState state )
    {
        _state = state;
        for ( AutoReconnectListener listener : _listeners )
        {
            listener.stateChanged ( state );
        }
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
        if ( isRequested () )
        {
            return;
        }

        _log.debug ( "Requesting connection" );
        notifyStateChange ( AutoReconnectState.DISCONNECTED );
        
        triggerReconnect ();
    }

    public synchronized void disconnect ()
    {
        if ( !isRequested () )
        {
            return;
        }

        _log.debug ( "Un-Requesting connection" );

        notifyStateChange ( AutoReconnectState.DISABLED );
        _server.disconnect ();
    }

    public boolean isRequested ()
    {
        return _state != AutoReconnectState.DISABLED;
    }

    public synchronized void connectionStateChanged ( boolean connected )
    {
        _log.debug ( "Connection state changed: " + connected );

        if ( !connected )
        {
            if ( isRequested () )
            {
                notifyStateChange ( AutoReconnectState.DISCONNECTED );
                triggerReconnect ();
            }
        }
        else
        {
            if ( !isRequested () )
            {
                _server.disconnect ();
            }
            else
            {
                notifyStateChange ( AutoReconnectState.CONNECTED );
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
            notifyStateChange ( AutoReconnectState.WAITING );
            _log.debug ( "Delaying..." );
            Thread.sleep ( _delay );
        }
        catch ( InterruptedException e )
        {
        }

        if ( !isRequested () )
        {
            _log.debug ( "Request canceled during delay" );
            return;
        }

        try
        {
            _log.debug ( "Connecting to server" );
            notifyStateChange ( AutoReconnectState.CONNECTING );
            _server.connect ();
            // CONNECTED state will be set by server callback
        }
        catch ( Exception e )
        {
            _log.info ( "Re-connect failed", e  );
            notifyStateChange ( AutoReconnectState.DISCONNECTED );
            triggerReconnect ();
        }
    }

}
