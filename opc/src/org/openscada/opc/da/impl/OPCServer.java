package org.openscada.opc.da.impl;

import java.net.UnknownHostException;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.core.JICallObject;
import org.jinterop.dcom.core.JIFlags;
import org.jinterop.dcom.core.JIInterfacePointer;
import org.jinterop.dcom.core.JIPointer;
import org.jinterop.dcom.core.JIStruct;
import org.jinterop.dcom.win32.ComFactory;
import org.openscada.opc.common.impl.OPCCommon;
import org.openscada.opc.da.Constants;
import org.openscada.opc.da.OPCSERVERSTATUS;

public class OPCServer extends OPCCommon
{
    private IJIComObject _opcServerObject = null;

    public OPCServer ( IJIComObject opcServer ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        super ( opcServer );
        _opcServerObject = (IJIComObject)opcServer.queryInterface ( Constants.IOPCServer_IID );
    }

    public OPCSERVERSTATUS getStatus () throws JIException
    {
        JICallObject callObject = new JICallObject ( _opcServerObject.getIpid (), true );
        callObject.setOpnum ( 3 );

        JIStruct status = OPCSERVERSTATUS.getStruct ();

        callObject.addOutParamAsObject ( new JIPointer ( status, false ), JIFlags.FLAG_NULL );

        Object[] result = _opcServerObject.call ( callObject );

        System.out.println ( result.toString () );

        return null;
    }

    public OPCGroup addGroup ( String name, boolean active, int updateRate, int clientHandle, int timeBias, float percentDeadband, int localeID ) throws JIException, IllegalArgumentException, UnknownHostException
    {
        JICallObject callObject = new JICallObject ( _opcServerObject.getIpid (), true );
        callObject.setOpnum ( 0 );

        callObject.addInParamAsString ( name, JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR );
        callObject.addInParamAsInt ( active ? 1 : 0, JIFlags.FLAG_NULL );
        callObject.addInParamAsInt ( updateRate, JIFlags.FLAG_NULL );
        callObject.addInParamAsInt ( clientHandle, JIFlags.FLAG_NULL );
        callObject.addInParamAsPointer ( new JIPointer ( new Integer ( timeBias ) ), JIFlags.FLAG_NULL );
        callObject.addInParamAsPointer ( new JIPointer ( new Float ( percentDeadband ) ), JIFlags.FLAG_NULL );
        callObject.addInParamAsInt ( localeID, JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        callObject.addInParamAsUUID ( Constants.IOPCGroupStateMgt_IID, JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( JIInterfacePointer.class, JIFlags.FLAG_NULL );

        Object[] result = _opcServerObject.call ( callObject );
        
        return new OPCGroup ( ComFactory.createCOMInstance ( _opcServerObject, (JIInterfacePointer)result[2] ) );
    }
    
    public void removeGroup ( int serverHandle, boolean force ) throws JIException
    {
        JICallObject callObject = new JICallObject ( _opcServerObject.getIpid (), true );
        callObject.setOpnum ( 4 );
        
        callObject.addInParamAsInt ( serverHandle, JIFlags.FLAG_NULL );
        callObject.addInParamAsInt ( force ? 1 : 0, JIFlags.FLAG_NULL );
        
        _opcServerObject.call ( callObject );
    }
    
    public void removeGroup ( OPCGroup group, boolean force ) throws JIException
    {
        removeGroup ( group.getState ().getServerHandle (), force );
    }
    
    public OPCGroup getGroupByName ( String name ) throws JIException, IllegalArgumentException, UnknownHostException
    {
        JICallObject callObject = new JICallObject ( _opcServerObject.getIpid (), true );
        callObject.setOpnum ( 2 );

        callObject.addInParamAsString ( name, JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR );
        callObject.addInParamAsUUID ( Constants.IOPCGroupStateMgt_IID, JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( JIInterfacePointer.class, JIFlags.FLAG_NULL );

        Object[] result = _opcServerObject.call ( callObject );
        
        JIInterfacePointer ptr = (JIInterfacePointer)result[0];
        
        return new OPCGroup ( ComFactory.createCOMInstance ( _opcServerObject, ptr ) );
    }
    
    public OPCItemProperties getItemPropertiesService ()
    {
        try
        {
            return new OPCItemProperties ( _opcServerObject );
        }
        catch ( Exception e )
        {
            return null;
        }
    }
    
    public OPCItemIO getItemIOService ()
    {
        try
        {
            return new OPCItemIO ( _opcServerObject );
        }
        catch ( Exception e )
        {
            return null;
        }
    }
    
    /**
     * Get the browser object (<code>IOPCBrowseServerAddressSpace</code>) from the server instance
     * @return the browser object
     */
    public OPCBrowseServerAddressSpace getBrowser ()
    {
        try
        {
            return new OPCBrowseServerAddressSpace ( _opcServerObject );
        }
        catch ( Exception e )
        {
            return null;
        }
    }
}
