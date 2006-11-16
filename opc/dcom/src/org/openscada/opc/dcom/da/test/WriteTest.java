package org.openscada.opc.dcom.da.test;

import org.jinterop.dcom.core.JIVariant;

public class WriteTest
{
    private String _itemID = "";

    private JIVariant _value = JIVariant.EMPTY;

    public WriteTest ( String itemID, JIVariant value )
    {
        super ();
        _itemID = itemID;
        _value = value;
    }

    public String getItemID ()
    {
        return _itemID;
    }

    public void setItemID ( String itemID )
    {
        _itemID = itemID;
    }

    public JIVariant getValue ()
    {
        return _value;
    }

    public void setValue ( JIVariant value )
    {
        _value = value;
    }
}
