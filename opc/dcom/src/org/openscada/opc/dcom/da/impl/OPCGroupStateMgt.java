package org.openscada.opc.dcom.da.impl;

import java.net.UnknownHostException;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.core.JICallObject;
import org.jinterop.dcom.core.JIFlags;
import org.jinterop.dcom.core.JIInterfacePointer;
import org.jinterop.dcom.core.JIPointer;
import org.jinterop.dcom.core.JIString;
import org.jinterop.dcom.win32.ComFactory;
import org.openscada.opc.dcom.common.EventHandler;
import org.openscada.opc.dcom.common.impl.BaseCOMObject;
import org.openscada.opc.dcom.da.Constants;
import org.openscada.opc.dcom.da.IOPCDataCallback;
import org.openscada.opc.dcom.da.OPCGroupState;

/**
 * Implementation of <code>IOPCGroupStateMgt</code>
 * @author Jens Reimann <jens.reimann@inavare.net>
 *
 */
public class OPCGroupStateMgt extends BaseCOMObject
{
    public OPCGroupStateMgt ( IJIComObject opcGroup ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        super ( (IJIComObject)opcGroup.queryInterface ( Constants.IOPCGroupStateMgt_IID ) );
    }

    public OPCGroupState getState () throws JIException
    {
        JICallObject callObject = new JICallObject ( getCOMObject ().getIpid (), true );
        callObject.setOpnum ( 0 );

        callObject.addOutParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( Boolean.class, JIFlags.FLAG_NULL );
        callObject.addOutParamAsObject ( new JIPointer ( new JIString ( JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR ) ), JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( Float.class, JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( Integer.class, JIFlags.FLAG_NULL );

        Object result[] = getCOMObject ().call ( callObject );

        OPCGroupState state = new OPCGroupState ();
        state.setUpdateRate ( (Integer)result[0] );
        state.setActive ( (Boolean)result[1] );
        state.setName ( ( (JIString) ( (JIPointer)result[2] ).getReferent () ).getString () );
        state.setTimeBias ( (Integer)result[3] );
        state.setPercentDeadband ( (Float)result[4] );
        state.setLocaleID ( (Integer)result[5] );
        state.setClientHandle ( (Integer)result[6] );
        state.setServerHandle ( (Integer)result[7] );

        return state;
    }

    /**
     * Set the group state
     * 
     * Leaving any of the parameters <code>null</code> will keep the current value untouched.
     * 
     * @param requestedUpdateRate the requested update rate
     * @param active Flag if the group is active or not 
     * @param timeBias The time bias
     * @param percentDeadband the deadband percent
     * @param localeID the locale ID
     * @param clientHandle the client handle
     * @return the granted update rate
     * @throws JIException
     */
    public int setState ( Integer requestedUpdateRate, Boolean active, Integer timeBias, Float percentDeadband, Integer localeID, Integer clientHandle ) throws JIException
    {
        JICallObject callObject = new JICallObject ( getCOMObject ().getIpid (), true );
        callObject.setOpnum ( 1 );

        callObject.addInParamAsPointer ( new JIPointer ( requestedUpdateRate ), JIFlags.FLAG_NULL );
        if ( active != null )
            callObject.addInParamAsPointer ( new JIPointer ( Integer.valueOf ( active.booleanValue () ? 1 : 0 ) ), JIFlags.FLAG_NULL );
        else
            callObject.addInParamAsPointer ( new JIPointer ( null ), JIFlags.FLAG_NULL );
        callObject.addInParamAsPointer ( new JIPointer ( timeBias ), JIFlags.FLAG_NULL );
        callObject.addInParamAsPointer ( new JIPointer ( percentDeadband ), JIFlags.FLAG_NULL );
        callObject.addInParamAsPointer ( new JIPointer ( localeID ), JIFlags.FLAG_NULL );
        callObject.addInParamAsPointer ( new JIPointer ( clientHandle ), JIFlags.FLAG_NULL );

        callObject.addOutParamAsType ( Integer.class, JIFlags.FLAG_NULL );

        Object[] result = getCOMObject ().call ( callObject );

        return (Integer)result[0];
    }

    public OPCItemMgt getItemManagement () throws IllegalArgumentException, UnknownHostException, JIException
    {
        return new OPCItemMgt ( getCOMObject () );
    }

    /**
     * Rename to group
     * @param name the new name
     * @throws JIException
     */
    public void setName ( String name ) throws JIException
    {
        JICallObject callObject = new JICallObject ( getCOMObject ().getIpid (), true );
        callObject.setOpnum ( 2 );

        callObject.addInParamAsString ( name, JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR );

        getCOMObject ().call ( callObject );
    }

    /**
     * Clone the group
     * @param name the name of the cloned group
     * @return The cloned group
     * @throws JIException 
     * @throws UnknownHostException 
     * @throws IllegalArgumentException 
     */
    public OPCGroupStateMgt clone ( String name ) throws JIException, IllegalArgumentException, UnknownHostException
    {
        JICallObject callObject = new JICallObject ( getCOMObject ().getIpid (), true );
        callObject.setOpnum ( 3 );

        callObject.addInParamAsString ( name, JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR );
        callObject.addInParamAsUUID ( Constants.IOPCGroupStateMgt_IID, JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( JIInterfacePointer.class, JIFlags.FLAG_NULL );

        Object[] result = getCOMObject ().call ( callObject );

        JIInterfacePointer ptr = (JIInterfacePointer)result[0];

        return new OPCGroupStateMgt ( ComFactory.createCOMInstance ( getCOMObject (), ptr ) );
    }

    public EventHandler attach ( IOPCDataCallback callback ) throws JIException
    {
        OPCDataCallback callbackObject = new OPCDataCallback ();
        callbackObject.setCallback ( callback );

        String id = ComFactory.attachEventHandler ( getCOMObject (), Constants.IOPCDataCallback_IID, JIInterfacePointer
                .getInterfacePointer ( getCOMObject ().getAssociatedSession (), callbackObject.getCoClass () ) );

        callbackObject.setInfo ( getCOMObject (), id );
        return callbackObject;
    }

    public OPCAsyncIO2 getAsyncIO2 ()
    {
        try
        {
            return new OPCAsyncIO2 ( getCOMObject () );
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
            return new OPCSyncIO ( getCOMObject () );
        }
        catch ( Exception e )
        {
            return null;
        }
    }
}
