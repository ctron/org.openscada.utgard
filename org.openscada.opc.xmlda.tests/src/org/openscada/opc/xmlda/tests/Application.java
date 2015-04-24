package org.openscada.opc.xmlda.tests;

public class Application
{
    public static void main ( final String[] args ) throws Exception
    {
        // try ( ConnectionTest c = new ConnectionTest ( "http://dentrassi.de", "OpcXmlDA" ) )
        try ( ConnectionTest c = new ConnectionTest ( "http://advosol.com/XMLDADemo/XML_Sim/opcxmldaserver.asmx", "OpcXmlDA" ) )
        {
            c.dumpState ();
            System.out.println ( c.read ( "Dynamic.Analog Types.Double" ) );
            // System.out.println ( c.read ( "Dynamic.Analog Types.Double1" ) );
            // c.subscribe ( "Dynamic.Analog Types.Double" );
        }
        System.err.println ( "======================" );
        try ( ConnectionTest c = new ConnectionTest ( "http://advosol.com/XMLDADemo/ts_sim/OpcDaGateway.asmx", "OpcXmlDaSrv" ) )
        {
            c.dumpState ();
            System.out.println ( c.read ( "Dynamic.Analog Types.Double" ) );
            // System.out.println ( c.read ( "Dynamic.Analog Types.Double1" ) );
            // c.subscribe ( "Dynamic.Analog Types.Double" );
        }
    }
}
