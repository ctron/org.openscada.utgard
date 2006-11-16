package org.openscada.opc.da;

public enum OPCBROWSEDIRECTION
{
    OPC_BROWSE_UP ( 1 ), OPC_BROWSE_DOWN ( 2 ), OPC_BROWSE_TO ( 3 ), OPC_BROWSE_UNKNOWN ( 0 );

    private int _id;

    private OPCBROWSEDIRECTION ( int id )
    {
        _id = id;
    }

    public int id ()
    {
        return _id;
    }

    public static OPCBROWSEDIRECTION fromID ( int id )
    {
        switch ( id )
        {
        case 1:
            return OPC_BROWSE_UP;
        case 2:
            return OPC_BROWSE_DOWN;
        case 3:
            return OPC_BROWSE_TO;
        default:
            return OPC_BROWSE_UNKNOWN;
        }
    }
}
