package org.openscada.opc.da;

import java.util.Calendar;

import org.jinterop.dcom.core.JIVariant;

public class ValueData
{
    private JIVariant _value;

    private short _quality;

    private Calendar _timestamp;

    public short getQuality ()
    {
        return _quality;
    }

    public void setQuality ( short quality )
    {
        _quality = quality;
    }

    public Calendar getTimestamp ()
    {
        return _timestamp;
    }

    public void setTimestamp ( Calendar timestamp )
    {
        _timestamp = timestamp;
    }

    public JIVariant getValue ()
    {
        return _value;
    }

    public void setValue ( JIVariant value )
    {
        _value = value;
    }
}
