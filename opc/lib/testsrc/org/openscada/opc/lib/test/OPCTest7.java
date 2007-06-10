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

package org.openscada.opc.lib.test;

import java.util.logging.Level;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.common.JISystem;
import org.jinterop.dcom.core.JIArray;
import org.jinterop.dcom.core.JIFlags;
import org.jinterop.dcom.core.JIString;
import org.jinterop.dcom.core.JIVariant;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.da.Group;
import org.openscada.opc.lib.da.Item;
import org.openscada.opc.lib.da.ItemState;
import org.openscada.opc.lib.da.Server;

/**
 * A sample that reads an item and writes back the result. You will need a
 * read/write item for this to work.
 * @author Jens Reimann &lt;jens.reimann@inavare.net&gt;
 *
 */
public class OPCTest7
{
    @SuppressWarnings ( "unused" )
    public static void main ( String[] args ) throws Throwable
    {
        JISystem.setLogLevel ( Level.ALL );

        // create connection information
        ConnectionInformation ci = new ConnectionInformation ();
        ci.setHost ( args[0] );
        ci.setDomain ( args[1] );
        ci.setUser ( args[2] );
        ci.setPassword ( args[3] );
        ci.setClsid ( args[4] );

        String itemName = args[5];

        // create a new server
        Server server = new Server ( ci );
        try
        {
            // connect to server
            server.connect ();
            
            // Add a new group
            Group group = server.addGroup ( "test" );

            // Add a new item to the group
            Item item = group.addItem ( itemName );

            JIString[] sdata = new JIString[] { new JIString ( "ab", JIFlags.FLAG_REPRESENTATION_STRING_BSTR ),
                    new JIString ( "cd", JIFlags.FLAG_REPRESENTATION_STRING_BSTR ),
                    new JIString ( "ef", JIFlags.FLAG_REPRESENTATION_STRING_BSTR ) };
            Double[] ddata = new Double[] { 1.1, 2.2, 3.3 };
            Boolean[] bdata = new Boolean[] { true, false, true, false };
            Integer[] idata = new Integer[] { 1202, 1203, 1204 };
            Long[] ldata = new Long[] { 12020001L, 12030001L, 12040001L };
            Float[] fdata = new Float[] { 1.1f, 1.2f, 1.3f };
            Byte[] bydata = new Byte[] { 1, 2, 3 };
            Character[] cdata = new Character[] { 'A', 'B', 'C' };

            JIArray array = new JIArray ( ddata, true );
            JIVariant value = new JIVariant ( array );

            System.out.println ( "============= PHASE 1 ============= " );

            // dump the value
            VariantDumper.dumpValue ( value );

            System.out.println ( "============= PHASE 2 ============= " );

            // now write it to the item
            item.write ( value );
            Thread.sleep ( 2500 );

            System.out.println ( "============= PHASE 3 ============= " );

            // now read the value back and dump it
            ItemState itemState = item.read ( true );
            VariantDumper.dumpValue ( itemState.getValue () );

            System.out.println ( "============= PHASE 4 ============= " );

            // and write the value just read in
            item.write ( itemState.getValue () );

            System.out.println ( "============= COMPLETE ============= " );
        }
        catch ( JIException e )
        {
            System.out.println ( String.format ( "%08X: %s", e.getErrorCode (),
                    server.getErrorMessage ( e.getErrorCode () ) ) );
        }
    }
}
