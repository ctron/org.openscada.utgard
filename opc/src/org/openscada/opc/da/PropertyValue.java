package org.openscada.opc.da;

import org.jinterop.dcom.core.JIVariant;

public class PropertyValue
{
    private int _id = 0;
    private JIVariant _value = null;
    private int _errorCode = 0;
    
    public int getErrorCode ()
    {
        return _errorCode;
    }
    public void setErrorCode ( int errorCode )
    {
        _errorCode = errorCode;
    }
    public JIVariant getValue ()
    {
        return _value;
    }
    public void setValue ( JIVariant value )
    {
        _value = value;
    }
    public int getId ()
    {
        return _id;
    }
    public void setId ( int id )
    {
        _id = id;
    }
}
