package org.openscada.opc.da;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIStruct;
import org.jinterop.dcom.core.JIVariant;
import org.openscada.opc.common.FILETIME;

public class OPCITEMSTATE
{
    private int _clientHandle = 0;

    private FILETIME _timestamp = null;

    private short _quality = 0;

    private short _reserved = 0;

    private JIVariant _value = null;

    public int getClientHandle ()
    {
        return _clientHandle;
    }

    public void setClientHandle ( int clientHandle )
    {
        _clientHandle = clientHandle;
    }

    public short getQuality ()
    {
        return _quality;
    }

    public void setQuality ( short quality )
    {
        _quality = quality;
    }

    public short getReserved ()
    {
        return _reserved;
    }

    public void setReserved ( short reserved )
    {
        _reserved = reserved;
    }

    public FILETIME getTimestamp ()
    {
        return _timestamp;
    }

    public void setTimestamp ( FILETIME timestamp )
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

    public static JIStruct getStruct () throws JIException
    {
        JIStruct struct = new JIStruct ();

        struct.addMember ( Integer.class );
        struct.addMember ( FILETIME.getStruct () );
        struct.addMember ( Short.class );
        struct.addMember ( Short.class );
        struct.addMember ( JIVariant.class );

        return struct;
    }

    public static OPCITEMSTATE fromStruct ( JIStruct struct )
    {
        OPCITEMSTATE itemState = new OPCITEMSTATE ();

        itemState.setClientHandle ( (Integer)struct.getMember ( 0 ) );
        itemState.setTimestamp ( FILETIME.fromStruct ( (JIStruct)struct.getMember ( 1 ) ) );
        itemState.setQuality ( (Short)struct.getMember ( 2 ) );
        itemState.setReserved ( (Short)struct.getMember ( 3 ) );
        itemState.setValue ( (JIVariant)struct.getMember ( 4 ) );

        return itemState;
    }
}
