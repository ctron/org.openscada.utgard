package org.openscada.opc.da;

public enum OPCITEMSOURCE
{
    OPC_DS_CACHE ( 1 ), OPC_DS_DEVICE ( 2 ), OPC_DS_UNKNOWN ( 0 );

    private int _id;

    private OPCITEMSOURCE ( int id )
    {
        _id = id;
    }
    
    public int id ()
    {
        return _id;
    }
    
    public static OPCITEMSOURCE fromID ( int id )
    {
        switch ( id )
        {
        case 1:
            return OPC_DS_CACHE;
        case 2:
            return OPC_DS_DEVICE;
        default:
            return OPC_DS_UNKNOWN;
        }
    }
}
