/*
 * This file is part of the OpenSCADA project
 * Copyright (C) 2006-2010 inavare GmbH (http://inavare.com)
 *
 * OpenSCADA is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License version 3
 * only, as published by the Free Software Foundation.
 *
 * OpenSCADA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License version 3 for more details
 * (a copy is included in the LICENSE file that accompanied this code).
 *
 * You should have received a copy of the GNU Lesser General Public License
 * version 3 along with OpenSCADA. If not, see
 * <http://opensource.org/licenses/lgpl-3.0.html> for a copy of the LGPLv3 License.
 */

package org.openscada.opc.lib.da;

import java.net.UnknownHostException;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Async20Access extends AccessBase implements IOPCDataCallback
{
    private static Logger _log = LoggerFactory.getLogger ( Async20Access.class );

    private EventHandler _eventHandler = null;

    private boolean _initialRefresh = false;

    public Async20Access ( final Server server, final int period, final boolean initialRefresh ) throws IllegalArgumentException, UnknownHostException, NotConnectedException, JIException, DuplicateGroupException
    {
        super ( server, period );
        this._initialRefresh = initialRefresh;
    }

    public Async20Access ( final Server server, final int period, final boolean initialRefresh, final String logTag ) throws IllegalArgumentException, UnknownHostException, NotConnectedException, JIException, DuplicateGroupException
    {
        super ( server, period, logTag );
        this._initialRefresh = initialRefresh;
    }

    @Override
    protected synchronized void start () throws JIException, IllegalArgumentException, UnknownHostException, NotConnectedException, DuplicateGroupException
    {
        if ( isActive () )
        {
            return;
        }

        super.start ();

        this._eventHandler = this.group.attach ( this );
        if ( !this.items.isEmpty () && this._initialRefresh )
        {
            OPCAsyncIO2 async20 = this.group.getAsyncIO20 ();
            if ( async20 == null )
            {
                throw new NotConnectedException ();
            }

            this.group.getAsyncIO20 ().refresh ( OPCDATASOURCE.OPC_DS_CACHE, 0 );
        }
    }

    @Override
    protected synchronized void stop () throws JIException
    {
        if ( !isActive () )
        {
            return;
        }

        if ( this._eventHandler != null )
        {
            try
            {
                this._eventHandler.detach ();
            }
            catch ( Throwable e )
            {
                _log.warn ( "Failed to detach group", e );
            }

            this._eventHandler = null;
        }

        super.stop ();
    }

    public void cancelComplete ( final int transactionId, final int serverGroupHandle )
    {
    }

    public void dataChange ( final int transactionId, final int serverGroupHandle, final int masterQuality, final int masterErrorCode, final KeyedResultSet<Integer, ValueData> result )
    {
        _log.debug ( String.format ( "dataChange - transId %d, items: %d", transactionId, result.size () ) );

        Group group = this.group;
        if ( group == null )
        {
            return;
        }

        for ( KeyedResult<Integer, ValueData> entry : result )
        {
            Item item = group.findItemByClientHandle ( entry.getKey () );
            _log.debug ( String.format ( "Update for '%s'", item.getId () ) );
            updateItem ( item, new ItemState ( entry.getErrorCode (), entry.getValue ().getValue (), entry.getValue ().getTimestamp (), entry.getValue ().getQuality () ) );
        }
    }

    public void readComplete ( final int transactionId, final int serverGroupHandle, final int masterQuality, final int masterErrorCode, final KeyedResultSet<Integer, ValueData> result )
    {
        _log.debug ( String.format ( "readComplete - transId %d", transactionId ) );
    }

    public void writeComplete ( final int transactionId, final int serverGroupHandle, final int masterErrorCode, final ResultSet<Integer> result )
    {
    }
}
