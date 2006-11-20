package org.openscada.opc.lib.test;

import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.da.DataCallback;
import org.openscada.opc.lib.da.Item;
import org.openscada.opc.lib.da.ItemState;
import org.openscada.opc.lib.da.Server;
import org.openscada.opc.lib.da.SyncAccess;
import org.openscada.opc.lib.da.browser.Branch;
import org.openscada.opc.lib.da.browser.FlatBrowser;
import org.openscada.opc.lib.da.browser.Leaf;
import org.openscada.opc.lib.da.browser.TreeBrowser;

/**
 * Another test showing the browser interfaces
 * @author Jens Reimann <jens.reimann@inavare.net>
 *
 */
public class OPCTest3
{
    public static void dumpTree ( Branch branch, int level )
    {
        StringBuilder sb = new StringBuilder ();
        for ( int i = 0; i < level ; i++ )
        {
            sb.append ( "  " );   
        }
        String indent = sb.toString ();
        
        for ( Leaf leaf : branch.getLeaves () )
        {
            System.out.println ( indent + "Leaf: " + leaf.getName () + " [" +  leaf.getItemId () + "]" );
        }
        for ( Branch subBranch : branch.getBranches () )
        {
            System.out.println ( indent + "Branch: " + subBranch.getName () );
            dumpTree ( subBranch, level+1 );
        }
    }
    
    public static void main ( String[] args ) throws Throwable
    {
        // create connection information
        ConnectionInformation ci = new ConnectionInformation ();
        ci.setHost ( args[0] );
        ci.setDomain ( args[1] );
        ci.setUser ( args[2] );
        ci.setPassword ( args[3] );
        ci.setClsid ( args[4] );
        
        // create a new server
        Server server = new Server ( ci );
        try
        {
            // connect to server
            server.connect ();

            // browse flat
            FlatBrowser flatBrowser = server.getFlatBrowser ();
            if ( flatBrowser != null )
            {
                for ( String item : server.getFlatBrowser ().browse ( "" ) )
                {
                    System.out.println ( item );
                }
            }
            
            // browse tree
            TreeBrowser treeBrowser = server.getTreeBrowser ();
            if ( treeBrowser != null )
            {
                dumpTree ( server.getTreeBrowser ().browse (), 0 );
            }
            
            
            
        }
        catch ( JIException e )
        {
            System.out.println ( String.format ( "%08X: %s", e.getErrorCode (), server.getErrorMessage ( e.getErrorCode () ) ) );
        }
    }
}
