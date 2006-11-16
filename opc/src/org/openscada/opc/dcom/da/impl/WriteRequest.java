package org.openscada.opc.dcom.da.impl;

import org.jinterop.dcom.core.JIVariant;

/**
 * Data for a write request to the server
 * @author Jens Reimann <jens.reimann@inavare.net>
 *
 */
public class WriteRequest
{
    private int _serverHandle = 0;

    private JIVariant _value = JIVariant.EMPTY;

    public WriteRequest ()
    {
    }

    public WriteRequest ( WriteRequest arg0 )
    {
        _serverHandle = arg0._serverHandle;
        _value = arg0._value;
    }

    /**
     * Create a new write request with pre-fille data
     * @param serverHandle the server handle of the item to write to
     * @param value the value to write.
     */
    public WriteRequest ( int serverHandle, JIVariant value )
    {
        _serverHandle = serverHandle;
        _value = value;
    }

    public int getServerHandle ()
    {
        return _serverHandle;
    }

    public void setServerHandle ( int serverHandle )
    {
        _serverHandle = serverHandle;
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
