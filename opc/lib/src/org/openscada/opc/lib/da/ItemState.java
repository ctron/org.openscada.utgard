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

    @Override
    public int hashCode ()
    {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ( ( _quality == null ) ? 0 : _quality.hashCode () );
        result = PRIME * result + ( ( _timestamp == null ) ? 0 : _timestamp.hashCode () );
        result = PRIME * result + ( ( _value == null ) ? 0 : _value.toString().hashCode () );
        return result;
    }

    @Override
    public boolean equals ( Object obj )
    {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass () != obj.getClass () )
            return false;
        final ItemState other = (ItemState)obj;
        if ( _quality == null )
        {
            if ( other._quality != null )
                return false;
        }
        else if ( !_quality.equals ( other._quality ) )
            return false;
        if ( _timestamp == null )
        {
            if ( other._timestamp != null )
                return false;
        }
        else if ( !_timestamp.equals ( other._timestamp ) )
            return false;
        if ( _value == null )
        {
            if ( other._value != null )
                return false;
        }
        else if ( !_value.toString ().equals ( other._value.toString () ) )
            return false;
        return true;
    }
}
