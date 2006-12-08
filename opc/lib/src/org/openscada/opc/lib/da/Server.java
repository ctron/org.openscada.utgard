/*
 * This file is part of the OpenSCADA project
 * Copyright (C) 2006 inavare GmbH (http://inavare.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package org.openscada.opc.lib.da;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIClsid;
import org.jinterop.dcom.core.JIComServer;
import org.jinterop.dcom.core.JIProgId;
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
    
    private boolean _defaultActive = true;
    private int _defaultUpdateRate = 1000;
    private Integer _defaultTimeBias = null;
    private Float _defaultPercentDeadband = null;
    private int _defaultLocaleID = 0;
    private ErrorMessageResolver _errorMessageResolver = null;
    
    private Map<Integer,Group> _groups = new HashMap<Integer, Group> (); 
    
    private List<ServerStateListener> _stateListeners = new LinkedList<ServerStateListener> ();
    
    public Server ( ConnectionInformation connectionInformation )
    {
        _connectionInformation = connectionInformation;
    }
    
    protected synchronized boolean isConnected ()
    {
        return _session != null;
    }
    
    public synchronized void connect () throws IllegalArgumentException, UnknownHostException, JIException, AlreadyConnectedException
    {
        if ( isConnected () )
            throw new AlreadyConnectedException ();
        
        _session = JISession.createSession ( _connectionInformation.getDomain (),
                                             _connectionInformation.getUser (),
                                             _connectionInformation.getPassword () );
        
        if ( _connectionInformation.getClsid () != null )
            _comServer = new JIComServer ( JIClsid.valueOf ( _connectionInformation.getClsid () ), _connectionInformation.getHost (), _session );
        else if ( _connectionInformation.getProgId () != null )
            _comServer = new JIComServer ( JIProgId.valueOf ( _session, _connectionInformation.getClsid () ), _connectionInformation.getHost (), _session );
        else
            throw new IllegalArgumentException ( "Neither clsid nor progid is valid!" );
        
        _server = new OPCServer ( _comServer.createInstance () );
        _errorMessageResolver = new ErrorMessageResolver ( _server.getCommon (), _defaultLocaleID );
        
        notifyConnectionStateChange ( true );
    }
    
    public synchronized void disconnect () throws JIException
    {
        if ( !isConnected () )
            return;
        
        try
        {
            JISession.destroySession ( _session );
        }
        finally
        {
            _errorMessageResolver = null;
            _session = null;
            _comServer = null;
            _server = null;
            notifyConnectionStateChange ( false );
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
    
    /**
     * Add a new named group to the server
     * @param name The name of the group to use (must be unique)
     * @return The new group
     * @throws NotConnectedException If the server is not connected using {@link Server#connect()}
     * @throws IllegalArgumentException
     * @throws UnknownHostException
     * @throws JIException
     * @throws DuplicateGroupException If a group with this name already exists
     */
    public synchronized Group addGroup ( String name ) throws NotConnectedException, IllegalArgumentException, UnknownHostException, JIException, DuplicateGroupException
    {
        if ( !isConnected () )
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
     * 
     * Actually this method only calls {@link Server#addGroup(String)} with <code>null</code>
     * as parameter.
     * 
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
    
    /**
     * Find a group by its name
     * @param name The name to look for
     * @return The group
     * @throws IllegalArgumentException
     * @throws UnknownHostException
     * @throws JIException
     * @throws UnknownGroupException If the group was not found
     * @throws NotConnectedException If the server is not connected
     */
    public Group findGroup ( String name ) throws IllegalArgumentException, UnknownHostException, JIException, UnknownGroupException, NotConnectedException
    {
        if ( !isConnected () )
            throw new NotConnectedException ();
        
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
    
    /**
     * Get the flat browser
     * @return The flat browser or <code>null</code> if the functionality is not supported 
     */
    public FlatBrowser getFlatBrowser ()
    {
        OPCBrowseServerAddressSpace browser = _server.getBrowser ();
        if ( browser == null )
            return null;
        
        return new FlatBrowser ( browser );
    }
    
    /**
     * Get the tree browser
     * @return The tree browser or <code>null</code> if the functionality is not supported
     * @throws JIException
     */
    public TreeBrowser getTreeBrowser () throws JIException
    {
        OPCBrowseServerAddressSpace browser = _server.getBrowser ();
        if ( browser == null )
            return null;
        
        if ( browser.queryOrganization () != OPCNAMESPACETYPE.OPC_NS_HIERARCHIAL )
            return null;
        
        return new TreeBrowser ( browser );
    }
    
    public synchronized String getErrorMessage ( int errorCode )
    {
        if ( _errorMessageResolver == null )
            return String.format ( "Unknown error (%08X)", errorCode );
        
        // resolve message
        String message = _errorMessageResolver.getMessage ( errorCode );
        
        // and return if successfull
        if ( message != null )
            return message;
        
        // return default message
        return String.format ( "Unknown error (%08X)", errorCode );
    }
    
    public synchronized void addStateListener ( ServerStateListener listener )
    {
        _stateListeners.add ( listener );
        listener.connectionStateChanged ( isConnected () );
    }
    
    public synchronized void removeStateListener ( ServerStateListener listener )
    {
        _stateListeners.remove ( listener );
    }
    
    protected synchronized void notifyConnectionStateChange ( boolean connected )
    {
        List<ServerStateListener> list = new ArrayList<ServerStateListener> ( _stateListeners );
        for ( ServerStateListener listener : list )
        {
            listener.connectionStateChanged ( connected );
        }
    }
}
