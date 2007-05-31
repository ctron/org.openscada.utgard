/*
 * This file is part of the OpenSCADA project
 * Copyright (C) 2006-2007 inavare GmbH (http://inavare.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package org.openscada.opc.lib.da.browser;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class Branch
{
    private Branch _parent = null;

    private String _name = null;

    private Collection<Branch> _branches = new LinkedList<Branch> ();

    private Collection<Leaf> _leaves = new LinkedList<Leaf> ();

    /**
     * Create a branch to the virtual root folder
     *
     */
    public Branch ()
    {
        super ();
    }
    
    /**
     * Create a branch with a parent branch and a name of this branch.
     * @param parent The parent of this branch
     * @param name The name of this branch
     */
    public Branch ( Branch parent, String name )
    {
        super ();
        _name = name;
        _parent = parent;
    }

    /**
     * Get all branches.
     * <br/>
     * They must be filled first with a fill method from the {@link TreeBrowser} 
     * @return The list of branches
     */
    public Collection<Branch> getBranches ()
    {
        return _branches;
    }

    public void setBranches ( Collection<Branch> branches )
    {
        _branches = branches;
    }

    /**
     * Get all leaves.
     * <br/>
     * They must be filled first with a fill method from the {@link TreeBrowser} 
     * @return The list of leaves
     */
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

    public Branch getParent ()
    {
        return _parent;
    }
    
    /**
     * Get the list of names from the parent up to this branch
     * @return The stack of branch names from the parent up this one
     */
    public Collection<String> getBranchStack ()
    {
        LinkedList<String> branches = new LinkedList<String> ();
        
        Branch currentBranch = this;
        while ( currentBranch.getParent () != null )
        {
            branches.add ( currentBranch.getName () );
            currentBranch = currentBranch.getParent ();
        }
        
        Collections.reverse ( branches );
        return branches;
    }

}
