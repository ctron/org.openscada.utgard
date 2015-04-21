package org.openscada.opc.xmlda.tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;
import org.openscada.opc.xmlda.Connection;
import org.openscada.opc.xmlda.browse.Browser;
import org.openscada.opc.xmlda.browse.BrowserListener;
import org.openscada.opc.xmlda.browse.BrowserState;
import org.openscada.opc.xmlda.requests.BrowseEntry;
import org.openscada.opc.xmlda.requests.ItemProperty;

public class Test4
{
    private final class PropertyComparator implements Comparator<ItemProperty>
    {
        @Override
        public int compare ( final ItemProperty o1, final ItemProperty o2 )
        {
            return o1.getNameAsString ().compareTo ( o2.getNameAsString () );
        }
    }

    @Test
    public void test1 () throws Exception
    {
        try ( Connection c = new Connection ( "http://advosol.com/XMLDADemo/ts_sim/OpcDaGateway.asmx", "OpcXmlDaSrv" ) )
        // try ( Connection c = new Connection ( "http://advosol.com/XMLDADemo/XML_Sim/opcxmldaserver.asmx", "OpcXmlDA" ) )
        {

            final Browser browser = c.createRootBrowser ( new BrowserListener () {

                @Override
                public void stateChange ( final BrowserState state, final Throwable error )
                {
                    System.out.println ( "State change: " + state );
                    if ( error != null )
                    {
                        error.printStackTrace ( System.out );
                    }
                }

                @Override
                public void dataChange ( final List<BrowseEntry> entries )
                {
                    System.out.println ( "Data change" );
                    for ( final BrowseEntry entry : entries )
                    {
                        System.out.print ( "\t" + entry.getName () + " " );
                        if ( entry.isItem () )
                        {
                            System.out.print ( "!" );
                        }
                        if ( entry.isParent () )
                        {
                            System.out.print ( ">" );
                        }
                        System.out.println ();
                        final List<ItemProperty> props = new ArrayList<> ( entry.getProperties ().values () );
                        Collections.sort ( props, new PropertyComparator () );

                        for ( final ItemProperty prop : props )
                        {
                            System.out.println ( "\t\t" + prop );
                        }
                    }
                }
            }, 5_000, 5, false );

            browser.dispose ( 10_000 );
        }
    }
}
