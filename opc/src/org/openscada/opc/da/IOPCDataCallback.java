package org.openscada.opc.da;

import org.openscada.opc.common.KeyedResultSet;
import org.openscada.opc.common.ResultSet;

public interface IOPCDataCallback
{
    public int dataChange ( int transactionId, int serverGroupHandle, int masterQuality, int masterErrorCode, KeyedResultSet<Integer,ValueData> result );
    public int readComplete ( int transactionId, int serverGroupHandle, int masterQuality, int masterErrorCode, KeyedResultSet<Integer,ValueData> result );
    public int writeComplete ( int transactionId, int serverGroupHandle, int masterErrorCode, ResultSet<Integer> result );
    public int cancelComplete ( int transactionId, int serverGroupHandle );
}
