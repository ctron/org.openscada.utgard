package org.openscada.opc.da.impl;

import org.jinterop.dcom.core.JIArray;
import org.openscada.opc.common.Constants;

public abstract class OPCDataCallback
{
    public int OnDataChange (
            int transactionId,
            int serverGroupHandle,
            int masterQuality,
            int masterErrorCode,
            int count,
            JIArray clientHandles,
            JIArray values,
            JIArray quantities,
            JIArray timestamps,
            JIArray errors
            )
    {
        return Constants.S_OK;
    }
    
    public int OnReadComplete (
            int transactionId,
            int serverGroupHandle,
            int masterQuality,
            int masterErrorCode,
            int count,
            JIArray clientHandles,
            JIArray values,
            JIArray quantities,
            JIArray timestamps,
            JIArray errors
            )
    {
        return Constants.S_OK;
    }
    
    public int OnWriteComplete (
            int transactionId,
            int serverGroupHandle,
            int masterErrorCode,
            int count,
            JIArray clientHandles,
            JIArray errors
    )
    {
        return Constants.S_OK;
    }
    
    public int OnCancelComplete (
            int transactionId,
            int serverGroupHandle )
    {
        return Constants.S_OK;
    }
}
