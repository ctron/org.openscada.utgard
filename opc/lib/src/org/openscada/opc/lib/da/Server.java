package org.openscada.opc.lib.da;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIClsid;
import org.jinterop.dcom.core.JIComServer;
import org.jinterop.dcom.core.JISession;
import org.openscada.opc.dcom.common.impl.OPCCommon;
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
    private OPCCommon _common = null;
    
    private int _defaultUpdateRate = 1000;
    private int _defaultTimeBias = 0;
    private float _defaultPercentDeadband = 0.0f;
    private int _defaultLocaleID = 0;
    
    private Map<Integer,Group> _groups = new HashMap<Integer, Group> (); 
    
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
        _common = _server.getCommon ();
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
    
    protected synchronized Group getGroup ( OPCGroupStateMgt groupMgt ) throws JIException, IllegalArgumentException, UnknownHostException
    {
        Integer serverHandle = groupMgt.getState ().getServerHandle ();
        if ( _groups.containsKey ( serverHandle ) )
        {
            return _groups.get ( serverHandle );
        }
        else
        {
            Group group = new Group ( this, groupMgt );
            _groups.put ( serverHandle, group );
            return group;
        }
    }
    
    public synchronized Group addGroup ( String name ) throws NotConnectedException, IllegalArgumentException, UnknownHostException, JIException, DuplicateGroupException
    {
        if ( _server == null )
            throw new NotConnectedException ();
        
        try
        {
            OPCGroupStateMgt groupMgt = _server.addGroup ( name, true, _defaultUpdateRate, 0, _defaultTimeBias, _defaultPercentDeadband, _defaultLocaleID );
            return getGroup ( groupMgt );
        }
        catch ( JIException e )
        {
            switch ( e.getErrorCode () )
            {
            case 0xC004000C:
                throw new DuplicateGroupException ();
            default:
                throw e;
            }
        }
    }
    
    /**
     * Add a new group and let the server generate a group name
     * @return the new group
     * @throws IllegalArgumentException
     * @throws UnknownHostException
     * @throws NotConnectedException
     * @throws JIException
     * @throws DuplicateGroupException 
     */
    public Group addGroup () throws IllegalArgumentException, UnknownHostException, NotConnectedException, JIException, DuplicateGroupException
    {
        return addGroup ( null );
    }
    
    public Group findGroup ( String name ) throws IllegalArgumentException, UnknownHostException, JIException, UnknownGroupException
    {
        try
        {
            OPCGroupStateMgt groupMgt = _server.getGroupByName ( name );
            return getGroup ( groupMgt );
        }
        catch ( JIException e )
        {
            switch ( e.getErrorCode () )
            {
            case 0x80070057:
                throw new UnknownGroupException ( name );
            default:
                throw e;
            }
        }
    }
    
    public String getErrorMessage ( int errorCode ) throws JIException
    {
        return _common.getErrorString ( errorCode, _defaultLocaleID );
    }

    public int getDefaultLocaleID ()
    {
        return _defaultLocaleID;
    }

    public void setDefaultLocaleID ( int defaultLocaleID )
    {
        _defaultLocaleID = defaultLocaleID;
    }

    public float getDefaultPercentDeadband ()
    {
        return _defaultPercentDeadband;
    }

    public void setDefaultPercentDeadband ( float defaultPercentDeadband )
    {
        _defaultPercentDeadband = defaultPercentDeadband;
    }

    public int getDefaultTimeBias ()
    {
        return _defaultTimeBias;
    }

    public void setDefaultTimeBias ( int defaultTimeBias )
    {
        _defaultTimeBias = defaultTimeBias;
    }

    public int getDefaultUpdateRate ()
    {
        return _defaultUpdateRate;
    }

    public void setDefaultUpdateRate ( int defaultUpdateRate )
    {
        _defaultUpdateRate = defaultUpdateRate;
    }
}
