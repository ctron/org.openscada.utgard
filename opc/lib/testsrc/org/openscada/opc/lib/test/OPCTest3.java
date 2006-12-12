/*
 * This file is part of the OpenSCADA project
 * Copyright (C) 2006 inavare GmbH (http://inavare.com)
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

import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.da.Server;
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
        for ( int i = 0; i < level; i++ )
        {
            sb.append ( "  " );
        }
        String indent = sb.toString ();

        for ( Leaf leaf : branch.getLeaves () )
        {
            System.out.println ( indent + "Leaf: " + leaf.getName () + " [" + leaf.getItemId () + "]" );
        }
        for ( Branch subBranch : branch.getBranches () )
        {
            System.out.println ( indent + "Branch: " + subBranch.getName () );
            dumpTree ( subBranch, level + 1 );
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
            System.out.println ( String.format ( "%08X: %s", e.getErrorCode (),
                    server.getErrorMessage ( e.getErrorCode () ) ) );
        }
    }
}
