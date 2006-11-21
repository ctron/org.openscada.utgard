package org.openscada.opc.lib.da.browser;

public class Leaf
{
    private Branch _parent = null; 
    private String _name = "";
    private String _itemId = null;
    
    public Leaf ( Branch parent, String name )
    {
        _parent = parent;
        _name = name;
    }
    
    public Leaf ( Branch parent, String name, String itemId )
    {
        _parent = parent;
        _name = name;
        _itemId = itemId;
    }

    public String getItemId ()
    {
        return _itemId;
    }

    public void setItemId ( String itemId )
    {
        _itemId = itemId;
    }

    public String getName ()
    {
        return _name;
    }

    public void setName ( String name )
    {
        _name = name;
    }

    public Branch getParent ()
    {
        return _parent;
    }
    
    
}
