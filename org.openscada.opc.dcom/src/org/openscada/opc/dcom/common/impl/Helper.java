/*
 * This file is part of the openSCADA project
 * 
 * Copyright (C) 2006-2010 TH4 SYSTEMS GmbH (http://th4-systems.com)
 * Copyright (C) 2013 IBH SYSTEMS GmbH (http://ibh-systems.com)
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openscada.opc.dcom.common.impl;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.core.JICallBuilder;
import org.jinterop.dcom.core.JIFlags;
import org.jinterop.dcom.core.JIVariant;

public class Helper
{
    /**
     * Make the COM call but do not treat S_FALSE as error condition for the
     * whole call
     * 
     * @param object
     *            the object to make to call on
     * @param callObject
     *            the call object
     * @return the result of the call
     * @throws JIException
     */
    public static Object[] callRespectSFALSE ( final IJIComObject object, final JICallBuilder callObject ) throws JIException
    {
        return callIgnoreSpecificError ( object, callObject, org.openscada.opc.dcom.common.Constants.S_FALSE );
    }

    /**
     * Make the COM call but do not treat specific errors as error condition for
     * the whole call
     * 
     * @param object
     *            the object to make to call on
     * @param callObject
     *            the call object
     * @param ignoreErrorCode
     *            the errors to ignore
     * @return the result of the call
     * @throws JIException
     * @since 1.2.0
     */
    public static Object[] callIgnoreSpecificError ( final IJIComObject object, final JICallBuilder callObject, final int... ignoreErrorCode ) throws JIException
    {
        try
        {
            return object.call ( callObject );
        }
        catch ( final JIException e )
        {
            final int rc = e.getErrorCode ();
            for ( final int ec : ignoreErrorCode )
            {
                if ( ec == rc )
                {
                    // error code to ignore ... return result
                    return callObject.getResultsInCaseOfException ();
                }
            }

            // real error ... throw
            throw e;
        }
    }

    /**
     * Perform some fixes on the variant when writing it to OPC items. This
     * method
     * only changes control information on the variant and not the value itself!
     * 
     * @param value
     *            the value to fix
     * @return the fixed value
     * @throws JIException
     *             In case something goes wrong
     */
    public static JIVariant fixVariant ( final JIVariant value ) throws JIException
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
