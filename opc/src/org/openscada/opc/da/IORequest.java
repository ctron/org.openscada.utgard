/**
 * 
 */
package org.openscada.opc.da;

public class IORequest
{
    private String _itemID;
    private int _maxAge;
    
    public IORequest ( String itemID, int maxAge )
    {
        _itemID = itemID;
        _maxAge = maxAge;
    }

    public String getItemID ()
    {
        return _itemID;
    }

    public void setItemID ( String itemID )
    {
        _itemID = itemID;
    }

    public int getMaxAge ()
    {
        return _maxAge;
    }

    public void setMaxAge ( int maxAge )
    {
        _maxAge = maxAge;
    }
    
    
}