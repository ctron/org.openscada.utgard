package org.openscada.opc.lib.da.browser;

public enum Access
{
    READ(1),
    WRITE(2);
    
    private int _code = 0;
    
    private Access ( int code )
    {
        _code = code;
    }
    
    public int getCode ()
    {
        return _code;
    }
}
