package org.openscada.opc.common.impl;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.IJIComObject;

public class BaseCOMObject
{
    private IJIComObject _comObject = null;

    /**
     * Create a new base COM object
     * @param comObject The COM object to wrap but be addRef'ed
     */
    public BaseCOMObject ( IJIComObject comObject )
    {
        super ();
        _comObject = comObject;
    }

    /**
     * Give up the wrapped COM object. Will release the COM object.
     * @throws JIException
     */
    public synchronized void dispose () throws JIException
    {
        // FIXME: need fix in upstream library
        /*
         if ( _comObject != null )
         _comObject.release ();
         */
        _comObject = null;
    }

    @Override
    protected void finalize () throws Throwable
    {
        try
        {
            dispose ();
        }
        catch ( Throwable e )
        {
        }

        super.finalize ();
    }

    protected synchronized IJIComObject getCOMObject ()
    {
        return _comObject;
    }
}
