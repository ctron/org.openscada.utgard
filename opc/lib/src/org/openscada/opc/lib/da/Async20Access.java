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

package org.openscada.opc.lib.da;

import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.jinterop.dcom.common.JIException;
import org.openscada.opc.dcom.common.EventHandler;
import org.openscada.opc.dcom.common.KeyedResult;
import org.openscada.opc.dcom.common.KeyedResultSet;
import org.openscada.opc.dcom.common.ResultSet;
import org.openscada.opc.dcom.da.IOPCDataCallback;
import org.openscada.opc.dcom.da.OPCDATASOURCE;
import org.openscada.opc.dcom.da.ValueData;
import org.openscada.opc.dcom.da.impl.OPCAsyncIO2;
import org.openscada.opc.lib.common.NotConnectedException;

public class Async20Access extends AccessBase implements IOPCDataCallback
{
    @SuppressWarnings ( "unused" )
    private static Logger _log = Logger.getLogger ( Async20Access.class );

    private EventHandler _eventHandler = null;

    private boolean _initialRefresh = false;

    public Async20Access ( Server server, int period, boolean initialRefresh ) throws IllegalArgumentException, UnknownHostException, NotConnectedException, JIException, DuplicateGroupException
    {
        super ( server, period );
        _initialRefresh = initialRefresh;
    }

    @Override
    protected synchronized void start () throws JIException, IllegalArgumentException, UnknownHostException, NotConnectedException, DuplicateGroupException
    {
        if ( isActive () )
        {
            return;
        }

        super.start ();

        _eventHandler = _group.attach ( this );
        if ( ( !_items.isEmpty () ) && _initialRefresh )
        {
            OPCAsyncIO2 async20 = _group.getAsyncIO20 ();
            if ( async20 == null )
            {
                throw new NotConnectedException ();
            }
            
            _group.getAsyncIO20 ().refresh ( OPCDATASOURCE.OPC_DS_CACHE, 0 );
        }
    }

    @Override
    protected synchronized void stop () throws JIException
    {
        if ( !isActive () )
            return;

        super.stop ();

        _eventHandler.detach ();
    }

    public void cancelComplete ( int transactionId, int serverGroupHandle )
    {
    }

    public void dataChange ( int transactionId, int serverGroupHandle, int masterQuality, int masterErrorCode, KeyedResultSet<Integer, ValueData> result )
    {
        _log.debug ( String.format ( "readComplete - transId %d", transactionId ) );
        for ( KeyedResult<Integer, ValueData> entry : result )
        {
            Item item = _group.findItemByClientHandle ( entry.getKey () );
            updateItem ( item, new ItemState ( entry.getErrorCode (), entry.getValue ().getValue (),
                    entry.getValue ().getTimestamp (), entry.getValue ().getQuality () ) );
        }
    }

    public void readComplete ( int transactionId, int serverGroupHandle, int masterQuality, int masterErrorCode, KeyedResultSet<Integer, ValueData> result )
    {
        _log.debug ( String.format ( "readComplete - transId %d", transactionId ) );
    }

    public void writeComplete ( int transactionId, int serverGroupHandle, int masterErrorCode, ResultSet<Integer> result )
    {
    }
}
