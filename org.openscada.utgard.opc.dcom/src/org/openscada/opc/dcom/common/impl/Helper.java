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

package org.openscada.opc.dcom.common.impl;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.core.JICallBuilder;
import org.jinterop.dcom.core.JIFlags;
import org.jinterop.dcom.core.JIVariant;
import org.openscada.opc.dcom.da.Constants;

public class Helper
{
    /**
     * Make the COM call but do not treat S_FALSE as error condition for the whole call
     * @param object the object to make to call on
     * @param callObject the call object
     * @return the result of the call
     * @throws JIException
     */
    public static Object[] callRespectSFALSE ( IJIComObject object, JICallBuilder callObject ) throws JIException
    {
        try
        {
            return object.call ( callObject );
        }
        catch ( JIException e )
        {
            if ( e.getErrorCode () != Constants.S_FALSE )
                throw e;
            return callObject.getResultsInCaseOfException ();
        }
    }
    
    /**
     * Perform some fixes on the variant when writing it to OPC items. This method
     * only changes control information on the variant and not the value itself!
     * @param value the value to fix
     * @return the fixed value
     * @throws JIException In case something goes wrong
     */
    public static JIVariant fixVariant ( JIVariant value ) throws JIException
    {
        if ( value.isArray () )
        {
            if ( value.getObjectAsArray ().getArrayInstance () instanceof Boolean[] )
            {
                value.setFlag ( JIFlags.FLAG_REPRESENTATION_VARIANT_BOOL );
            }
        }
        return value;
    }
}
