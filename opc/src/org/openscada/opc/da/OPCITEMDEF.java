package org.openscada.opc.da;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIFlags;
import org.jinterop.dcom.core.JIPointer;
import org.jinterop.dcom.core.JIString;
import org.jinterop.dcom.core.JIStruct;
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

    /**
     * Convert to structure to a J-Interop structure
     * @return the j-interop structe
     * @throws JIException
     */
    public JIStruct toStruct () throws JIException
    {
        JIStruct struct = new JIStruct ();
        struct.addMember ( new JIString ( getAccessPath (), JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR ) );
        struct.addMember ( new JIString ( getItemID (), JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR ) );
        struct.addMember ( new Integer ( isActive () ? 1 : 0 ) );
        struct.addMember ( Integer.valueOf ( getClientHandle () ) );

        struct.addMember ( Integer.valueOf ( 0 ) ); // blob size
        struct.addMember ( new JIPointer ( null ) ); // blob

        struct.addMember ( Short.valueOf ( getRequestedDataType () ) );
        struct.addMember ( Short.valueOf ( getReserved () ) );
        return struct;
    }
}
