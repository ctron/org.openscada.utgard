package org.openscada.opc.common;

public class Result<T>
{
    private T _value = null;
    private int _errorCode = 0;
    
    public Result ()
    {
    }
    
    public Result ( T value, int errorCode )
    {
        _value = value;
        _errorCode = errorCode;
    }
    
    public int getErrorCode ()
    {
        return _errorCode;
    }
    public void setErrorCode ( int errorCode )
    {
        _errorCode = errorCode;
    }
    public T getValue ()
    {
        return _value;
    }
    public void setValue ( T value )
    {
        _value = value;
    }
    
    public boolean isFailed ()
    {
        return _errorCode != 0;
    }
}
