package org.openscada.opc.common.impl;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.core.JIArray;
import org.jinterop.dcom.core.JICallObject;
import org.jinterop.dcom.core.JIFlags;
import org.jinterop.dcom.core.JIInterfacePointer;
import org.jinterop.dcom.core.JIPointer;
import org.jinterop.dcom.core.JIString;
import org.jinterop.dcom.win32.ComFactory;

public class EnumString
{
    private IJIComObject _enumStringObject = null;

    public EnumString ( IJIComObject enumStringObject ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        _enumStringObject = (IJIComObject)enumStringObject
                .queryInterface ( org.openscada.opc.common.Constants.IEnumString_IID );
    }

    public int next ( List<String> list, int num ) throws JIException
    {
        if ( num <= 0 )
            return 0;

        JICallObject callObject = new JICallObject ( _enumStringObject.getIpid (), true );
        callObject.setOpnum ( 0 );

        callObject.addInParamAsInt ( num, JIFlags.FLAG_NULL );
        callObject.addOutParamAsObject ( new JIArray ( new JIPointer ( new JIString (
                JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR ) ), null, 1, true, true ), JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( Integer.class, JIFlags.FLAG_NULL );

        Object[] result = Helper.callRespectSFALSE ( _enumStringObject, callObject );
        
        JIPointer[] resultData = (JIPointer[]) ( (JIArray) ( result[0] ) ).getArrayInstance ();
        Integer cnt = (Integer)result[1];

        for ( int i = 0; i < cnt; i++ )
        {
            list.add ( ( (JIString)resultData[i].getReferent () ).getString () );
        }
        return cnt;
    }

    public Collection<String> next ( int num ) throws JIException
    {
        List<String> list = new ArrayList<String> ( num );
        next ( list, num );
        return list;
    }

    public void skip ( int num ) throws JIException
    {
        if ( num <= 0 )
            return;

        JICallObject callObject = new JICallObject ( _enumStringObject.getIpid (), true );
        callObject.setOpnum ( 1 );

        callObject.addInParamAsInt ( num, JIFlags.FLAG_NULL );

        _enumStringObject.call ( callObject );
    }

    public void reset () throws JIException
    {
        JICallObject callObject = new JICallObject ( _enumStringObject.getIpid (), true );
        callObject.setOpnum ( 2 );

        _enumStringObject.call ( callObject );
    }

    public EnumString cloneObject () throws JIException, IllegalArgumentException, UnknownHostException
    {
        JICallObject callObject = new JICallObject ( _enumStringObject.getIpid (), true );
        callObject.setOpnum ( 3 );

        callObject.addOutParamAsType ( JIInterfacePointer.class, JIFlags.FLAG_NULL );

        Object[] result = _enumStringObject.call ( callObject );

        IJIComObject object = ComFactory.createCOMInstance ( _enumStringObject, (JIInterfacePointer)result[0] );

        return new EnumString ( object );
    }

    public Collection<String> asCollection () throws JIException
    {
        reset ();

        List<String> data = new ArrayList<String> ();
        int i = 0;
        do
        {
            i = next ( data, 100 );
        } while ( i == 100 );

        return data;
    }

}
