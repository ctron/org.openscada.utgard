package org.openscada.opc.lib.da;

import org.jinterop.dcom.common.JIException;
import org.openscada.opc.dcom.da.impl.OPCGroupStateMgt;

public class Group
{
    private OPCGroupStateMgt _group = null;
    
    public Group ( OPCGroupStateMgt group )
    {
        _group = group;
    }
    
    public void setActive ( boolean state ) throws JIException
    {
        _group.setState ( null, state, null, null, null, null );
    }
    
    public boolean isActive () throws JIException
    {
        return _group.getState ().isActive ();
    }

}
