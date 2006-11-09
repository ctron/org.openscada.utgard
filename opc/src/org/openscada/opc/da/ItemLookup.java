package org.openscada.opc.da;

public class ItemLookup
{
    private int _id = 0;
    private String _itemId = "";
    private int _errorCode = 0;
    
    
    public int getErrorCode ()
    {
        return _errorCode;
    }
    public void setErrorCode ( int errorCode )
    {
        _errorCode = errorCode;
    }
    public int getId ()
    {
        return _id;
    }
    public void setId ( int id )
    {
        _id = id;
    }
    public String getItemId ()
    {
        return _itemId;
    }
    public void setItemId ( String itemId )
    {
        _itemId = itemId;
    }
    
    
}
