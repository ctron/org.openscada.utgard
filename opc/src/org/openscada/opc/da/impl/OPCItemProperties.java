package org.openscada.opc.da.impl;

import java.net.UnknownHostException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.core.JIArray;
import org.jinterop.dcom.core.JICallObject;
import org.jinterop.dcom.core.JIFlags;
import org.jinterop.dcom.core.JIPointer;
import org.jinterop.dcom.core.JIString;
import org.openscada.opc.da.Constants;
import org.openscada.opc.da.PropertyDescription;

public class OPCItemProperties
{
    private IJIComObject _opcItemProperties = null;

    public OPCItemProperties ( IJIComObject opcItemProperties ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        _opcItemProperties = (IJIComObject)opcItemProperties.queryInterface ( Constants.IOPCItemProperties_IID );
    }
    
    public Collection<PropertyDescription> queryAvailableProperties ( String itemID ) throws JIException
    {
        JICallObject callObject = new JICallObject ( _opcItemProperties.getIpid (), true );
        callObject.setOpnum ( 0 );
        
        callObject.addInParamAsString ( itemID, JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR );
        
        callObject.addOutParamAsType ( Integer.class, JIFlags.FLAG_NULL );
        callObject.addOutParamAsObject ( new JIPointer ( new JIArray ( Integer.class, null, 1, true ) ), JIFlags.FLAG_NULL );
        callObject.addOutParamAsObject ( new JIPointer ( new JIArray ( new JIString ( JIFlags.FLAG_REPRESENTATION_STRING_BSTR ), null, 1, true ) ), JIFlags.FLAG_NULL );
        callObject.addOutParamAsObject ( new JIPointer ( new JIArray ( Short.class, null, 1, true ) ), JIFlags.FLAG_NULL );
        
        Object result [] = _opcItemProperties.call ( callObject );
        
        List<PropertyDescription> properties = new LinkedList<PropertyDescription> ();
        
        int len = (Integer)result[0];
        Integer [] ids = (Integer[]) ((JIArray)((JIPointer)result[1]).getReferent ()).getArrayInstance ();
        JIString [] descriptions = (JIString[]) ((JIArray)((JIPointer)result[2]).getReferent ()).getArrayInstance ();
        Short [] variableTypes = (Short[]) ((JIArray)((JIPointer)result[3]).getReferent ()).getArrayInstance ();
        
        for ( int i = 0; i < len; i++ )
        {
            PropertyDescription pd = new PropertyDescription ();
            pd.setId ( ids[i] );
            pd.setDescription ( descriptions[i].getString () );
            pd.setVarType ( variableTypes[i] );
            properties.add ( pd );
        }
        return properties;
    }
}
