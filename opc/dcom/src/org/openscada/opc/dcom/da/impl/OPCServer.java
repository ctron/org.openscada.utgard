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

package org.openscada.opc.dcom.da.impl;

import java.net.UnknownHostException;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.core.JICallObject;
import org.jinterop.dcom.core.JIFlags;
import org.jinterop.dcom.core.JIInterfacePointer;
import org.jinterop.dcom.core.JIPointer;
import org.jinterop.dcom.core.JIStruct;
import org.jinterop.dcom.win32.JIComFactory;
import org.openscada.opc.dcom.common.impl.BaseCOMObject;
import org.openscada.opc.dcom.common.impl.EnumString;
import org.openscada.opc.dcom.common.impl.Helper;
import org.openscada.opc.dcom.common.impl.OPCCommon;
import org.openscada.opc.dcom.da.Constants;
import org.openscada.opc.dcom.da.OPCENUMSCOPE;
import org.openscada.opc.dcom.da.OPCSERVERSTATUS;

public class OPCServer extends BaseCOMObject
{
    public OPCServer ( IJIComObject opcServer ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        super ( (IJIComObject)opcServer.queryInterface ( Constants.IOPCServer_IID ) );
    }

    /**
     * Retrieve the current server status
     * @return the current server status
     * @throws JIException
     */
    public OPCSERVERSTATUS getStatus () throws JIException
    {
        JICallObject callObject = new JICallObject ( getCOMObject ().getIpid (), true );
        callObject.setOpnum ( 3 );

        callObject.addOutParamAsObject ( new JIPointer ( OPCSERVERSTATUS.getStruct () ), JIFlags.FLAG_NULL );

        Object[] result = getCOMObject ().call ( callObject );

        return OPCSERVERSTATUS.fromStruct ( (JIStruct) ( (JIPointer)result[0] ).getReferent () );
    }

    public OPCGroupStateMgt addGroup ( String name, boolean active, int updateRate, int clientHandle, Integer timeBias, Float percentDeadband, int localeID ) throws JIException, IllegalArgumentException, UnknownHostException
    {
        JICallObject callObject = new JICallObject ( getCOMObject ().getIpid (), true );
        callObject.setOpnum ( 0 );

        callObject.addInParamAsString ( name, JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR );
        callObject.addInParamAsInt ( active ? 1 : 0, JIFlags.FLAG_NULL );
        callObject.addInParamAsInt ( updateRate, JIFlags.FLAG_NULL );
        callObject.addInParamAsInt ( clientHandle, JIFlags.FLAG_NULL );
        callObject.addInParamAsPointer ( new JIPointer ( timeBias ), JIFlags.FLAG_NULL );
        callObject.addInParamAsPointer ( new JIPointer ( percentDeadband ), JIFlags.FLAG_NULL );
        callObject.addInParamAsInt ( localeID, JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        callObject.addInParamAsUUID ( Constants.IOPCGroupStateMgt_IID, JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( JIInterfacePointer.class, JIFlags.FLAG_NULL );

        Object[] result = getCOMObject ().call ( callObject );

        return new OPCGroupStateMgt ( JIComFactory.createCOMInstance ( getCOMObject (), (JIInterfacePointer)result[2] ) );
    }

    public void removeGroup ( int serverHandle, boolean force ) throws JIException
    {
        JICallObject callObject = new JICallObject ( getCOMObject ().getIpid (), true );
        callObject.setOpnum ( 4 );

        callObject.addInParamAsInt ( serverHandle, JIFlags.FLAG_NULL );
        callObject.addInParamAsInt ( force ? 1 : 0, JIFlags.FLAG_NULL );

        getCOMObject ().call ( callObject );
    }

    public void removeGroup ( OPCGroupStateMgt group, boolean force ) throws JIException
    {
        removeGroup ( group.getState ().getServerHandle (), force );
    }

    public OPCGroupStateMgt getGroupByName ( String name ) throws JIException, IllegalArgumentException, UnknownHostException
    {
        JICallObject callObject = new JICallObject ( getCOMObject ().getIpid (), true );
        callObject.setOpnum ( 2 );

        callObject.addInParamAsString ( name, JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR );
        callObject.addInParamAsUUID ( Constants.IOPCGroupStateMgt_IID, JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( JIInterfacePointer.class, JIFlags.FLAG_NULL );

        Object[] result = getCOMObject ().call ( callObject );

        return new OPCGroupStateMgt ( JIComFactory.createCOMInstance ( getCOMObject (), (JIInterfacePointer)result[0] ) );
    }

    /**
     * Get the groups
     * @param scope The scope to get
     * @return A string enumerator with the groups
     * @throws JIException
     * @throws IllegalArgumentException
     * @throws UnknownHostException
     */
    public EnumString getGroups ( OPCENUMSCOPE scope ) throws JIException, IllegalArgumentException, UnknownHostException
    {
        JICallObject callObject = new JICallObject ( getCOMObject ().getIpid (), true );
        callObject.setOpnum ( 5 );

        callObject.addInParamAsShort ( (short)scope.id (), JIFlags.FLAG_NULL );
        callObject.addInParamAsUUID ( Constants.IEnumString_IID, JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( JIInterfacePointer.class, JIFlags.FLAG_NULL );

        Object[] result = Helper.callRespectSFALSE ( getCOMObject (), callObject );

        return new EnumString ( JIComFactory.createCOMInstance ( getCOMObject (), (JIInterfacePointer)result[0] ) );
    }

    public OPCItemProperties getItemPropertiesService ()
    {
        try
        {
            return new OPCItemProperties ( getCOMObject () );
        }
        catch ( Exception e )
        {
            return null;
        }
    }

    public OPCItemIO getItemIOService ()
    {
        try
        {
            return new OPCItemIO ( getCOMObject () );
        }
        catch ( Exception e )
        {
            return null;
        }
    }

    /**
     * Get the browser object (<code>IOPCBrowseServerAddressSpace</code>) from the server instance
     * @return the browser object
     */
    public OPCBrowseServerAddressSpace getBrowser ()
    {
        try
        {
            return new OPCBrowseServerAddressSpace ( getCOMObject () );
        }
        catch ( Exception e )
        {
            return null;
        }
    }

    /**
     * Get the common interface if supported 
     * @return the common interface or <code>null</code> if it is not supported
     */
    public OPCCommon getCommon ()
    {
        try
        {
            return new OPCCommon ( getCOMObject () );
        }
        catch ( Exception e )
        {
            return null;
        }
    }
}
