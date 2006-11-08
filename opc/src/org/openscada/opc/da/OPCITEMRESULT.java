package org.openscada.opc.da;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIPointer;
import org.jinterop.dcom.core.JIStruct;

public class OPCITEMRESULT
{
    private int _serverHandle = 0;

    private short _canonicalDataType = 0;

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
}
