package org.openscada.opc.da;

public class OPCGroupState
{
    private int _updateRate = 1000;

    private boolean _active = true;

    private String _name = "";

    private int _timeBias = 0;

    private float _percentDeadband = 0.0f;

    private int _localeID = 0;

    private int _clientHandle = 0;

    private int _serverHandle = 0;

    public boolean isActive ()
    {
        return _active;
    }

    public void setActive ( boolean active )
    {
        _active = active;
    }

    public int getClientHandle ()
    {
        return _clientHandle;
    }

    public void setClientHandle ( int clientHandle )
    {
        _clientHandle = clientHandle;
    }

    public int getLocaleID ()
    {
        return _localeID;
    }

    public void setLocaleID ( int localeID )
    {
        _localeID = localeID;
    }

    public String getName ()
    {
        return _name;
    }

    public void setName ( String name )
    {
        _name = name;
    }

    public float getPercentDeadband ()
    {
        return _percentDeadband;
    }

    public void setPercentDeadband ( float percentDeadband )
    {
        _percentDeadband = percentDeadband;
    }

    public int getServerHandle ()
    {
        return _serverHandle;
    }

    public void setServerHandle ( int serverHandle )
    {
        _serverHandle = serverHandle;
    }

    public int getTimeBias ()
    {
        return _timeBias;
    }

    public void setTimeBias ( int timeBias )
    {
        _timeBias = timeBias;
    }

    public int getUpdateRate ()
    {
        return _updateRate;
    }

    public void setUpdateRate ( int updateRate )
    {
        _updateRate = updateRate;
    }
}
