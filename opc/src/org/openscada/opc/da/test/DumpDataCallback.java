package org.openscada.opc.da.test;

import org.openscada.opc.da.Constants;
import org.openscada.opc.common.KeyedResultSet;
import org.openscada.opc.common.ResultSet;
import org.openscada.opc.da.IOPCDataCallback;
import org.openscada.opc.da.ValueData;

public class DumpDataCallback implements IOPCDataCallback
{

    public int cancelComplete ( int transactionId, int serverGroupHandle )
    {
        // TODO Auto-generated method stub
        return Constants.S_OK;
    }

    public int dataChange ( int transactionId, int serverGroupHandle, int masterQuality, int masterErrorCode, KeyedResultSet<Integer, ValueData> result )
    {
        // TODO Auto-generated method stub
        return Constants.S_OK;
    }

    public int readComplete ( int transactionId, int serverGroupHandle, int masterQuality, int masterErrorCode, KeyedResultSet<Integer, ValueData> result )
    {
        // TODO Auto-generated method stub
        return Constants.S_OK;
    }

    public int writeComplete ( int transactionId, int serverGroupHandle, int masterErrorCode, ResultSet<Integer> result )
    {
        // TODO Auto-generated method stub
        return Constants.S_OK;
    }

}
