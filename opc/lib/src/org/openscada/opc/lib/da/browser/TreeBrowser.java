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

import java.net.UnknownHostException;
import java.util.Collection;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIVariant;
import org.openscada.opc.dcom.da.OPCBROWSEDIRECTION;
import org.openscada.opc.dcom.da.OPCBROWSETYPE;
import org.openscada.opc.dcom.da.impl.OPCBrowseServerAddressSpace;

/**
 * Browse through the hierarchical server namespace.
 * <br/>
 * The operations on the address space browser browser are not synchronized
 * as is the TreeBrowser itself. The user must take care of preventing
 * simultanious access to this instance and the server address space browser.
 * @author Jens Reimann <jens.reimann@inavare.net>
 *
 */
public class TreeBrowser
{
    private OPCBrowseServerAddressSpace _browser = null;

    private String _filterCriteria = "";

    public TreeBrowser ( OPCBrowseServerAddressSpace browser )
    {
        _browser = browser;
    }

    public TreeBrowser ( OPCBrowseServerAddressSpace browser, String filterCriteria )
    {
        _browser = browser;
        _filterCriteria = filterCriteria;
    }

    protected void moveToRoot () throws JIException
    {
        _browser.changePosition ( null, OPCBROWSEDIRECTION.OPC_BROWSE_TO );
    }
    
    protected void moveToBranch ( Branch branch ) throws JIException
    {
        Collection<String> branchStack = branch.getBranchStack ();
        
        moveToRoot ();
        for ( String branchName : branchStack )
        {
            _browser.changePosition ( branchName, OPCBROWSEDIRECTION.OPC_BROWSE_DOWN );
        }
    }
    
    public Branch browseBranches () throws JIException, IllegalArgumentException, UnknownHostException
    {
        Branch branch = new Branch ();
        fillBranches ( branch );
        return branch;        
    }
    
    public Branch browseLeaves () throws IllegalArgumentException, UnknownHostException, JIException
    {
        Branch branch = new Branch ();
        fillLeaves ( branch );
        return branch;
    }
    
    public Branch fillBranches ( Branch branch ) throws JIException, IllegalArgumentException, UnknownHostException
    {
        moveToBranch ( branch );
        browse ( branch, false, true, false );
        return branch;
    }
    
    public Branch fillLeaves ( Branch branch ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        moveToBranch ( branch );
        browse ( branch, true, false, false );
        return branch;
    }
    
    public Branch browse () throws JIException, IllegalArgumentException, UnknownHostException
    {
        Branch branch = new Branch ();
        fill ( branch );
        return branch;
    }
    
    public Branch fill ( Branch branch ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        moveToBranch ( branch );
        browse ( branch, true, true, true );
        return branch;
    }

    /**
     * Fill the branch object with the leaves of this currently selected branch.
     * <br/>
     * The server object is not located to the branch before browsing!
     * @param branch The branch to fill
     * @throws IllegalArgumentException
     * @throws UnknownHostException
     * @throws JIException
     */
    protected void browseLeaves ( Branch branch ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        branch.getLeaves ().clear ();
        for ( String item : _browser.browse ( OPCBROWSETYPE.OPC_LEAF, _filterCriteria, 0, JIVariant.VT_EMPTY ).asCollection () )
        {
            Leaf leaf = new Leaf ( branch, item, _browser.getItemID ( item ) );
            branch.getLeaves ().add ( leaf );
        }
    }
    
    protected void browseBranches ( Branch branch, boolean leaves, boolean descend ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        branch.getBranches ().clear ();
        
        for ( String item : _browser.browse ( OPCBROWSETYPE.OPC_BRANCH, _filterCriteria, 0, JIVariant.VT_EMPTY ).asCollection () )
        {
            Branch subBranch = new Branch ( branch, item );
            // descend only if we should
            if ( descend )
            {
                _browser.changePosition ( item, OPCBROWSEDIRECTION.OPC_BROWSE_DOWN );
                browse ( subBranch, leaves, true, true );
                _browser.changePosition ( null, OPCBROWSEDIRECTION.OPC_BROWSE_UP );
            }
            branch.getBranches ().add ( subBranch );
        }
    }
    
    protected void browse ( Branch branch, boolean leaves, boolean branches, boolean descend ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        // process leaves
        if ( leaves )
        {
            browseLeaves ( branch );
        }
        
        // process branches
        if ( branches )
        {
            browseBranches ( branch, leaves, descend );
        }
    }
}
