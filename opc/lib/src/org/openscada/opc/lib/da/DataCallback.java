package org.openscada.opc.lib.da;

public interface DataCallback
{
    void changed ( Item item, ItemState itemState );
}
