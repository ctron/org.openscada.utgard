package org.openscada.opc.lib.da.browser;

import java.net.UnknownHostException;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIVariant;
import org.openscada.opc.dcom.da.OPCBROWSEDIRECTION;
import org.openscada.opc.dcom.da.OPCBROWSETYPE;
import org.openscada.opc.dcom.da.impl.OPCBrowseServerAddressSpace;

/**
 * Browse through the hierarchical server namespace
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
    
    public Branch browse () throws JIException, IllegalArgumentException, UnknownHostException
    {
        Branch branch = new Branch ( null );
        
        _browser.changePosition ( null, OPCBROWSEDIRECTION.OPC_BROWSE_TO );
        browse ( branch );
        
        return branch;
    }
    
    protected void browse ( Branch branch ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        for ( String item : _browser.browse ( OPCBROWSETYPE.OPC_LEAF, _filterCriteria, 0, JIVariant.VT_EMPTY ).asCollection () )
        {
            Leaf leaf = new Leaf ( branch, item, _browser.getItemID ( item ) );
            branch.getLeaves ().add ( leaf );
        }
        for ( String item : _browser.browse ( OPCBROWSETYPE.OPC_BRANCH, _filterCriteria, 0, JIVariant.VT_EMPTY ).asCollection () )
        {
            Branch subBranch = new Branch ( branch, item );
            _browser.changePosition ( item, OPCBROWSEDIRECTION.OPC_BROWSE_DOWN );
            browse ( subBranch );
            _browser.changePosition ( null, OPCBROWSEDIRECTION.OPC_BROWSE_UP );
            branch.getBranches ().add ( subBranch );
        }
    }
}
