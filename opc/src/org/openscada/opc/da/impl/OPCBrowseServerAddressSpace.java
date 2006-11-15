package org.openscada.opc.da.impl;

import java.net.UnknownHostException;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.core.JICallObject;
import org.jinterop.dcom.core.JIFlags;
import org.jinterop.dcom.core.JIInterfacePointer;
import org.jinterop.dcom.win32.ComFactory;
import org.openscada.opc.common.impl.EnumString;
import org.openscada.opc.da.Constants;
import org.openscada.opc.da.OPCBROWSEDIRECTION;
import org.openscada.opc.da.OPCBROWSETYPE;
import org.openscada.opc.da.OPCNAMESPACETYPE;

public class OPCBrowseServerAddressSpace
{
    private IJIComObject _opcBrowseServerAddressSpaceObject = null;

    public OPCBrowseServerAddressSpace ( IJIComObject opcServer ) throws IllegalArgumentException, UnknownHostException, JIException
    {
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
    
    public void changePosition ( String position, OPCBROWSEDIRECTION direction ) throws JIException
    {
        JICallObject callObject = new JICallObject ( _opcBrowseServerAddressSpaceObject.getIpid (), true );
        callObject.setOpnum ( 1 );
        
        callObject.addInParamAsShort ( (short)direction.id (), JIFlags.FLAG_NULL );
        callObject.addInParamAsString ( position, JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR );
        
        _opcBrowseServerAddressSpaceObject.call ( callObject );
        
    }
    
    // FIXME: here
    public void browse ( OPCBROWSETYPE browseType, String filterCriteria, int accessRights ) throws JIException
    {
        JICallObject callObject = new JICallObject ( _opcBrowseServerAddressSpaceObject.getIpid (), true );
        callObject.setOpnum ( 2 );
        
        callObject.addInParamAsShort ( (short)browseType.id (), JIFlags.FLAG_NULL );
        callObject.addInParamAsString ( filterCriteria, JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR );
        callObject.addInParamAsShort ( (short)0, JIFlags.FLAG_NULL ); // todo
        callObject.addInParamAsInt ( accessRights, JIFlags.FLAG_NULL );
        
        Object result [] = _opcBrowseServerAddressSpaceObject.call ( callObject );
    }
    
    public EnumString browseAccessPaths ( String itemID ) throws JIException, IllegalArgumentException, UnknownHostException
    {
        JICallObject callObject = new JICallObject ( _opcBrowseServerAddressSpaceObject.getIpid (), true );
        callObject.setOpnum ( 4 );
        
        callObject.addInParamAsString ( itemID, JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR );
        callObject.addOutParamAsType ( JIInterfacePointer.class, JIFlags.FLAG_NULL );
        
        Object [] result = _opcBrowseServerAddressSpaceObject.call ( callObject );
        
        return new EnumString ( ComFactory.createCOMInstance ( _opcBrowseServerAddressSpaceObject, (JIInterfacePointer)result[0] ) );
    }
}
