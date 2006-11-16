package org.openscada.opc.da.test;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.common.JISystem;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.core.JIClsid;
import org.jinterop.dcom.core.JIComServer;
import org.jinterop.dcom.core.JISession;
import org.jinterop.dcom.core.JIVariant;
import org.openscada.opc.common.EventHandler;
import org.openscada.opc.common.KeyedResult;
import org.openscada.opc.common.KeyedResultSet;
import org.openscada.opc.common.Result;
import org.openscada.opc.common.ResultSet;
import org.openscada.opc.common.impl.OPCCommon;
import org.openscada.opc.da.IORequest;
import org.openscada.opc.da.OPCBROWSEDIRECTION;
import org.openscada.opc.da.OPCBROWSETYPE;
import org.openscada.opc.da.OPCGroupState;
import org.openscada.opc.da.OPCITEMDEF;
import org.openscada.opc.da.OPCITEMRESULT;
import org.openscada.opc.da.OPCITEMSOURCE;
import org.openscada.opc.da.OPCITEMSTATE;
import org.openscada.opc.da.OPCNAMESPACETYPE;
import org.openscada.opc.da.PropertyDescription;
import org.openscada.opc.da.impl.OPCAsyncIO2;
import org.openscada.opc.da.impl.OPCBrowseServerAddressSpace;
import org.openscada.opc.da.impl.OPCGroupStateMgt;
import org.openscada.opc.da.impl.OPCItemIO;
import org.openscada.opc.da.impl.OPCItemMgt;
import org.openscada.opc.da.impl.OPCItemProperties;
import org.openscada.opc.da.impl.OPCServer;
import org.openscada.opc.da.impl.OPCSyncIO;
import org.openscada.opc.da.impl.WriteRequest;

public class Test1
{
    private static JISession _session = null;

    public static void showError ( OPCCommon common, int errorCode ) throws JIException
    {
        System.out
                .println ( String.format ( "Error (%X): '%s'", errorCode, common.getErrorString ( errorCode, 1033 ) ) );
    }

    public static void showAccessPaths ( OPCBrowseServerAddressSpace browser, String id ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        for ( String i : browser.browseAccessPaths ( id ).asCollection () )
        {
            System.out.println ( "AccessPath Entry: " + i );
        }
    }

    public static void browseTree ( OPCBrowseServerAddressSpace browser ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        System.out.println ( "Showing hierarchial address space" );
        System.out.println ( String.format ( "Organization: %s", browser.queryOrganization () ) );

        if ( !browser.queryOrganization ().equals ( OPCNAMESPACETYPE.OPC_NS_HIERARCHIAL ) )
            return;

        browser.changePosition ( null, OPCBROWSEDIRECTION.OPC_BROWSE_TO );
        browseTree ( browser, 0 );
    }

    protected static void browseTree ( OPCBrowseServerAddressSpace browser, int level ) throws JIException, IllegalArgumentException, UnknownHostException
    {
        StringBuilder indent = new StringBuilder ( level );
        for ( int i = 0; i < level; i++ )
        {
            indent.append ( '\t' );
        }
        for ( String item : browser.browse ( OPCBROWSETYPE.OPC_LEAF, "", 0, JIVariant.VT_EMPTY ).asCollection () )
        {
            System.out.println ( indent + "Leaf: " + item );
            System.out.println ( indent + "\tName: " + browser.getItemID ( item ) );
        }

        for ( String item : browser.browse ( OPCBROWSETYPE.OPC_BRANCH, "", 0, JIVariant.VT_EMPTY ).asCollection () )
        {
            System.out.println ( indent + "Branch: " + item );
            browser.changePosition ( item, OPCBROWSEDIRECTION.OPC_BROWSE_DOWN );
            browseTree ( browser, level + 1 );
            browser.changePosition ( null, OPCBROWSEDIRECTION.OPC_BROWSE_UP );
        }
    }

    public static void browseFlat ( OPCBrowseServerAddressSpace browser ) throws JIException, IllegalArgumentException, UnknownHostException
    {
        System.out.println ( String.format ( "Organization: %s", browser.queryOrganization () ) );
        browser.changePosition ( null, OPCBROWSEDIRECTION.OPC_BROWSE_TO );

        System.out.println ( "Showing flat address space" );
        for ( String id : browser.browse ( OPCBROWSETYPE.OPC_FLAT, "", 0, JIVariant.VT_EMPTY ).asCollection () )
        {
            System.out.println ( "Item: " + id );
            //showAccessPaths ( browser, id );
        }
    }

