package org.openscada.opc.da.test;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.common.JISystem;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.core.JIClsid;
import org.jinterop.dcom.core.JIComServer;
import org.jinterop.dcom.core.JISession;
import org.jinterop.dcom.core.JIVariant;
import org.openscada.opc.common.KeyedResult;
import org.openscada.opc.common.KeyedResultSet;
import org.openscada.opc.da.IORequest;
import org.openscada.opc.da.OPCBROWSEDIRECTION;
import org.openscada.opc.da.OPCGroupState;
import org.openscada.opc.da.OPCITEMDEF;
import org.openscada.opc.da.OPCITEMRESULT;
import org.openscada.opc.da.PropertyDescription;
import org.openscada.opc.da.impl.OPCBrowseServerAddressSpace;
import org.openscada.opc.da.impl.OPCGroup;
import org.openscada.opc.da.impl.OPCItemIO;
import org.openscada.opc.da.impl.OPCItemMgt;
import org.openscada.opc.da.impl.OPCItemProperties;
import org.openscada.opc.da.impl.OPCServer;

public class Test1
{
    public static void showError ( OPCServer server, int errorCode ) throws JIException
    {
        System.out.println ( String.format ( "Error (%X): '%s'", errorCode, server.getErrorString ( errorCode, 1033 ) ) );
    }
    
    public static void browse ( OPCBrowseServerAddressSpace browser ) throws JIException
    {
        System.out.println ( String.format ( "Organization: %s", browser.queryOrganization () ) );
        browser.changePosition ( null, OPCBROWSEDIRECTION.OPC_BROWSE_TO );
    }
    
    public static void dumpGroupState ( OPCGroup group ) throws JIException
    {
        OPCGroupState state = group.getState ();
        
        System.out.println ( "Name: " + state.getName () );
        System.out.println ( "Active: " + state.isActive () );
        System.out.println ( "Update Rate: " + state.getUpdateRate () );
        System.out.println ( "Time Bias: " + state.getTimeBias () );
        System.out.println ( "Percent Deadband: " + state.getPercentDeadband () );
        System.out.println ( "Locale ID: " + state.getLocaleID () );
        System.out.println ( "Client Handle: " + state.getClientHandle () );
        System.out.println ( "Server Handle: " + state.getServerHandle () );
    }
    
    public static void dumpItemProperties2 ( OPCItemProperties itemProperties, String itemID, int... ids ) throws JIException
    {
        KeyedResultSet<Integer, JIVariant> values = itemProperties.getItemProperties ( itemID, ids );
        for ( KeyedResult<Integer, JIVariant> entry : values )
        {
            System.out.println ( String.format ( "ID: %d, Value: %s, Error Code: %08x", entry.getKey (), entry.getValue ().toString (), entry.getErrorCode () ) );
        }
    }
    
    public static void dumpItemPropertiesLookup ( OPCItemProperties itemProperties, String itemID, int... ids ) throws JIException
    {
        KeyedResultSet<Integer, String> values = itemProperties.lookupItemIDs ( itemID, ids );
        for ( KeyedResult<Integer, String> entry : values )
        {
            System.out.println ( String.format ( "ID: %d, Item ID: %s, Error Code: %08x", entry.getKey (), entry.getValue (), entry.getErrorCode () ) );
        }
    }
    
    public static void dumpItemProperties ( OPCItemProperties itemProperties, String itemID ) throws JIException
    {
        Collection<PropertyDescription> properties = itemProperties.queryAvailableProperties ( itemID );
        int[] ids = new int[properties.size ()];
        
        System.out.println ( String.format ( "Item Properties for '%s' (count:%d)", itemID, properties.size () ) );
        int i = 0;
        for ( PropertyDescription pd : properties )
        {
            ids[i] = pd.getId ();
            System.out.println ( "ID: " + pd.getId () );
            System.out.println ( "Description: " + pd.getDescription () );
            System.out.println ( "Variable Type: " + pd.getVarType () );
            i++;
        }

        System.out.println ( "Lookup" );
        dumpItemPropertiesLookup ( itemProperties, itemID, ids );

        System.out.println ( "Query" );
        dumpItemProperties2 ( itemProperties, itemID, ids );
    }
    
    public static void queryItems ( OPCItemIO itemIO, String ...items) throws JIException
    {
        List<IORequest> requests = new LinkedList<IORequest> ();
        for ( String item : items )
        {
            requests.add ( new IORequest ( item, 0 ) );
        }
        itemIO.read ( requests.toArray ( new IORequest[0] ) );
    }
    
