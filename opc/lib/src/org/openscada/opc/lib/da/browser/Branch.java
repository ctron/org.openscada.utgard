package org.openscada.opc.lib.da.browser;

import java.util.Collection;
import java.util.LinkedList;

public class Branch
{
    private Branch _parent = null;
    private String _name = null;
    private Collection<Branch> _branches = new LinkedList<Branch> ();
    private Collection<Leaf> _leaves = new LinkedList<Leaf> ();
    
    public Branch ( Branch parent )
    {
        super ();
        _parent = parent;
    }
    
    public Branch ( Branch parent, String name )
    {
        super ();
        _name = name;
        _parent = parent;
    }

    public Collection<Branch> getBranches ()
    {
        return _branches;
    }

    public void setBranches ( Collection<Branch> branches )
    {
        _branches = branches;
    }

    public Collection<Leaf> getLeaves ()
    {
        return _leaves;
    }

    public void setLeaves ( Collection<Leaf> leaves )
    {
        _leaves = leaves;
    }

    public String getName ()
    {
        return _name;
    }

    public void setName ( String name )
    {
        _name = name;
    }
    
    
}
