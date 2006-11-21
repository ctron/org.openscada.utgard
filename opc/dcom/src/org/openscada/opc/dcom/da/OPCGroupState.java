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

package org.openscada.opc.dcom.da;

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
