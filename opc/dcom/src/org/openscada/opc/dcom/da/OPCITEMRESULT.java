package org.openscada.opc.dcom.da;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIPointer;
import org.jinterop.dcom.core.JIStruct;
import org.jinterop.dcom.core.JIVariant;

public class OPCITEMRESULT
{
    private int _serverHandle = 0;

    private short _canonicalDataType = JIVariant.VT_EMPTY;

    private short _reserved = 0;

    private int _accessRights = 0;

    public int getAccessRights ()
    {
        return _accessRights;
    }

    public void setAccessRights ( int accessRights )
    {
        _accessRights = accessRights;
    }

    public short getCanonicalDataType ()
    {
        return _canonicalDataType;
    }

    public void setCanonicalDataType ( short canonicalDataType )
    {
        _canonicalDataType = canonicalDataType;
    }

    public short getReserved ()
    {
        return _reserved;
    }

    public void setReserved ( short reserved )
    {
        _reserved = reserved;
    }

    public int getServerHandle ()
    {
        return _serverHandle;
    }

    public void setServerHandle ( int serverHandle )
    {
        _serverHandle = serverHandle;
    }

    public static JIStruct getStruct () throws JIException
    {
        JIStruct struct = new JIStruct ();

        struct.addMember ( Integer.class );
        struct.addMember ( Short.class );
        struct.addMember ( Short.class );
        struct.addMember ( Integer.class );
        struct.addMember ( Integer.class );
        struct.addMember ( new JIPointer ( null ) );

        return struct;
    }

    public static OPCITEMRESULT fromStruct ( JIStruct struct )
    {
        OPCITEMRESULT result = new OPCITEMRESULT ();

        result.setServerHandle ( new Integer ( (Integer)struct.getMember ( 0 ) ) );
        result.setCanonicalDataType ( new Short ( (Short)struct.getMember ( 1 ) ) );
        result.setReserved ( new Short ( (Short)struct.getMember ( 2 ) ) );
        result.setAccessRights ( new Integer ( (Integer)struct.getMember ( 3 ) ) );

        return result;
    }
}
