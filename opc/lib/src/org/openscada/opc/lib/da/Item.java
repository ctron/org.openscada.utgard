package org.openscada.opc.lib.da;

import org.apache.log4j.Logger;
import org.jinterop.dcom.common.JIException;
import org.openscada.opc.dcom.da.OPCITEMSTATE;
import org.openscada.opc.dcom.da.impl.OPCItemMgt;

public class Item
{
    private static Logger _log = Logger.getLogger ( Item.class );
    
    private Group _group = null;
    private int _serverHandle = 0;
    private String _id = null;
    
    public Item ( Group group, int serverHandle, String id )
    {
        super ();
        _log.debug ( String.format ( "Adding new item '%s' (0x%08X) for group %s", id, serverHandle, group.toString () ) );
        _group = group;
        _serverHandle = serverHandle;
        _id = id;
    }
    
    public Group getGroup ()
    {
        return _group;
    }
    
    public int getServerHandle ()
    {
        return _serverHandle;
    }

    public String getId ()
    {
        return _id;
    }
    
    public void setActive ( boolean state ) throws JIException
    {
        _group.setActive ( state, this );
    }
    
    public void write ()
    {
        
    }
    
    public ItemState read ( boolean device ) throws JIException
    {
        return _group.read ( device, this ).get ( _id );
    }
}
