package org.openscada.opc.xmlda.tests;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import org.junit.Test;
import org.openscada.opc.xmlda.Connection;
import org.openscada.opc.xmlda.Poller;
import org.openscada.opc.xmlda.SubscriptionListener;
import org.openscada.opc.xmlda.SubscriptionState;
import org.openscada.opc.xmlda.requests.ItemValue;

public class Test3
{
    private static final SubscriptionListener DEFAULT_LISTENER = new ConsoleDumpListener ();

    private static class ConsoleDumpListener implements SubscriptionListener
    {
        @Override
        public void stateChange ( final SubscriptionState state )
        {
            System.out.println ( "State change: " + state );
        }

        @Override
        public void dataChange ( final Map<String, ItemValue> values )
        {
            final StringWriter sw = new StringWriter ();
            final PrintWriter pw = new PrintWriter ( sw );

            pw.println ( "Update" );
            pw.println ( "----------" );

            for ( final Map.Entry<String, ItemValue> entry : values.entrySet () )
            {
                pw.format ( "\t%s = %s%n", entry.getKey (), entry.getValue () );
            }

            System.out.print ( sw );
            System.out.flush ();
        }
    }

    @Test
    public void testAsync1 () throws Exception
    {
        System.out.println ( "   ASYNC" );
        System.out.println ( "========================" );
        performTest ( true );
        System.out.println ( "========================" );
    }

    @Test
    public void testSync1 () throws Exception
    {
        System.out.println ( "   SYNC" );
        System.out.println ( "========================" );
        performTest ( false );
        System.out.println ( "========================" );
    }

    private void performTest ( final boolean async ) throws Exception
    {
        try ( Connection c = new Connection ( "http://advosol.com/XMLDADemo/ts_sim/OpcDaGateway.asmx", "OpcXmlDaSrv" ) )
        // try ( Connection c = new Connection ( "http://advosol.com/XMLDADemo/XML_Sim/opcxmldaserver.asmx", "OpcXmlDA" ) )
        {
            final Poller poller;

            if ( async )
            {
                poller = c.createSubscriptionPoller ( DEFAULT_LISTENER );
            }
            else
            {
                poller = c.createReadPoller ( DEFAULT_LISTENER, 1_000, 0 );
            }

            try
            {
                Thread.sleep ( 1_000 );

                poller.setItems ( "Dynamic.Analog Types.Double", "Dynamic.Analog Types.Double1" );

                Thread.sleep ( 10_000 );

                poller.setItems ( "Dynamic.Analog Types.Double", "Dynamic.Analog Types.Double1", "SimulatedData.Ramp" );

                Thread.sleep ( 20_000 );

                poller.setItems ( "Dynamic.Analog Types.Double", "Dynamic.Analog Types.Double1" );

                Thread.sleep ( 30_000 );

                poller.setItems ();

                Thread.sleep ( 3_000 );
            }
            finally
            {
                poller.close ();
            }
        }
    }
}
