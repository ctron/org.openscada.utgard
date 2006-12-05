package org.openscada.opc.lib.da;

import org.jinterop.dcom.core.JIVariant;

public class WriteRequest
{
    private Item _item = null;
    private JIVariant _value = null;
    
    public WriteRequest ( Item item, JIVariant value )
    {
        super ();
        _item = item;
        _value = value;
    }

    public Item getItem ()
    {
        return _item;
    }

    public JIVariant getValue ()
    {
        return _value;
    }
}
