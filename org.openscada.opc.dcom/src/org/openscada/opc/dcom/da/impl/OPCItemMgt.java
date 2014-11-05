/*
 * This file is part of the OpenSCADA project
 * Copyright (C) 2006-2012 TH4 SYSTEMS GmbH (http://th4-systems.com)
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openscada.opc.dcom.da.impl;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.core.JIArray;
import org.jinterop.dcom.core.JICallBuilder;
import org.jinterop.dcom.core.JIFlags;
import org.jinterop.dcom.core.JIPointer;
import org.jinterop.dcom.core.JIStruct;
import org.openscada.opc.dcom.common.KeyedResult;
import org.openscada.opc.dcom.common.KeyedResultSet;
import org.openscada.opc.dcom.common.Result;
import org.openscada.opc.dcom.common.ResultSet;
import org.openscada.opc.dcom.common.impl.BaseCOMObject;
import org.openscada.opc.dcom.common.impl.Helper;
import org.openscada.opc.dcom.da.Constants;
import org.openscada.opc.dcom.da.OPCITEMDEF;
import org.openscada.opc.dcom.da.OPCITEMRESULT;

public class OPCItemMgt extends BaseCOMObject
{
    public OPCItemMgt ( final IJIComObject opcGroup ) throws JIException
    {
        super ( opcGroup.queryInterface ( Constants.IOPCItemMgt_IID ) );
    }

    public KeyedResultSet<OPCITEMDEF, OPCITEMRESULT> validate ( final OPCITEMDEF... items ) throws JIException
    {
        if ( items.length == 0 )
        {
            return new KeyedResultSet<OPCITEMDEF, OPCITEMRESULT> ();
        }

        final JICallBuilder callObject = new JICallBuilder ( true );
        callObject.setOpnum ( 1 );

        final JIStruct struct[] = new JIStruct[items.length];
        for ( int i = 0; i < items.length; i++ )
        {
            struct[i] = items[i].toStruct ();
        }
        final JIArray itemArray = new JIArray ( struct, true );

        callObject.addInParamAsInt ( items.length, JIFlags.FLAG_NULL );
        callObject.addInParamAsArray ( itemArray, JIFlags.FLAG_NULL );
        callObject.addInParamAsInt ( 0, JIFlags.FLAG_NULL ); // don't update blobs
        callObject.addOutParamAsObject ( new JIPointer ( new JIArray ( OPCITEMRESULT.getStruct (), null, 1, true ) ), JIFlags.FLAG_NULL );
        callObject.addOutParamAsObject ( new JIPointer ( new JIArray ( Integer.class, null, 1, true ) ), JIFlags.FLAG_NULL );

        final Object result[] = Helper.callRespectSFALSE ( getCOMObject (), callObject );

        final JIStruct[] results = (JIStruct[]) ( (JIArray) ( (JIPointer)result[0] ).getReferent () ).getArrayInstance ();
        final Integer[] errorCodes = (Integer[]) ( (JIArray) ( (JIPointer)result[1] ).getReferent () ).getArrayInstance ();

        final KeyedResultSet<OPCITEMDEF, OPCITEMRESULT> resultList = new KeyedResultSet<OPCITEMDEF, OPCITEMRESULT> ( items.length );
        for ( int i = 0; i < items.length; i++ )
        {
            final OPCITEMRESULT itemResult = OPCITEMRESULT.fromStruct ( results[i] );
            final KeyedResult<OPCITEMDEF, OPCITEMRESULT> resultEntry = new KeyedResult<OPCITEMDEF, OPCITEMRESULT> ( items[i], itemResult, errorCodes[i] );
            resultList.add ( resultEntry );
        }

        return resultList;
    }

    public KeyedResultSet<OPCITEMDEF, OPCITEMRESULT> add ( final OPCITEMDEF... items ) throws JIException
    {
        if ( items.length == 0 )
        {
            return new KeyedResultSet<OPCITEMDEF, OPCITEMRESULT> ();
        }

        final JICallBuilder callObject = new JICallBuilder ( true );
        callObject.setOpnum ( 0 );

        final JIStruct struct[] = new JIStruct[items.length];
        for ( int i = 0; i < items.length; i++ )
        {
            struct[i] = items[i].toStruct ();
        }
        final JIArray itemArray = new JIArray ( struct, true );

        callObject.addInParamAsInt ( items.length, JIFlags.FLAG_NULL );
        callObject.addInParamAsArray ( itemArray, JIFlags.FLAG_NULL );

        /*
        callObject.addOutParamAsObject ( new JIPointer ( new JIArray ( OPCITEMRESULT.getStruct (), null, 1, true ) ),
                JIFlags.FLAG_NULL );
        callObject.addOutParamAsObject ( new JIPointer ( new JIArray ( Integer.class, null, 1, true ) ),
                JIFlags.FLAG_NULL );
                */
        callObject.addOutParamAsObject ( new JIPointer ( new JIArray ( OPCITEMRESULT.getStruct (), null, 1, true ) ), JIFlags.FLAG_NULL );
        callObject.addOutParamAsObject ( new JIPointer ( new JIArray ( Integer.class, null, 1, true ) ), JIFlags.FLAG_NULL );

        final Object result[] = Helper.callRespectSFALSE ( getCOMObject (), callObject );

        final JIStruct[] results = (JIStruct[]) ( (JIArray) ( (JIPointer)result[0] ).getReferent () ).getArrayInstance ();
        final Integer[] errorCodes = (Integer[]) ( (JIArray) ( (JIPointer)result[1] ).getReferent () ).getArrayInstance ();

        final KeyedResultSet<OPCITEMDEF, OPCITEMRESULT> resultList = new KeyedResultSet<OPCITEMDEF, OPCITEMRESULT> ( items.length );
        for ( int i = 0; i < items.length; i++ )
        {
            final OPCITEMRESULT itemResult = OPCITEMRESULT.fromStruct ( results[i] );
            final KeyedResult<OPCITEMDEF, OPCITEMRESULT> resultEntry = new KeyedResult<OPCITEMDEF, OPCITEMRESULT> ( items[i], itemResult, errorCodes[i] );
            resultList.add ( resultEntry );
        }

        return resultList;
    }

    public ResultSet<Integer> remove ( final Integer... serverHandles ) throws JIException
    {
        if ( serverHandles.length == 0 )
        {
            return new ResultSet<Integer> ();
        }

        final JICallBuilder callObject = new JICallBuilder ( true );
        callObject.setOpnum ( 2 );

        callObject.addInParamAsInt ( serverHandles.length, JIFlags.FLAG_NULL );
        callObject.addInParamAsArray ( new JIArray ( serverHandles, true ), JIFlags.FLAG_NULL );
        callObject.addOutParamAsObject ( new JIPointer ( new JIArray ( Integer.class, null, 1, true ) ), JIFlags.FLAG_NULL );

        final Object result[] = Helper.callRespectSFALSE ( getCOMObject (), callObject );

        final Integer[] errorCodes = (Integer[]) ( (JIArray) ( (JIPointer)result[0] ).getReferent () ).getArrayInstance ();
        final ResultSet<Integer> results = new ResultSet<Integer> ( serverHandles.length );
        for ( int i = 0; i < serverHandles.length; i++ )
        {
            results.add ( new Result<Integer> ( serverHandles[i], errorCodes[i] ) );
        }
        return results;
    }

    public ResultSet<Integer> setActiveState ( final boolean state, final Integer... items ) throws JIException
    {
        if ( items.length == 0 )
        {
            return new ResultSet<Integer> ();
        }

        final JICallBuilder callObject = new JICallBuilder ( true );
        callObject.setOpnum ( 3 );

        callObject.addInParamAsInt ( items.length, JIFlags.FLAG_NULL );
        callObject.addInParamAsArray ( new JIArray ( items, true ), JIFlags.FLAG_NULL );
        callObject.addInParamAsInt ( state ? 1 : 0, JIFlags.FLAG_NULL );
        callObject.addOutParamAsObject ( new JIPointer ( new JIArray ( Integer.class, null, 1, true ) ), JIFlags.FLAG_NULL );

        final Object[] result = Helper.callRespectSFALSE ( getCOMObject (), callObject );

        final Integer[] errorCodes = (Integer[]) ( (JIArray) ( (JIPointer)result[0] ).getReferent () ).getArrayInstance ();
        final ResultSet<Integer> results = new ResultSet<Integer> ( items.length );
        for ( int i = 0; i < items.length; i++ )
        {
            results.add ( new Result<Integer> ( items[i], errorCodes[i] ) );
        }
        return results;
    }

    public ResultSet<Integer> setClientHandles ( final Integer[] serverHandles, final Integer[] clientHandles ) throws JIException
    {
        if ( serverHandles.length != clientHandles.length )
        {
            throw new JIException ( 0, "Array sizes don't match" );
        }
        if ( serverHandles.length == 0 )
        {
            return new ResultSet<Integer> ();
        }

        final JICallBuilder callObject = new JICallBuilder ( true );
        callObject.setOpnum ( 4 );

        callObject.addInParamAsInt ( serverHandles.length, JIFlags.FLAG_NULL );
        callObject.addInParamAsArray ( new JIArray ( serverHandles, true ), JIFlags.FLAG_NULL );
        callObject.addInParamAsArray ( new JIArray ( clientHandles, true ), JIFlags.FLAG_NULL );
        callObject.addOutParamAsObject ( new JIPointer ( new JIArray ( Integer.class, null, 1, true ) ), JIFlags.FLAG_NULL );

        final Object[] result = Helper.callRespectSFALSE ( getCOMObject (), callObject );

        final Integer[] errorCodes = (Integer[]) ( (JIArray) ( (JIPointer)result[0] ).getReferent () ).getArrayInstance ();
        final ResultSet<Integer> results = new ResultSet<Integer> ( serverHandles.length );
        for ( int i = 0; i < serverHandles.length; i++ )
        {
            results.add ( new Result<Integer> ( serverHandles[i], errorCodes[i] ) );
        }
        return results;
    }

}
