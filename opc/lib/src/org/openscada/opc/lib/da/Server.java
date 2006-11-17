package org.openscada.opc.lib.da;

import java.net.UnknownHostException;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIClsid;
import org.jinterop.dcom.core.JIComServer;
import org.jinterop.dcom.core.JISession;
import org.openscada.opc.dcom.da.impl.OPCGroupStateMgt;
import org.openscada.opc.dcom.da.impl.OPCServer;
import org.openscada.opc.lib.common.AlreadyConnectedException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.common.NotConnectedException;

public class Server
{
    private ConnectionInformation _connectionInformation = null;
    
    private JISession _session = null;
    private JIComServer _comServer = null;
    private OPCServer _server = null;
    
    private int _defaultUpdateRate = 1000;
    private int _defaultTimeBias = 0;
    private float _defaultPercentDeadband = 0.0f;
    private int _defaultLocaleID = 0;
    
    public Server ( ConnectionInformation connectionInformation )
    {
        _connectionInformation = connectionInformation;
    }
    
    public synchronized void connect () throws IllegalArgumentException, UnknownHostException, JIException, AlreadyConnectedException
    {
        if ( _session != null )
            throw new AlreadyConnectedException ();
        
        _session = JISession.createSession ( _connectionInformation.getDomain (),
                                             _connectionInformation.getUser (),
                                             _connectionInformation.getPassword () );
        
        _comServer = new JIComServer ( JIClsid.valueOf ( _connectionInformation.getClsid () ), _connectionInformation.getHost (), _session );
        _server = new OPCServer ( _comServer.createInstance () );
    }
    
    public synchronized void disconnect () throws JIException
    {
        if ( _session != null )
        {
            JISession.destroySession ( _session );
            _session = null;
            _comServer = null;
            _server = null;
        }
    }
    
    public OPCServer getOPCServer ()
    {
        return _server;
    }
    
    public synchronized Group addGroup ( String name ) throws NotConnectedException, IllegalArgumentException, UnknownHostException, JIException
    {
        if ( _server == null )
            throw new NotConnectedException ();
        
        OPCGroupStateMgt group = _server.addGroup ( name, true, _defaultUpdateRate, 0, _defaultTimeBias, _defaultPercentDeadband, _defaultLocaleID );
        
        return new Group ( group );
    }
    
}
