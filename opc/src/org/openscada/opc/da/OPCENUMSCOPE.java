package org.openscada.opc.da;

public enum OPCENUMSCOPE
{
    OPC_ENUM_PRIVATE_CONNECTIONS ( 1 ),
    OPC_ENUM_PUBLIC_CONNECTIONS ( 2 ),
    OPC_ENUM_ALL_CONNECTIONS ( 3 ),
    OPC_ENUM_PRIVATE ( 4 ),
    OPC_ENUM_PUBLIC ( 5 ),
    OPC_ENUM_ALL ( 6 ),
    OPC_ENUM_UNKNOWN ( 0 );

    private int _id;

    private OPCENUMSCOPE ( int id )
    {
        _id = id;
    }

    public int id ()
    {
        return _id;
    }

    public static OPCENUMSCOPE fromID ( int id )
    {
        switch ( id )
        {
        case 1:
            return OPC_ENUM_PRIVATE_CONNECTIONS;
        case 2:
            return OPC_ENUM_PUBLIC_CONNECTIONS;
        case 3:
            return OPC_ENUM_ALL_CONNECTIONS;
        case 4:
            return OPC_ENUM_PRIVATE;
        case 5:
            return OPC_ENUM_PUBLIC;
        case 6:
            return OPC_ENUM_ALL;
        default:
            return OPC_ENUM_UNKNOWN;
        }
    }
}
