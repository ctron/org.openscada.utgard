package org.openscada.opc.lib.da;

public class UnknownGroupException extends Exception
{
    private String _name = null;
    
    public UnknownGroupException ( String name )
    {
        super ();
        _name = name;
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1771564928794033075L;

    public String getName ()
    {
        return _name;
    }

    public void setName ( String name )
    {
        _name = name;
    }

}
