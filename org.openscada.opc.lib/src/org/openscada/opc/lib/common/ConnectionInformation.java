/*
 * This file is part of the OpenSCADA project
 * Copyright (C) 2006-2010 TH4 SYSTEMS GmbH (http://th4-systems.com)
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openscada.opc.lib.common;

/**
 * Holds the connection information
 * @author Jens Reimann
 *
 * If both <code>clsId</code> and <code>progId</code> are set then <code>clsId</code>
 * has priority!
 */
public class ConnectionInformation
{
    private String _host = "localhost";

    private String _domain = "localhost";

    private String _user = "";

    private String _password = "";

    private String _clsid = null;

    private String _progId = null;

    public ConnectionInformation ()
    {
        super ();
    }

    public ConnectionInformation ( final String user, final String password )
    {
        super ();
        this._user = user;
        this._password = password;
    }

    public ConnectionInformation ( final ConnectionInformation arg0 )
    {
        super ();
        this._user = arg0._user;
        this._password = arg0._password;
        this._domain = arg0._domain;
        this._host = arg0._host;
        this._progId = arg0._progId;
        this._clsid = arg0._clsid;
    }

    public String getDomain ()
    {
        return this._domain;
    }

    /**
     * Set the domain of the user used for logging on
     * @param domain
     */
    public void setDomain ( final String domain )
    {
        this._domain = domain;
    }

    public String getHost ()
    {
        return this._host;
    }

    /**
     * Set the host on which the server is located
     * @param host The host to use, either an IP address oder hostname
     */
    public void setHost ( final String host )
    {
        this._host = host;
    }

    public String getPassword ()
    {
        return this._password;
    }

    public void setPassword ( final String password )
    {
        this._password = password;
    }

    public String getUser ()
    {
        return this._user;
    }

    public void setUser ( final String user )
    {
        this._user = user;
    }

    public String getClsid ()
    {
        return this._clsid;
    }

    public void setClsid ( final String clsid )
    {
        this._clsid = clsid;
    }

    public String getProgId ()
    {
        return this._progId;
    }

    public void setProgId ( final String progId )
    {
        this._progId = progId;
    }

    public String getClsOrProgId ()
    {
        if ( this._clsid != null )
        {
            return this._clsid;
        }
        else if ( this._progId != null )
        {
            return this._progId;
        }
        else
        {
            return null;
        }
    }
}
