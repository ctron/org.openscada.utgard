package org.openscada.opc.lib.test;

import java.util.Map;

import org.jinterop.dcom.common.JIException;
import org.openscada.opc.dcom.da.OPCITEMSTATE;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.da.Group;
import org.openscada.opc.lib.da.Item;
import org.openscada.opc.lib.da.ItemState;
import org.openscada.opc.lib.da.Server;
import org.openscada.opc.lib.da.browser.Branch;
import org.openscada.opc.lib.da.browser.Leaf;

public class OPCTest1
{
    public static void dumpItemState ( Item item, ItemState state )
    {
        System.out.println ( String.format ( "Item: %s, Value: %s, Timestamp: %tc, Quality: %d", item.getId (), state.getValue (), state.getTimestamp (), state.getQuality () ) );
    }
    
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

            // browse
            dumpTree ( server.getTreeBrowser ().browse (), 0 );
            
            // Add a new group
            Group group = server.addGroup ( "test" );
            // group is initially active ... just for demonstration
            group.setActive ( true );
            
            // We already have our group ... just for demonstration
            group = server.findGroup ( "test" );
            
            // Add a new item to the group
            Item item = group.addItem ( "Saw-toothed Waves.Int2" );
            // Items are initially active ... just for demonstration
            item.setActive ( true );
            
            // Add some more items ... including one that is already existing
            Map<String,Item> items = group.addItems ( "Saw-toothed Waves.Int2", "Saw-toothed Waves.Int4" );
            
            // sync-read some values
            for ( int i = 0; i < 10; i++ )
            {
                Thread.sleep ( 100 );
                dumpItemState ( item, item.read ( false ) );
            }
        }
        catch ( JIException e )
        {
            System.out.println ( String.format ( "%08X: %s", e.getErrorCode (), server.getErrorMessage ( e.getErrorCode () ) ) );
        }
    }
}
