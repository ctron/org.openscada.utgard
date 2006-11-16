package org.openscada.opc.dcom.common;

import java.util.Calendar;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIStruct;

public class FILETIME
{
    private int _high = 0;

    private int _low = 0;

    public FILETIME ()
    {
    }

    public FILETIME ( FILETIME arg0 )
    {
        _high = arg0._high;
        _low = arg0._low;
    }

    public int getHigh ()
    {
        return _high;
    }

    public void setHigh ( int high )
    {
        _high = high;
    }

    public int getLow ()
    {
        return _low;
    }

    public void setLow ( int low )
    {
        _low = low;
    }

    @Override
    public int hashCode ()
    {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + _high;
        result = PRIME * result + _low;
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
        final FILETIME other = (FILETIME)obj;
        if ( _high != other._high )
            return false;
        if ( _low != other._low )
            return false;
        return true;
    }

    public static JIStruct getStruct () throws JIException
    {
        JIStruct struct = new JIStruct ();

        struct.addMember ( Integer.class );
        struct.addMember ( Integer.class );

        return struct;
    }

    public static FILETIME fromStruct ( JIStruct struct )
    {
        FILETIME ft = new FILETIME ();

        ft.setLow ( (Integer)struct.getMember ( 0 ) );
        ft.setHigh ( (Integer)struct.getMember ( 1 ) );

        return ft;
    }

    public Calendar asCalendar ()
    {
        Calendar c = Calendar.getInstance ();

        long i = _high;
        i = i << 32L;
        i = i | _low;

        i = i - 116444736000000000L;

        i = i / 10000L;

        c.setTimeInMillis ( i );

        return c;
    }
}
