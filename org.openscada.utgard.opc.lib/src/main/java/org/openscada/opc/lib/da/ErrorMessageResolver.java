/*
 * This file is part of the OpenSCADA project
 * Copyright (C) 2006-2009 inavare GmbH (http://inavare.com)
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

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jinterop.dcom.common.JIException;
import org.openscada.opc.dcom.common.impl.OPCCommon;

/**
 * An error message resolver that will lookup the error code using the
 * server interface and will cache the result locally.
 * @author Jens Reimann
 *
 */
public class ErrorMessageResolver
{
    private static Logger _log = LoggerFactory.getLogger ( ErrorMessageResolver.class );

    private OPCCommon _opcCommon = null;

    private Map<Integer, String> _messageCache = new HashMap<Integer, String> ();

    private int _localeId = 0;

    public ErrorMessageResolver ( OPCCommon opcCommon, int localeId )
    {
        super ();
        _opcCommon = opcCommon;
        _localeId = localeId;
    }

    /**
     * Get an error message from an error code
     * @param errorCode The error code to look up
     * @return the error message or <code>null</code> if no message could be looked up
     */
    public synchronized String getMessage ( int errorCode )
    {
        String message = _messageCache.get ( Integer.valueOf ( errorCode ) );

        if ( message == null )
        {
            try
            {
                message = _opcCommon.getErrorString ( errorCode, _localeId );
                _log.info ( String.format ( "Resolved %08X to '%s'", errorCode, message ) );
            }
            catch ( JIException e )
            {
                _log.warn ( String.format ( "Failed to resolve error code for %08X", errorCode ), e );
            }
            if ( message != null )
                _messageCache.put ( errorCode, message );
        }
        return message;
    }
}