    public static boolean dumpOPCITEMRESULT ( KeyedResultSet<OPCITEMDEF,OPCITEMRESULT> result )
    {
        int failed = 0;
        for ( KeyedResult<OPCITEMDEF,OPCITEMRESULT> resultEntry : result )
        {
            System.out.println ( "==================================" );
            System.out.println ( String.format ( "Item: '%s' ", resultEntry.getKey ().getItemID () ) );
            
            System.out.println ( String.format ( "Error Code: %08x", resultEntry.getErrorCode () ) );
            if ( !resultEntry.isFailed () )
            {
                System.out.println ( String.format ( "Server Handle: %d", resultEntry.getValue ().getServerHandle ()  ) );
                System.out.println ( String.format ( "Data Type: %d", resultEntry.getValue ().getCanonicalDataType ()  ) );
                System.out.println ( String.format ( "Access Rights: %d", resultEntry.getValue ().getAccessRights ()  ) );
            }
            else
                failed++;
        }
        return failed == 0;
    }
    
    public static void testItems ( OPCGroup group, String ...itemIDs ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        OPCItemMgt itemManagement = group.getItemManagement ();
        List<OPCITEMDEF> items = new ArrayList<OPCITEMDEF> ( itemIDs.length );
        for ( String id : itemIDs )
        {
            OPCITEMDEF item = new OPCITEMDEF ();
            item.setItemID ( id );
            items.add ( item );    
        }
        
        OPCITEMDEF[] itemArray = items.toArray ( new OPCITEMDEF[0] );
        
        System.out.println ( "Validate" );
        KeyedResultSet<OPCITEMDEF,OPCITEMRESULT> result = itemManagement.validate ( itemArray );
        if ( !dumpOPCITEMRESULT ( result ) )
            return;
        
        // now add them to the group
        System.out.println ( "Add" );
        result = itemManagement.add ( itemArray );
        if ( !dumpOPCITEMRESULT ( result ) )
            return;
        
        // get the server handle array
        Integer[] serverHandles = new Integer[itemArray.length ];
        for ( int i = 0; i < itemArray.length; i++ )
        {
            serverHandles[i] = result.get ( i ).getValue ().getServerHandle ();
        }
        
        System.out.println ( "Active/Inactive" );
        // set them active
        itemManagement.setActiveState ( true, serverHandles );
        // set them inactive
        itemManagement.setActiveState ( false, serverHandles );
        
        // finally remove them again
        System.out.println ( "Remove" );
        itemManagement.remove ( serverHandles );
    }
    
    public static void main ( String[] args ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        JISession session = null;
        OPCServer server = null;
        try
        {
            JISystem.setAutoRegisteration ( true );

            session = JISession.createSession ( args[1], args[2], args[3] );
            // OPCServer server = new OPCServer ( "127.0.0.1", JIProgId.valueOf
            // ( session, "Matrikon.OPC.Simulation.1" ),
            // session );
            JIComServer comServer = new JIComServer ( JIClsid.valueOf ( "F8582CF2-88FB-11D0-B850-00C0F0104305" ),
                    args[0], session );
            IJIComObject serverObject = comServer.createInstance ();
            server = new OPCServer ( serverObject );
            
            // server.GetStatus ();
            server.setLocaleID ( 1033 );
            System.out.println ( String.format ( "LCID: %d", server.getLocaleID () ) );
            server.setClientName ( "test" );
            for ( Integer i : server.queryAvailableLocaleIDs () )
            {
                System.out.println ( String.format ( "Available LCID: %d", i ) );
            }
            OPCBrowseServerAddressSpace serverBrowser = new OPCBrowseServerAddressSpace (serverObject);
            //browse ( serverBrowser );

           OPCGroup group = server.addGroup ( "test", true, 1000, 1234, 0, 0.0f, 1033 );
           group.setName ( "test2" );
           OPCGroup group2 = group.clone ( "test" );
           group = server.getGroupByName ( "test2" );
           dumpGroupState ( group );
           dumpGroupState ( group2 );
           
           testItems ( group, "Saw-toothed Waves.Int" , "Saw-toothed Waves.Int2" );
           
           OPCItemProperties itemProperties = server.getItemPropertiesService ();
           dumpItemProperties ( itemProperties, "Saw-toothed Waves.Int" );
           
           OPCItemIO itemIO = server.getItemIOService ();
           //queryItems ( itemIO, "Saw-toothed Waves.Int" );
           
           // clean up
           server.removeGroup ( group, true );
           server.removeGroup ( group2, true );
           // server.getStatus ();
           
           //showError ( server, 0x80004005 );
        }
        catch ( JIException e )
        {
            e.printStackTrace ();
            showError ( server, e.getErrorCode () );
        }
        finally
        {
            JISession.destroySession ( session );
        }
    }
}
