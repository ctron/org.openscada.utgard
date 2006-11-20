package org.openscada.opc.lib.test;

import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.da.DataCallback;
import org.openscada.opc.lib.da.Item;
import org.openscada.opc.lib.da.ItemState;
import org.openscada.opc.lib.da.Server;
import org.openscada.opc.lib.da.SyncAccess;

/**
 * Another test showing the "Access" interface with the SyncAccess implementation.
 * @author Jens Reimann <jens.reimann@inavare.net>
 *
 */
public class OPCTest2
{
    public static void dumpItemState ( Item item, ItemState state )
    {
        System.out.println ( String.format ( "Item: %s, Value: %s, Timestamp: %tc, Quality: %d", item.getId (), state.getValue (), state.getTimestamp (), state.getQuality () ) );
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
            
            // add sync access
            
            SyncAccess syncAccess = new SyncAccess ( server, 100 );
            syncAccess.addItem ( "Saw-toothed Waves.Int2", new DataCallback () {

                public void changed ( Item item, ItemState itemState )
                {
                    dumpItemState ( item, itemState );
                }} );
            
            // start reading
            syncAccess.start ();
            
            // wait a little bit
            Thread.sleep ( 10 * 1000 );
            
            // stop reading
            syncAccess.stop ();
        }
        catch ( JIException e )
        {
            System.out.println ( String.format ( "%08X: %s", e.getErrorCode (), server.getErrorMessage ( e.getErrorCode () ) ) );
        }
    }
}