    public static void dumpGroupState ( OPCGroupStateMgt group ) throws JIException
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
            System.out.println ( String.format ( "ID: %d, Value: %s, Error Code: %08x", entry.getKey (), entry
                    .getValue ().toString (), entry.getErrorCode () ) );
        }
    }

    public static void dumpItemPropertiesLookup ( OPCItemProperties itemProperties, String itemID, int... ids ) throws JIException
    {
        KeyedResultSet<Integer, String> values = itemProperties.lookupItemIDs ( itemID, ids );
        for ( KeyedResult<Integer, String> entry : values )
        {
            System.out.println ( String.format ( "ID: %d, Item ID: %s, Error Code: %08x", entry.getKey (), entry
                    .getValue (), entry.getErrorCode () ) );
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

    public static void queryItems ( OPCItemIO itemIO, String... items ) throws JIException
    {
        List<IORequest> requests = new LinkedList<IORequest> ();
        for ( String item : items )
        {
            requests.add ( new IORequest ( item, 0 ) );
        }
        itemIO.read ( requests.toArray ( new IORequest[0] ) );
    }

    public static boolean dumpOPCITEMRESULT ( KeyedResultSet<OPCITEMDEF, OPCITEMRESULT> result )
    {
        int failed = 0;
        for ( KeyedResult<OPCITEMDEF, OPCITEMRESULT> resultEntry : result )
        {
            System.out.println ( "==================================" );
            System.out.println ( String.format ( "Item: '%s' ", resultEntry.getKey ().getItemID () ) );

            System.out.println ( String.format ( "Error Code: %08x", resultEntry.getErrorCode () ) );
            if ( !resultEntry.isFailed () )
            {
                System.out
                        .println ( String.format ( "Server Handle: %08X", resultEntry.getValue ().getServerHandle () ) );
                System.out
                        .println ( String.format ( "Data Type: %d", resultEntry.getValue ().getCanonicalDataType () ) );
                System.out.println ( String.format ( "Access Rights: %d", resultEntry.getValue ().getAccessRights () ) );
                System.out.println ( String.format ( "Reserved: %d", resultEntry.getValue ().getReserved () ) );
            }
            else
                failed++;
        }
        return failed == 0;
    }

    public static void writeItems ( OPCServer server, OPCGroupStateMgt group, WriteTest... writeTests ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        System.out.println ( "Write items" );

        OPCItemMgt itemManagement = group.getItemManagement ();
        OPCITEMDEF[] defs = new OPCITEMDEF[writeTests.length];
        for ( int i = 0; i < writeTests.length; i++ )
        {
            OPCITEMDEF def = new OPCITEMDEF ();
            def.setActive ( true );
            def.setItemID ( writeTests[i].getItemID () );
            //def.setRequestedDataType ( (short)writeTests[i].getValue ().getType () );
            defs[i] = def;

            System.out.println ( String.format ( "%s <= (%d) %s", writeTests[i].getItemID (), writeTests[i].getValue ()
                    .getType (), writeTests[i].getValue ().toString () ) );
        }

        System.out.println ( "Add to group" );
        KeyedResultSet<OPCITEMDEF, OPCITEMRESULT> result = itemManagement.add ( defs );
        WriteRequest[] writeRequests = new WriteRequest[writeTests.length];
        Integer[] serverHandles = new Integer[writeTests.length];
        for ( int i = 0; i < writeTests.length; i++ )
        {
            if ( result.get ( i ).getErrorCode () != 0 )
                throw new JIException ( result.get ( i ).getErrorCode () );

            writeRequests[i] = new WriteRequest ( result.get ( i ).getValue ().getServerHandle (), writeTests[i]
                    .getValue () );
            serverHandles[i] = writeRequests[i].getServerHandle ();

            System.out.println ( String.format ( "Item: %s, VT: %d", writeTests[i].getItemID (), result.get ( i )
                    .getValue ().getCanonicalDataType () ) );
        }

        System.out.println ( "Perform write" );
        OPCSyncIO syncIO = group.getSyncIO ();
        ResultSet<WriteRequest> writeResults = syncIO.write ( writeRequests );
        for ( int i = 0; i < writeTests.length; i++ )
        {
            Result<WriteRequest> writeResult = writeResults.get ( i );
            System.out.println ( String.format ( "ItemID: %s, ErrorCode: %08X", writeTests[i].getItemID (), writeResult
                    .getErrorCode () ) );
            if ( writeResult.getErrorCode () != 0 )
                showError ( server, writeResult.getErrorCode () );
        }

        // finally remove them again
        System.out.println ( "Remove from group" );
        itemManagement.remove ( serverHandles );

        System.out.println ( "Write items...complete" );
    }

    public static void testItems ( OPCServer server, OPCGroupStateMgt group, String... itemIDs ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        OPCItemMgt itemManagement = group.getItemManagement ();
        List<OPCITEMDEF> items = new ArrayList<OPCITEMDEF> ( itemIDs.length );
        for ( String id : itemIDs )
        {
            OPCITEMDEF item = new OPCITEMDEF ();
            item.setItemID ( id );
            item.setClientHandle ( new Random ().nextInt () );
            items.add ( item );
        }

        OPCITEMDEF[] itemArray = items.toArray ( new OPCITEMDEF[0] );

        System.out.println ( "Validate" );
        KeyedResultSet<OPCITEMDEF, OPCITEMRESULT> result = itemManagement.validate ( itemArray );
        if ( !dumpOPCITEMRESULT ( result ) )
            return;

        // now add them to the group
        System.out.println ( "Add" );
        result = itemManagement.add ( itemArray );
        if ( !dumpOPCITEMRESULT ( result ) )
            return;

        // get the server handle array
        Integer[] serverHandles = new Integer[itemArray.length];
        for ( int i = 0; i < itemArray.length; i++ )
        {
            serverHandles[i] = new Integer ( result.get ( i ).getValue ().getServerHandle () );
        }

        // set them active
        System.out.println ( "Activate" );
        ResultSet<Integer> resultSet = itemManagement.setActiveState ( true, serverHandles );
        for ( Result<Integer> resultEntry : resultSet )
        {
            System.out.println ( String.format ( "Item: %08X, Error: %08X", resultEntry.getValue (), resultEntry
                    .getErrorCode () ) );
        }

        // set client handles
        System.out.println ( "Set client handles" );
        Integer[] clientHandles = new Integer[serverHandles.length];
        for ( int i = 0; i < serverHandles.length; i++ )
        {
            clientHandles[i] = i;
        }
        itemManagement.setClientHandles ( serverHandles, clientHandles );

        OPCAsyncIO2 asyncIO2 = group.getAsyncIO2 ();
        // connect handler

        EventHandler eventHandler = group.attach ( new DumpDataCallback () );
        asyncIO2.setEnable ( true );
        asyncIO2.refresh ( (short)1, 1 );

        // sleep
        try
        {
            System.out.println ( "Waiting..." );
            Thread.sleep ( 10 * 1000 );
        }
        catch ( InterruptedException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace ();
        }

        eventHandler.detach ();

        // sync IO - read
        OPCSyncIO syncIO = group.getSyncIO ();
        KeyedResultSet<Integer, OPCITEMSTATE> itemState = syncIO.read ( OPCITEMSOURCE.OPC_DS_DEVICE, serverHandles );
        for ( KeyedResult<Integer, OPCITEMSTATE> itemStateEntry : itemState )
        {
            int errorCode = itemStateEntry.getErrorCode ();
            System.out.println ( String.format (
                    "Server ID: %08X, Value: %s, Timestamp: %d/%d (%Tc), Quality: %d, Error: %08X", itemStateEntry
                            .getKey (), itemStateEntry.getValue ().getValue (), itemStateEntry.getValue ()
                            .getTimestamp ().getHigh (), itemStateEntry.getValue ().getTimestamp ().getLow (),
                    itemStateEntry.getValue ().getTimestamp ().asCalendar (), itemStateEntry.getValue ().getQuality (),
                    errorCode ) );
            if ( errorCode != 0 )
            {
                showError ( server, errorCode );
            }
        }

        // set them inactive
        System.out.println ( "In-Active" );
        itemManagement.setActiveState ( false, serverHandles );

        // finally remove them again
        System.out.println ( "Remove" );
        itemManagement.remove ( serverHandles );
    }

    public static void main ( String[] args ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        TestConfiguration configuration = new MatrikonSimulationServerConfiguration ();

        OPCServer server = null;
        try
        {
            JISystem.setAutoRegisteration ( true );
            JISystem.setLogLevel ( Level.ALL );

            _session = JISession.createSession ( args[1], args[2], args[3] );
            // OPCServer server = new OPCServer ( "127.0.0.1", JIProgId.valueOf
            // ( session, "Matrikon.OPC.Simulation.1" ),
            // session );
            JIComServer comServer = new JIComServer ( JIClsid.valueOf ( configuration.getCLSID () ), args[0], _session );

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

            OPCBrowseServerAddressSpace serverBrowser = server.getBrowser ();
            browseFlat ( serverBrowser );
            browseTree ( serverBrowser );

            OPCGroupStateMgt group = server.addGroup ( "test", true, 100, 1234, 60, 0.0f, 1033 );
            group.setName ( "test2" );
            OPCGroupStateMgt group2 = group.clone ( "test" );
            group = server.getGroupByName ( "test2" );
            group.setState ( null, false, null, null, null, null );
            group.setState ( null, true, null, null, null, null );
            dumpGroupState ( group );
            dumpGroupState ( group2 );

            //testItems ( server, group, configuration.getReadItems () );
            if ( configuration.getWriteItems () != null )
            {
                writeItems ( server, group, configuration.getWriteItems () );
            }

            OPCItemProperties itemProperties = server.getItemPropertiesService ();
            //dumpItemProperties ( itemProperties, "Saw-toothed Waves.Int" );

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
            JISession.destroySession ( _session );
            _session = null;
        }
    }
}
