package org.openscada.opc.lib.da;

import java.util.Calendar;

import org.jinterop.dcom.core.JIVariant;

public class ItemState
{
    private JIVariant _value = null;

    private Calendar _timestamp = null;

    private Short _quality = null;

    public ItemState ( JIVariant value, Calendar timestamp, Short quality )
    {
        super ();
        _value = value;
        _timestamp = timestamp;
        _quality = quality;
    }

    public ItemState ()
    {
        super ();
    }

    public Short getQuality ()
    {
        return _quality;
    }

    public void setQuality ( Short quality )
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
