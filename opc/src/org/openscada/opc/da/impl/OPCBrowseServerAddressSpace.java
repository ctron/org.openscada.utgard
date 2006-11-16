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
import org.openscada.opc.common.impl.BaseCOMObject;
import org.openscada.opc.common.impl.EnumString;
import org.openscada.opc.common.impl.Helper;
import org.openscada.opc.da.Constants;
import org.openscada.opc.da.OPCBROWSEDIRECTION;
import org.openscada.opc.da.OPCBROWSETYPE;
import org.openscada.opc.da.OPCNAMESPACETYPE;

/**
 * Implementation for <code>IOPCBrowseServerAddressSpace</code>
 * @author Jens Reimann <jens.reimann@inavare.net>
 *
 */
public class OPCBrowseServerAddressSpace extends BaseCOMObject
{
    public OPCBrowseServerAddressSpace ( IJIComObject opcServer ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        super ( (IJIComObject)opcServer.queryInterface ( Constants.IOPCBrowseServerAddressSpace_IID ) );
    }

    /**
     * Get the information how the namespace is organized
     * @return the organization of the namespace
     * @throws JIException
     */
    public OPCNAMESPACETYPE queryOrganization () throws JIException
    {
        JICallObject callObject = new JICallObject ( getCOMObject ().getIpid (), true );
        callObject.setOpnum ( 0 );

        callObject.addOutParamAsType ( Short.class, JIFlags.FLAG_NULL );

        Object result[] = getCOMObject ().call ( callObject );

        return OPCNAMESPACETYPE.fromID ( (Short)result[0] );
    }

    /**
     * Direct the browser to another position
     * 
     * Depending on the <em>direction</em> the new position will be set based on the provided
     * position information. If the direction is {@link OPCBROWSEDIRECTION#OPC_BROWSE_TO} then
     * the <em>position</em> is the item to go to. If the direction is {@link OPCBROWSEDIRECTION#OPC_BROWSE_DOWN}
     * the browser will descent into the tree down (not to) the branch item in <em>position</em>.
     * Passing {@link OPCBROWSEDIRECTION#OPC_BROWSE_UP} won't need a <em>position</em> (pass <code>null</code>)
     * and will ascent in the tree one level.
     * 
     * Passing {@link OPCBROWSEDIRECTION#OPC_BROWSE_TO} and <code>null</code> as position will
     * go to the first root entry of the namespace.
     * 
     * @param position The item position reference for the direction
     * @param direction The direction to go based on the position
     * @throws JIException
     */
    public void changePosition ( String position, OPCBROWSEDIRECTION direction ) throws JIException
    {
        JICallObject callObject = new JICallObject ( getCOMObject ().getIpid (), true );
        callObject.setOpnum ( 1 );

        callObject.addInParamAsShort ( (short)direction.id (), JIFlags.FLAG_NULL );
        callObject.addInParamAsString ( position, JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR );

        getCOMObject ().call ( callObject );

    }

    public EnumString browse ( OPCBROWSETYPE browseType, String filterCriteria, int accessRights, int dataType ) throws JIException, IllegalArgumentException, UnknownHostException
    {
        JICallObject callObject = new JICallObject ( getCOMObject ().getIpid (), true );
        callObject.setOpnum ( 2 );

        callObject.addInParamAsShort ( (short)browseType.id (), JIFlags.FLAG_NULL );
        callObject.addInParamAsString ( filterCriteria, JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR );
        callObject.addInParamAsShort ( (short)dataType, JIFlags.FLAG_NULL );
        callObject.addInParamAsInt ( accessRights, JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( JIInterfacePointer.class, JIFlags.FLAG_NULL );

        Object result[] = Helper.callRespectSFALSE ( getCOMObject (), callObject );

        return new EnumString ( ComFactory.createCOMInstance ( getCOMObject (), (JIInterfacePointer)result[0] ) );
    }

    /**
     * Return the possible access paths for an item
     * @param itemID the item to query
     * @return A string enumerator for the possible access paths
     * @throws JIException
     * @throws IllegalArgumentException
     * @throws UnknownHostException
     */
    public EnumString browseAccessPaths ( String itemID ) throws JIException, IllegalArgumentException, UnknownHostException
    {
        JICallObject callObject = new JICallObject ( getCOMObject ().getIpid (), true );
        callObject.setOpnum ( 4 );

        callObject.addInParamAsString ( itemID, JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR );
        callObject.addOutParamAsType ( JIInterfacePointer.class, JIFlags.FLAG_NULL );

        Object[] result = Helper.callRespectSFALSE ( getCOMObject (), callObject );

        return new EnumString ( ComFactory.createCOMInstance ( getCOMObject (), (JIInterfacePointer)result[0] ) );
    }

    /**
     * Get the complete item id from an item at the local position.
     * 
     * Browsing a hierarchical namespace the browse method will return items based on the
     * local level in the namespace. So actually only the last part of the item ID hierarchy
     * is returned. In order to convert this to the full item ID one can use this method. It
     * will only work if the browser is still at the position in question. 
     * 
     * @param item the local item
     * @return the complete item ID
     * @throws JIException
     */
    public String getItemID ( String item ) throws JIException
    {
        JICallObject callObject = new JICallObject ( getCOMObject ().getIpid (), true );
        callObject.setOpnum ( 3 );

        callObject.addInParamAsString ( item, JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR );
        callObject.addOutParamAsObject ( new JIPointer ( new JIString ( JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR ) ), JIFlags.FLAG_NULL );

        Object[] result = getCOMObject ().call ( callObject );

        return ( (JIString) ( (JIPointer)result[0] ).getReferent () ).getString ();
    }
}
