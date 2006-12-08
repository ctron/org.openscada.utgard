package org.openscada.opc.lib.da;

import org.openscada.opc.dcom.da.OPCSERVERSTATUS;

public interface ServerStateListener
{
    public void stateUpdate ( OPCSERVERSTATUS state );
}
