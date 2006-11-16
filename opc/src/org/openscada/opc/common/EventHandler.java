package org.openscada.opc.common;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.IJIComObject;

public interface EventHandler
{
    public String getIdentifier ();

    public IJIComObject getObject ();

    public void detach () throws JIException;
}
