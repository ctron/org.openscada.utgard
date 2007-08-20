package org.openscada.opc.lib.da;

import org.apache.log4j.Logger;
import org.openscada.opc.dcom.da.OPCSERVERSTATUS;
import org.openscada.opc.dcom.da.impl.OPCServer;

/**
 * A server state operation which can be interruped
 * @author Jens Reimann
 *
 */
public class ServerStateOperation implements Runnable
{
    private static Logger _log = Logger.getLogger ( ServerStateOperation.class );
    
    public OPCSERVERSTATUS _serverStatus = null;
    public OPCServer _server;
    public Throwable _error;
    public Object _lock = new Object ();
    public boolean _running = false;

    public ServerStateOperation ( OPCServer server )
    {
        super ();
        _server = server;
    }
    
    /**
     * Perform the operation.
     * <p>
     * This method will block until either the serve state has been aquired or the
     * timeout triggers cancels the call.
     * </p>
     */
    public void run ()
    {
        synchronized ( _lock )
        {
            _running = true;
        }
        try
        {
            _serverStatus = _server.getStatus ();
            synchronized ( _lock )
            {
                _running = false;
                _lock.notify ();
            }
        }
        catch ( Throwable e )
        {
            _error = e;
            _running = false;
            synchronized ( _lock )
            {
                _lock.notify ();
            }
        }

    }
    
    /**
     * Get the server state with a timeout.
     * @return the server state or <code>null</code> if the server is not set.
     * @param timeout the timeout in ms
     * @throws Throwable any error that occurred
     */
    public OPCSERVERSTATUS getServerState ( int timeout ) throws Throwable
    {
        if ( _server == null )
        {
            return null;
        }
                
        Thread t = new Thread ( this );
        
        synchronized ( _lock )
        {
            t.start ();
            _lock.wait ( timeout );
            if ( _running )
            {
                _log.warn ( "State operation still running. Interrupting..." );
                t.interrupt ();
                throw new InterruptedException ( "Interrupted getting server state" );
            }
        }
        if ( _error != null )
        {
            _log.warn ( "An error occurred while getting server state", _error );
            throw _error;
        }
         
        return _serverStatus;
    }

}
