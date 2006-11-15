package org.openscada.opc.common.impl;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.win32.ComFactory;
import org.openscada.opc.common.EventHandler;

public class EventHandlerImpl implements EventHandler
{
    private String _identifier = null;
    private IJIComObject _object = null;

    public String getIdentifier ()
    {
        return _identifier;
    }

    public synchronized IJIComObject getObject ()
    {
        return _object;
    }
    
    public synchronized void setInfo ( IJIComObject object, String identifier )
    {
        _object = object;
        _identifier = identifier;
    }

    public synchronized void detach () throws JIException
    {
        if ( _object != null && _identifier != null )
        {
            ComFactory.detachEventHandler ( _object, _identifier );
        }
        _object = null;
        _identifier = null;
    }
    
}
