/*
 * This file is part of the OpenSCADA project
 * Copyright (C) 2006 inavare GmbH (http://inavare.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package org.openscada.opc.dcom.common.impl;

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

public class EnumString extends BaseCOMObject
{   
    private static final int MAX_FETCH_SIZE = 10;
    
    public EnumString ( IJIComObject enumStringObject ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        super ( (IJIComObject)enumStringObject.queryInterface ( org.openscada.opc.dcom.common.Constants.IEnumString_IID ) );
    }

    public int next ( List<String> list, int num ) throws JIException
    {
        if ( num <= 0 )
            return 0;

        JICallObject callObject = new JICallObject ( getCOMObject ().getIpid (), true );
        callObject.setOpnum ( 0 );

        callObject.addInParamAsInt ( num, JIFlags.FLAG_NULL );
        callObject.addInParamAsInt ( num, JIFlags.FLAG_NULL );
        callObject.addOutParamAsObject ( new JIArray ( new JIPointer ( new JIString ( JIFlags.FLAG_REPRESENTATION_STRING_LPWSTR ) ), null, 1, true,
                true ), JIFlags.FLAG_NULL );
        callObject.addOutParamAsType ( Integer.class, JIFlags.FLAG_NULL );

        Object[] result = Helper.callRespectSFALSE ( getCOMObject (), callObject );

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

        JICallObject callObject = new JICallObject ( getCOMObject ().getIpid (), true );
        callObject.setOpnum ( 1 );

        callObject.addInParamAsInt ( num, JIFlags.FLAG_NULL );

        getCOMObject ().call ( callObject );
    }

    public void reset () throws JIException
    {
        JICallObject callObject = new JICallObject ( getCOMObject ().getIpid (), true );
        callObject.setOpnum ( 2 );

        getCOMObject ().call ( callObject );
    }

    public EnumString cloneObject () throws JIException, IllegalArgumentException, UnknownHostException
    {
        JICallObject callObject = new JICallObject ( getCOMObject ().getIpid (), true );
        callObject.setOpnum ( 3 );

        callObject.addOutParamAsType ( JIInterfacePointer.class, JIFlags.FLAG_NULL );

        Object[] result = getCOMObject ().call ( callObject );

        IJIComObject object = ComFactory.createCOMInstance ( getCOMObject (), (JIInterfacePointer)result[0] );

        return new EnumString ( object );
    }

    public Collection<String> asCollection () throws JIException
    {
        reset ();

        List<String> data = new ArrayList<String> ();
        int i = 0;
        do
        {
            i = next ( data, MAX_FETCH_SIZE );
        } while ( i == MAX_FETCH_SIZE );

        return data;
    }

}
