package org.openscada.opc.lib.da;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jinterop.dcom.common.JIException;
import org.openscada.opc.dcom.common.impl.OPCCommon;

public class ErrorMessageResolver
{
    private static Logger _log = Logger.getLogger ( ErrorMessageResolver.class );
    
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
