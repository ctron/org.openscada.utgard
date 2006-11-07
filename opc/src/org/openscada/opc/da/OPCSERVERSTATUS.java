package org.openscada.opc.da;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIPointer;
import org.jinterop.dcom.core.JIStruct;
import org.openscada.opc.common.FILETIME;

public class OPCSERVERSTATUS
{
    private FILETIME _startTime;

    private FILETIME _currentTime;

    private FILETIME _lastUpdateTime;

    private OPCSERVERSTATE _serverState;

    private int _groupCount;

    private int _bandWidth;

    private short _majorVersion;

    private short _minorVersion;

    private short _buildNumber;

    private short _reserved;

    private String _vendorInfo;

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
        struct.addMember ( String.class );

        return struct;
    }
}
