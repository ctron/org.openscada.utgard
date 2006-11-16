package org.openscada.opc.dcom.da;

public class PropertyDescription
{
    private int _id = -1;

    private String _description = "";

    private short _varType = 0;

    public String getDescription ()
    {
        return _description;
    }

    public void setDescription ( String description )
    {
        _description = description;
    }

    public int getId ()
    {
        return _id;
    }

    public void setId ( int id )
    {
        _id = id;
    }

    public short getVarType ()
    {
        return _varType;
    }

    public void setVarType ( short varType )
    {
        _varType = varType;
    }
}
