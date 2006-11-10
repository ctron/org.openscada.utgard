package org.openscada.opc.da;

import org.jinterop.dcom.core.JIVariant;

public class OPCITEMDEF
{
    private String _accessPath = "";

    private String _itemID = "";

    private boolean _active = true;

    private int _clientHandle = 0;

    private short _requestedDataType = JIVariant.VT_EMPTY;

    private short _reserved = 0;

    public String getAccessPath ()
    {
        return _accessPath;
    }

    public void setAccessPath ( String accessPath )
    {
        _accessPath = accessPath;
    }

    public int getClientHandle ()
    {
        return _clientHandle;
    }

    public void setClientHandle ( int clientHandle )
    {
        _clientHandle = clientHandle;
    }

    public boolean isActive ()
    {
        return _active;
    }

    public void setActive ( boolean ctive )
    {
        _active = ctive;
    }

    public String getItemID ()
    {
        return _itemID;
    }

    public void setItemID ( String itemID )
    {
        _itemID = itemID;
    }

    public short getRequestedDataType ()
    {
        return _requestedDataType;
    }

    public void setRequestedDataType ( short requestedDataType )
    {
        _requestedDataType = requestedDataType;
    }

    public short getReserved ()
    {
        return _reserved;
    }

    public void setReserved ( short reserved )
    {
        _reserved = reserved;
    }
}
