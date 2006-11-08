package org.openscada.opc.da.impl;

import java.net.UnknownHostException;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.IJIComObject;
import org.openscada.opc.common.impl.OPCCommon;
import org.openscada.opc.da.Constants;

public class OPCBrowse extends OPCCommon
{
    private IJIComObject _opcBrowseObject = null;

    public OPCBrowse ( IJIComObject opcServer ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        super ( opcServer );
        _opcBrowseObject = (IJIComObject)opcServer.queryInterface ( Constants.IOPCBrowse_IID );
    }
}
