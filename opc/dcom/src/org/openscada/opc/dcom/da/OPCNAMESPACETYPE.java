package org.openscada.opc.dcom.da;

public enum OPCNAMESPACETYPE
{
    OPC_NS_HIERARCHIAL ( 1 ), OPC_NS_FLAT ( 2 ), OPC_NS_UNKNOWN ( 0 );

    private int _id;

    private OPCNAMESPACETYPE ( int id )
    {
        _id = id;
    }

    public int id ()
    {
        return _id;
    }

    public static OPCNAMESPACETYPE fromID ( int id )
    {
        switch ( id )
        {
        case 1:
            return OPC_NS_HIERARCHIAL;
        case 2:
            return OPC_NS_FLAT;
        default:
            return OPC_NS_UNKNOWN;
        }
    }
}
