package org.openscada.opc.lib.common;

public class ConnectionInformation
{
    private String _host = "localhost";

    private String _domain = "localhost";

    private String _user = "";

    private String _password = "";
    
    private String _clsid = "";

    public ConnectionInformation ()
    {
        super ();
    }

    public ConnectionInformation ( String user, String password )
    {
        super ();
        _user = user;
        _password = password;
    }

    public String getDomain ()
    {
        return _domain;
    }

    /**
     * Set the domain of the user used for logging on
     * @param domain
     */
    public void setDomain ( String domain )
    {
        _domain = domain;
    }

    public String getHost ()
    {
        return _host;
    }

    /**
     * Set the host on which the server is located
     * @param host The host to use, either an IP address oder hostname
     */
    public void setHost ( String host )
    {
        _host = host;
    }

    public String getPassword ()
    {
        return _password;
    }

    public void setPassword ( String password )
    {
        _password = password;
    }

    public String getUser ()
    {
        return _user;
    }

    public void setUser ( String user )
    {
        _user = user;
    }

    public String getClsid ()
    {
        return _clsid;
    }

    public void setClsid ( String clsid )
    {
        _clsid = clsid;
    }
}
