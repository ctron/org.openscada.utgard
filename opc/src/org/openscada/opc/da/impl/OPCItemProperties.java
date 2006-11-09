package org.openscada.opc.da.impl;

import java.lang.reflect.Array;
import java.net.UnknownHostException;
import java.util.Arrays;
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
import org.jinterop.dcom.core.JIVariant;
import org.openscada.opc.da.Constants;
import org.openscada.opc.da.ItemLookup;
import org.openscada.opc.da.PropertyDescription;
import org.openscada.opc.da.PropertyValue;

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
        
        Object result[] = _opcItemProperties.call ( callObject );

        List<PropertyDescription> properties = new LinkedList<PropertyDescription> ();

        int len = (Integer)result[0];
        Integer[] ids = (Integer[]) ( (JIArray) ( (JIPointer)result[1] ).getReferent () ).getArrayInstance ();
        JIString[] descriptions = (JIString[]) ( (JIArray) ( (JIPointer)result[2] ).getReferent () )
                .getArrayInstance ();
        Short[] variableTypes = (Short[]) ( (JIArray) ( (JIPointer)result[3] ).getReferent () ).getArrayInstance ();

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

    public Collection<PropertyValue> getItemProperties ( String itemID, int... properties ) throws JIException
    {
        Integer[] ids = new Integer[properties.length];
        for ( int i = 0; i < properties.length; i++ )
        {
            ids[i] = properties[i];
        }
        
        JICallObject callObject = new JICallObject ( _opcItemProperties.getIpid (), true );
        callObject.setOpnum ( 1 );

        callObject.addInParamAsString ( itemID, JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR );
        callObject.addInParamAsInt ( properties.length, JIFlags.FLAG_NULL );
        callObject.addInParamAsArray ( new JIArray ( ids, true ), JIFlags.FLAG_NULL );

        callObject.addOutParamAsObject (  new JIPointer ( new JIArray ( JIVariant.class, null, 1, true ) ), JIFlags.FLAG_NULL );
        callObject.addOutParamAsObject (  new JIPointer ( new JIArray ( Integer.class, null, 1, true ) ), JIFlags.FLAG_NULL );

        Object result[] = _opcItemProperties.call ( callObject );

        List<PropertyValue> propertyValues = new LinkedList<PropertyValue> ();

        JIVariant[] values = (JIVariant[]) ( (JIArray) ( (JIPointer)result[0] ).getReferent () ).getArrayInstance ();
        Integer[] errorCodes = (Integer[]) ( (JIArray) ( (JIPointer)result[1] ).getReferent () ).getArrayInstance ();

        for ( int i = 0; i < properties.length; i++ )
        {
            PropertyValue pv = new PropertyValue ();
            pv.setId ( properties[i] );
            pv.setValue ( values[i] );
            pv.setErrorCode ( errorCodes[i] );
            propertyValues.add ( pv );
        }
        return propertyValues;
    }
    
    public Collection<ItemLookup> lookupItemIDs ( String itemID, int... properties ) throws JIException
    {
        Integer[] ids = new Integer[properties.length];
        for ( int i = 0; i < properties.length; i++ )
        {
            ids[i] = properties[i];
        }

        JICallObject callObject = new JICallObject ( _opcItemProperties.getIpid (), true );
        callObject.setOpnum ( 2 );

        callObject.addInParamAsString ( itemID, JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR );
        callObject.addInParamAsInt ( properties.length, JIFlags.FLAG_NULL );
        callObject.addInParamAsArray ( new JIArray ( ids, true ), JIFlags.FLAG_NULL );

        callObject.addOutParamAsObject ( new JIPointer ( new JIArray ( new JIString ( JIFlags.FLAG_REPRESENTATION_STRING_BSTR ), null, 1, true ) ), JIFlags.FLAG_NULL );
        callObject.addOutParamAsObject ( new JIPointer ( new JIArray ( Integer.class, null, 1, true ) ), JIFlags.FLAG_NULL );
     
        Object result[] = _opcItemProperties.call ( callObject );

        List<ItemLookup> propertyValues = new LinkedList<ItemLookup> ();

        JIString[] itemIDs = (JIString[]) ( (JIArray) ( (JIPointer)result[0] ).getReferent () ).getArrayInstance ();
        Integer[] errorCodes = (Integer[]) ( (JIArray) ( (JIPointer)result[1] ).getReferent () ).getArrayInstance ();

        for ( int i = 0; i < properties.length; i++ )
        {
            ItemLookup il = new ItemLookup ();
            il.setId ( properties[i] );
            il.setItemId ( itemIDs[i].getString () );
            il.setErrorCode ( errorCodes[i] );
            propertyValues.add ( il );
        }
        return propertyValues;
    }
}
