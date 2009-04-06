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

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIFlags;
import org.jinterop.dcom.core.JIPointer;
import org.jinterop.dcom.core.JIString;
import org.jinterop.dcom.core.JIStruct;
import org.openscada.opc.dcom.common.FILETIME;

public class OPCSERVERSTATUS
{
    private FILETIME _startTime = null;

    private FILETIME _currentTime = null;

    private FILETIME _lastUpdateTime = null;

    private OPCSERVERSTATE _serverState = null;

    private int _groupCount = -1;

    private int _bandWidth = -1;

    private short _majorVersion = -1;

    private short _minorVersion = -1;

    private short _buildNumber = -1;

    private short _reserved = 0;

    private String _vendorInfo = null;

    public int getBandWidth ()
    {
        return _bandWidth;
    }

    public void setBandWidth ( int bandWidth )
    {
        _bandWidth = bandWidth;
    }

    public short getBuildNumber ()
    {
        return _buildNumber;
    }

    public void setBuildNumber ( short buildNumber )
    {
        _buildNumber = buildNumber;
    }

    public FILETIME getCurrentTime ()
    {
        return _currentTime;
    }

    public void setCurrentTime ( FILETIME currentTime )
    {
        _currentTime = currentTime;
    }

    public int getGroupCount ()
    {
        return _groupCount;
    }

    public void setGroupCount ( int groupCount )
    {
        _groupCount = groupCount;
    }

    public FILETIME getLastUpdateTime ()
    {
        return _lastUpdateTime;
    }

    public void setLastUpdateTime ( FILETIME lastUpdateTime )
    {
        _lastUpdateTime = lastUpdateTime;
    }

    public short getMajorVersion ()
    {
        return _majorVersion;
    }

    public void setMajorVersion ( short majorVersion )
    {
        _majorVersion = majorVersion;
    }

    public short getMinorVersion ()
    {
        return _minorVersion;
    }

    public void setMinorVersion ( short minorVersion )
    {
        _minorVersion = minorVersion;
    }

    public short getReserved ()
    {
        return _reserved;
    }

    public void setReserved ( short reserved )
    {
        _reserved = reserved;
    }

    public FILETIME getStartTime ()
    {
        return _startTime;
    }

    public void setStartTime ( FILETIME startTime )
    {
        _startTime = startTime;
    }

    public String getVendorInfo ()
    {
        return _vendorInfo;
    }

    public void setVendorInfo ( String vendorInfo )
    {
        _vendorInfo = vendorInfo;
    }

    public OPCSERVERSTATE getServerState ()
    {
        return _serverState;
    }

    public void setServerState ( OPCSERVERSTATE dwServerState )
    {
        this._serverState = dwServerState;
    }

    public static JIStruct getStruct () throws JIException
    {
        JIStruct struct = new JIStruct ();

        struct.addMember ( FILETIME.getStruct () );
        struct.addMember ( FILETIME.getStruct () );
        struct.addMember ( FILETIME.getStruct () );
        struct.addMember ( Short.class ); // enum: OPCSERVERSTATE
        struct.addMember ( Integer.class );
        struct.addMember ( Integer.class );
        struct.addMember ( Short.class );
        struct.addMember ( Short.class );
        struct.addMember ( Short.class );
        struct.addMember ( Short.class );
        struct.addMember ( new JIPointer ( new JIString ( JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR ) ) );

        return struct;
    }

    public static OPCSERVERSTATUS fromStruct ( JIStruct struct )
    {
        OPCSERVERSTATUS status = new OPCSERVERSTATUS ();

        status._startTime = FILETIME.fromStruct ( (JIStruct)struct.getMember ( 0 ) );
        status._currentTime = FILETIME.fromStruct ( (JIStruct)struct.getMember ( 1 ) );
        status._lastUpdateTime = FILETIME.fromStruct ( (JIStruct)struct.getMember ( 2 ) );

        status._serverState = OPCSERVERSTATE.fromID ( (Short)struct.getMember ( 3 ) );
        status._groupCount = (Integer)struct.getMember ( 4 );
        status._bandWidth = (Integer)struct.getMember ( 5 );
        status._majorVersion = (Short)struct.getMember ( 6 );
        status._minorVersion = (Short)struct.getMember ( 7 );
        status._buildNumber = (Short)struct.getMember ( 8 );
        status._reserved = (Short)struct.getMember ( 9 );
        status._vendorInfo = ( (JIString) ( (JIPointer)struct.getMember ( 10 ) ).getReferent () ).getString ();

        return status;
    }
}
