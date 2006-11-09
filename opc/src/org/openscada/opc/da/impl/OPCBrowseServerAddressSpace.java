package org.openscada.opc.da.impl;

import java.net.UnknownHostException;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.core.JICallObject;
import org.jinterop.dcom.core.JIFlags;
import org.openscada.opc.common.impl.OPCCommon;
import org.openscada.opc.da.Constants;
import org.openscada.opc.da.OPCNAMESPACETYPE;

public class OPCBrowseServerAddressSpace extends OPCCommon
{
    private IJIComObject _opcBrowseServerAddressSpaceObject = null;

    public OPCBrowseServerAddressSpace ( IJIComObject opcServer ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        super ( opcServer );
        _opcBrowseServerAddressSpaceObject = (IJIComObject)opcServer.queryInterface ( Constants.IOPCBrowseServerAddressSpace_IID );
    }
    
    public OPCNAMESPACETYPE queryOrganization () throws JIException
    {
        JICallObject callObject = new JICallObject ( _opcBrowseServerAddressSpaceObject.getIpid (), true );
        callObject.setOpnum ( 0 );
        
        callObject.addOutParamAsType ( Short.class, JIFlags.FLAG_NULL );
        
        Object result[] = _opcBrowseServerAddressSpaceObject.call ( callObject );
        
        return OPCNAMESPACETYPE.fromID ( (Short)result[0] );
    }
}
