package org.openscada.opc.common.impl;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.core.JICallObject;
import org.openscada.opc.da.Constants;

public class Helper
{
    /**
     * Make the COM call but do not treat S_FALSE as error condition for the whole call
     * @param object the object to make to call on
     * @param callObject the call object
     * @return the result of the call
     * @throws JIException
     */
    public static Object[] callRespectSFALSE ( IJIComObject object, JICallObject callObject ) throws JIException
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
}
