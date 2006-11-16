package org.openscada.opc.da;

public enum OPCSERVERSTATE
{
    OPC_STATUS_RUNNING ( 1 ),
    OPC_STATUS_FAILED ( 2 ),
    OPC_STATUS_NOCONFIG ( 3 ),
    OPC_STATUS_SUSPENDED ( 4 ),
    OPC_STATUS_TEST ( 5 ),
    OPC_STATUS_COMM_FAULT ( 6 ),
    OPC_STATUS_UNKNOWN ( 0 );

    private int _id;

    private OPCSERVERSTATE ( int id )
    {
        _id = id;
    }

    public int id ()
    {
        return _id;
    }

    public static OPCSERVERSTATE fromID ( int id )
    {
        switch ( id )
        {
        case 1:
            return OPC_STATUS_RUNNING;
        case 2:
            return OPC_STATUS_FAILED;
        case 3:
            return OPC_STATUS_NOCONFIG;
        case 4:
            return OPC_STATUS_SUSPENDED;
        case 5:
            return OPC_STATUS_TEST;
        case 6:
            return OPC_STATUS_COMM_FAULT;
        default:
            return OPC_STATUS_UNKNOWN;
        }
    }
}
