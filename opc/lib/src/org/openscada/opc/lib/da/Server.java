package org.openscada.opc.lib.da;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIClsid;
import org.jinterop.dcom.core.JIComServer;
import org.jinterop.dcom.core.JISession;
import org.openscada.opc.dcom.common.impl.OPCCommon;
import org.openscada.opc.dcom.da.OPCNAMESPACETYPE;
import org.openscada.opc.dcom.da.impl.OPCBrowseServerAddressSpace;
import org.openscada.opc.dcom.da.impl.OPCGroupStateMgt;
import org.openscada.opc.dcom.da.impl.OPCServer;
import org.openscada.opc.lib.common.AlreadyConnectedException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.common.NotConnectedException;
import org.openscada.opc.lib.da.browser.FlatBrowser;
import org.openscada.opc.lib.da.browser.TreeBrowser;

public class Server
{
    private ConnectionInformation _connectionInformation = null;
    
    private JISession _session = null;
    private JIComServer _comServer = null;
    private OPCServer _server = null;
    private OPCCommon _common = null;
    
    private boolean _defaultActive = true;
    private int _defaultUpdateRate = 1000;
    private Integer _defaultTimeBias = null;
    private Float _defaultPercentDeadband = null;
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
            OPCGroupStateMgt groupMgt = _server.addGroup ( name, _defaultActive, _defaultUpdateRate, 0, _defaultTimeBias, _defaultPercentDeadband, _defaultLocaleID );
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

    public Float getDefaultPercentDeadband ()
    {
        return _defaultPercentDeadband;
    }

    public void setDefaultPercentDeadband ( Float defaultPercentDeadband )
    {
        _defaultPercentDeadband = defaultPercentDeadband;
    }

    public Integer getDefaultTimeBias ()
    {
        return _defaultTimeBias;
    }

    public void setDefaultTimeBias ( Integer defaultTimeBias )
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

    public boolean isDefaultActive ()
    {
        return _defaultActive;
    }

    public void setDefaultActive ( boolean defaultActive )
    {
        _defaultActive = defaultActive;
    }
    
    public FlatBrowser getFlatBrowser ()
    {
        OPCBrowseServerAddressSpace browser = _server.getBrowser ();
        if ( browser == null )
            return null;
        
        return new FlatBrowser ( browser );
    }
    
    public TreeBrowser getTreeBrowser () throws JIException
    {
        OPCBrowseServerAddressSpace browser = _server.getBrowser ();
        if ( browser == null )
            return null;
        
        if ( browser.queryOrganization () != OPCNAMESPACETYPE.OPC_NS_HIERARCHIAL )
            return null;
        
        return new TreeBrowser ( browser );
    }
}
