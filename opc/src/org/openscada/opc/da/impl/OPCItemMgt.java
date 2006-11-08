package org.openscada.opc.da.impl;

import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Collections;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.core.JIArray;
import org.jinterop.dcom.core.JICallObject;
import org.jinterop.dcom.core.JIFlags;
import org.jinterop.dcom.core.JIPointer;
import org.jinterop.dcom.core.JIString;
import org.jinterop.dcom.core.JIStruct;
import org.openscada.opc.da.Constants;
import org.openscada.opc.da.OPCITEMDEF;
import org.openscada.opc.da.OPCITEMRESULT;

public class OPCItemMgt
{
    private IJIComObject _opcItemMgt = null;

    public OPCItemMgt ( IJIComObject opcGroup ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        _opcItemMgt = (IJIComObject)opcGroup.queryInterface ( Constants.IOPCItemMgt_IID );
    }
    
    public Collection<OPCITEMRESULT> validate ( Collection<OPCITEMDEF> items ) throws JIException
    {
        if ( items.size () == 0 )
            return Collections.emptyList ();
        
        JICallObject callObject = new JICallObject ( _opcItemMgt.getIpid (), true );
        callObject.setOpnum ( 1 );
        
        JIStruct struct[] = new JIStruct[items.size()];
        int i = 0;
        for ( OPCITEMDEF itemDef : items )
        {
            struct[i] = new JIStruct ();
            struct[i].addMember ( new JIString ( itemDef.getAccessPath (), JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR ) );
            struct[i].addMember ( new JIString ( itemDef.getItemID (), JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR ) );
            struct[i].addMember ( Boolean.valueOf ( itemDef.isActive () ) );
            struct[i].addMember ( Integer.valueOf ( itemDef.getClientHandle () ) );
            struct[i].addMember ( Integer.valueOf ( 0 ) ); // blob size
            struct[i].addMember ( new JIPointer ( null ) ); // blob
            struct[i].addMember ( Short.valueOf ( itemDef.getRequestedDataType () ) );
            struct[i].addMember ( Short.valueOf ( itemDef.getReserved () ) );
            i++;
        }
        JIArray itemArray = new JIArray ( struct, true);
        
        callObject.addInParamAsInt ( items.size (), JIFlags.FLAG_NULL );
        callObject.addInParamAsPointer ( new JIPointer ( itemArray ), JIFlags.FLAG_NULL );
        callObject.addInParamAsInt ( 0, JIFlags.FLAG_NULL ); // don't update blobs
        callObject.addOutParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        callObject.addOutParamAsObject ( new JIPointer ( new JIArray ( OPCITEMRESULT.getStruct (), null, 1, true ) ), JIFlags.FLAG_NULL );
        callObject.addOutParamAsObject ( new JIPointer ( new JIArray ( Integer.class, null, 1, true ) ), JIFlags.FLAG_NULL );
        
        Object result [] = _opcItemMgt.call ( callObject );
        
        return null;
    }
}
