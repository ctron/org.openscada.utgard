package org.openscada.opc.da.impl;

import java.net.UnknownHostException;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.core.JICallObject;
import org.jinterop.dcom.core.JIFlags;
import org.jinterop.dcom.core.JIInterfacePointer;
import org.jinterop.dcom.core.JIPointer;
import org.jinterop.dcom.core.JIString;
import org.jinterop.dcom.win32.ComFactory;
import org.openscada.opc.common.EventHandler;
import org.openscada.opc.common.impl.ConnectionPointContainer;
import org.openscada.opc.da.Constants;
import org.openscada.opc.da.IOPCDataCallback;
import org.openscada.opc.da.OPCGroupState;

public class OPCGroup
{
    private IJIComObject _opcGroupStateMgt = null;

    public OPCGroup ( IJIComObject opcGroup ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        _opcGroupStateMgt = (IJIComObject)opcGroup.queryInterface ( Constants.IOPCGroupStateMgt_IID );
    }
    
    public OPCGroupState getState () throws JIException
    {
        JICallObject callObject = new JICallObject ( _opcGroupStateMgt.getIpid (), true );
        callObject.setOpnum ( 0 );
        
        callObject.addOutParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( Boolean.class, JIFlags.FLAG_NULL );
        callObject.addOutParamAsObject ( new JIPointer ( new JIString ( JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR ) ), JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( Float.class, JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        
        Object result [] = _opcGroupStateMgt.call ( callObject );
        
        OPCGroupState state = new OPCGroupState ();
        state.setUpdateRate ( (Integer)result[0] );
        state.setActive ( (Boolean )result[1] );
        state.setName ( ((JIString)((JIPointer)result[2]).getReferent ()).getString () );
        state.setTimeBias ( (Integer) result[3] );
        state.setPercentDeadband ( (Float)result[4] );
        state.setLocaleID ( (Integer)result[5] );
        state.setClientHandle ( (Integer)result[6] );
        state.setServerHandle ( (Integer)result[7] );
        
        return state;
    }
    
    public OPCItemMgt getItemManagement () throws IllegalArgumentException, UnknownHostException, JIException
    {
        return new OPCItemMgt ( _opcGroupStateMgt );
    }
    
    /**
     * Rename to group
     * @param name the new name
     * @throws JIException
     */
    public void setName ( String name ) throws JIException
    {
        JICallObject callObject = new JICallObject ( _opcGroupStateMgt.getIpid (), true );
        callObject.setOpnum ( 2 );
        
        callObject.addInParamAsString ( name, JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR );
        
        _opcGroupStateMgt.call ( callObject );
    }
    
    /**
     * Clone the group
     * @param name the name of the cloned group
     * @return The cloned group
     * @throws JIException 
     * @throws UnknownHostException 
     * @throws IllegalArgumentException 
     */
    public OPCGroup clone ( String name ) throws JIException, IllegalArgumentException, UnknownHostException
    {
        JICallObject callObject = new JICallObject ( _opcGroupStateMgt.getIpid (), true );
        callObject.setOpnum ( 3 );
        
        callObject.addInParamAsString ( name, JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR );
        callObject.addInParamAsUUID ( Constants.IOPCGroupStateMgt_IID, JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( JIInterfacePointer.class, JIFlags.FLAG_NULL );

        Object[] result = _opcGroupStateMgt.call ( callObject );
        
        JIInterfacePointer ptr = (JIInterfacePointer)result[0];
        
        return new OPCGroup ( ComFactory.createCOMInstance ( _opcGroupStateMgt, ptr ) );
    }
    
    public EventHandler attach ( IOPCDataCallback callback ) throws JIException
    {
        OPCDataCallback callbackObject = new OPCDataCallback ();
        callbackObject.setCallback ( callback );
        
        String id = ComFactory.attachEventHandler ( _opcGroupStateMgt, Constants.IOPCDataCallback_IID, JIInterfacePointer.getInterfacePointer(_opcGroupStateMgt.getAssociatedSession (),callbackObject.getCoClass ()) );
        
        callbackObject.setInfo ( _opcGroupStateMgt, id );
        return callbackObject;
    }
    
    public ConnectionPointContainer getConnectionPointContainer () throws JIException
    {
        return new ConnectionPointContainer ( _opcGroupStateMgt );
    }
    
    public OPCAsyncIO2 getAsyncIO2 ()
    {
        try
        {
            return new OPCAsyncIO2 ( _opcGroupStateMgt );
        }
        catch ( Exception e )
        {
            return null;
        }
    }
    
    public OPCSyncIO getSyncIO ()
    {
        try
        {
            return new OPCSyncIO ( _opcGroupStateMgt );
        }
        catch ( Exception e )
        {
            return null;
        }
    }
}
