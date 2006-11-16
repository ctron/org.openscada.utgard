package org.openscada.opc.da.impl;

import java.net.UnknownHostException;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.IJIComObject;
import org.openscada.opc.common.impl.BaseCOMObject;
import org.openscada.opc.da.Constants;

public class OPCBrowse extends BaseCOMObject
{
    public OPCBrowse ( IJIComObject opcServer ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        super ( (IJIComObject)opcServer.queryInterface ( Constants.IOPCBrowse_IID ) );
    }
}
