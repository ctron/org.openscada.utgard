/*
 * This file is part of the OpenSCADA project
 * Copyright (C) 2006-2009 inavare GmbH (http://inavare.com)
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledExecutorService;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIClsid;
import org.jinterop.dcom.core.JIComServer;
import org.jinterop.dcom.core.JIProgId;
import org.jinterop.dcom.core.JISession;
import org.openscada.opc.dcom.da.OPCNAMESPACETYPE;
import org.openscada.opc.dcom.da.OPCSERVERSTATUS;
import org.openscada.opc.dcom.da.impl.OPCBrowseServerAddressSpace;
import org.openscada.opc.dcom.da.impl.OPCGroupStateMgt;
import org.openscada.opc.dcom.da.impl.OPCServer;
import org.openscada.opc.lib.common.AlreadyConnectedException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.common.NotConnectedException;
import org.openscada.opc.lib.da.browser.FlatBrowser;
import org.openscada.opc.lib.da.browser.TreeBrowser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server
{
    private static Logger _log = LoggerFactory.getLogger ( Server.class );

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

    private final Map<Integer, Group> _groups = new HashMap<Integer, Group> ();

    private final List<ServerConnectionStateListener> _stateListeners = new CopyOnWriteArrayList<ServerConnectionStateListener> ();

    private ScheduledExecutorService _scheduler = null;

    public Server ( final ConnectionInformation connectionInformation, final ScheduledExecutorService scheduler )
    {
        super ();
        this._connectionInformation = connectionInformation;
        this._scheduler = scheduler;
    }

    /**
     * Gets the scheduler for the server. Note that this scheduler might get blocked for
     * a short time if the connection breaks. It should not be used for time critical
     * operations.
     * @return the scheduler for the server
     */
    public ScheduledExecutorService getScheduler ()
    {
        return this._scheduler;
    }

    protected synchronized boolean isConnected ()
    {
        return this._session != null;
    }

    public synchronized void connect () throws IllegalArgumentException, UnknownHostException, JIException, AlreadyConnectedException
    {
        if ( isConnected () )
        {
            throw new AlreadyConnectedException ();
        }

        final int socketTimeout = Integer.getInteger ( "rpc.socketTimeout", 0 );
        _log.info ( String.format ( "Socket timeout: %s ", socketTimeout ) );

        try
        {
            if ( this._connectionInformation.getClsid () != null )
            {
                this._session = JISession.createSession ( this._connectionInformation.getDomain (), this._connectionInformation.getUser (), this._connectionInformation.getPassword () );
                this._session.setGlobalSocketTimeout ( socketTimeout );
                this._comServer = new JIComServer ( JIClsid.valueOf ( this._connectionInformation.getClsid () ), this._connectionInformation.getHost (), this._session );
            }
            else if ( this._connectionInformation.getProgId () != null )
            {
                this._session = JISession.createSession ( this._connectionInformation.getDomain (), this._connectionInformation.getUser (), this._connectionInformation.getPassword () );
                this._session.setGlobalSocketTimeout ( socketTimeout );
                this._comServer = new JIComServer ( JIProgId.valueOf ( this._connectionInformation.getClsid () ), this._connectionInformation.getHost (), this._session );
            }
            else
            {
                throw new IllegalArgumentException ( "Neither clsid nor progid is valid!" );
            }

            this._server = new OPCServer ( this._comServer.createInstance () );
            this._errorMessageResolver = new ErrorMessageResolver ( this._server.getCommon (), this._defaultLocaleID );
        }
        catch ( final UnknownHostException e )
        {
            _log.info ( "Unknown host when connecting to server", e );
            cleanup ();
            throw e;
        }
        catch ( final JIException e )
        {
            _log.info ( "Failed to connect to server", e );
            cleanup ();
            throw e;
        }
        catch ( final Throwable e )
        {
            _log.warn ( "Unknown error", e );
            cleanup ();
            throw new RuntimeException ( e );
        }

        notifyConnectionStateChange ( true );
    }

    /**
     * cleanup after the connection is closed
     */
    protected void cleanup ()
    {
        _log.info ( "Destroying DCOM session..." );
        final JISession destructSession = this._session;
        final Thread destructor = new Thread ( new Runnable () {

            public void run ()
            {
                final long ts = System.currentTimeMillis ();
                try
                {
                    _log.debug ( "Starting destruction of DCOM session" );
                    JISession.destroySession ( destructSession );
                    _log.info ( "Destructed DCOM session" );
                }
                catch ( final Throwable e )
                {
                    _log.warn ( "Failed to destruct DCOM session", e );
                }
                finally
                {
                    _log.info ( String.format ( "Session destruction took %s ms", System.currentTimeMillis () - ts ) );
                }
            }
        }, "UtgardSessionDestructor" );
        destructor.setName ( "OPCSessionDestructor" );
        destructor.setDaemon ( true );
        destructor.start ();
        _log.info ( "Destroying DCOM session... forked" );

        this._errorMessageResolver = null;
        this._session = null;
        this._comServer = null;
        this._server = null;

        this._groups.clear ();
    }

    /**
     * Disconnect the connection if it is connected
     */
    public synchronized void disconnect ()
    {
        if ( !isConnected () )
        {
            return;
        }

        try
        {
            notifyConnectionStateChange ( false );
        }
        catch ( final Throwable t )
        {
        }

        cleanup ();
    }

    /**
     * Dispose the connection in the case of an error
     */
    public void dispose ()
    {
        disconnect ();
    }

    protected synchronized Group getGroup ( final OPCGroupStateMgt groupMgt ) throws JIException, IllegalArgumentException, UnknownHostException
    {
        final Integer serverHandle = groupMgt.getState ().getServerHandle ();
        if ( this._groups.containsKey ( serverHandle ) )
        {
            return this._groups.get ( serverHandle );
        }
        else
        {
            final Group group = new Group ( this, serverHandle, groupMgt );
            this._groups.put ( serverHandle, group );
            return group;
        }
    }

    /**
     * Add a new named group to the server
     * @param name The name of the group to use. Must be unique or <code>null</code> so that the server creates a unique name.
     * @return The new group
     * @throws NotConnectedException If the server is not connected using {@link Server#connect()}
     * @throws IllegalArgumentException
     * @throws UnknownHostException
     * @throws JIException
     * @throws DuplicateGroupException If a group with this name already exists
     */
    public synchronized Group addGroup ( final String name ) throws NotConnectedException, IllegalArgumentException, UnknownHostException, JIException, DuplicateGroupException
    {
        if ( !isConnected () )
        {
            throw new NotConnectedException ();
        }

        try
        {
            final OPCGroupStateMgt groupMgt = this._server.addGroup ( name, this._defaultActive, this._defaultUpdateRate, 0, this._defaultTimeBias, this._defaultPercentDeadband, this._defaultLocaleID );
            return getGroup ( groupMgt );
        }
        catch ( final JIException e )
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
    public Group findGroup ( final String name ) throws IllegalArgumentException, UnknownHostException, JIException, UnknownGroupException, NotConnectedException
    {
        if ( !isConnected () )
        {
            throw new NotConnectedException ();
        }

        try
        {
            final OPCGroupStateMgt groupMgt = this._server.getGroupByName ( name );
            return getGroup ( groupMgt );
        }
        catch ( final JIException e )
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
        return this._defaultLocaleID;
    }

    public void setDefaultLocaleID ( final int defaultLocaleID )
    {
        this._defaultLocaleID = defaultLocaleID;
    }

    public Float getDefaultPercentDeadband ()
    {
        return this._defaultPercentDeadband;
    }

    public void setDefaultPercentDeadband ( final Float defaultPercentDeadband )
    {
        this._defaultPercentDeadband = defaultPercentDeadband;
    }

    public Integer getDefaultTimeBias ()
    {
        return this._defaultTimeBias;
    }

    public void setDefaultTimeBias ( final Integer defaultTimeBias )
    {
        this._defaultTimeBias = defaultTimeBias;
    }

    public int getDefaultUpdateRate ()
    {
        return this._defaultUpdateRate;
    }

    public void setDefaultUpdateRate ( final int defaultUpdateRate )
    {
        this._defaultUpdateRate = defaultUpdateRate;
    }

    public boolean isDefaultActive ()
    {
        return this._defaultActive;
    }

    public void setDefaultActive ( final boolean defaultActive )
    {
        this._defaultActive = defaultActive;
    }

    /**
     * Get the flat browser
     * @return The flat browser or <code>null</code> if the functionality is not supported 
     */
    public FlatBrowser getFlatBrowser ()
    {
        final OPCBrowseServerAddressSpace browser = this._server.getBrowser ();
        if ( browser == null )
        {
            return null;
        }

        return new FlatBrowser ( browser );
    }

    /**
     * Get the tree browser
     * @return The tree browser or <code>null</code> if the functionality is not supported
     * @throws JIException
     */
    public TreeBrowser getTreeBrowser () throws JIException
    {
        final OPCBrowseServerAddressSpace browser = this._server.getBrowser ();
        if ( browser == null )
        {
            return null;
        }

        if ( browser.queryOrganization () != OPCNAMESPACETYPE.OPC_NS_HIERARCHIAL )
        {
            return null;
        }

        return new TreeBrowser ( browser );
    }

    public synchronized String getErrorMessage ( final int errorCode )
    {
        if ( this._errorMessageResolver == null )
        {
            return String.format ( "Unknown error (%08X)", errorCode );
        }

        // resolve message
        final String message = this._errorMessageResolver.getMessage ( errorCode );

        // and return if successfull
        if ( message != null )
        {
            return message;
        }

        // return default message
        return String.format ( "Unknown error (%08X)", errorCode );
    }

    public synchronized void addStateListener ( final ServerConnectionStateListener listener )
    {
        this._stateListeners.add ( listener );
        listener.connectionStateChanged ( isConnected () );
    }

    public synchronized void removeStateListener ( final ServerConnectionStateListener listener )
    {
        this._stateListeners.remove ( listener );
    }

    protected void notifyConnectionStateChange ( final boolean connected )
    {
        final List<ServerConnectionStateListener> list = new ArrayList<ServerConnectionStateListener> ( this._stateListeners );
        for ( final ServerConnectionStateListener listener : list )
        {
            listener.connectionStateChanged ( connected );
        }
    }

    public OPCSERVERSTATUS getServerState ( final int timeout ) throws Throwable
    {
        return new ServerStateOperation ( this._server ).getServerState ( timeout );
    }

    public OPCSERVERSTATUS getServerState ()
    {
        try
        {
            return getServerState ( 2500 );
        }
        catch ( final Throwable e )
        {
            _log.info ( "Server connection failed", e );
            dispose ();
            return null;
        }
    }

    public void removeGroup ( final Group group, final boolean force ) throws JIException
    {
        if ( this._groups.containsKey ( group.getServerHandle () ) )
        {
            this._server.removeGroup ( group.getServerHandle (), force );
            this._groups.remove ( group.getServerHandle () );
        }
    }
}
