package org.openscada.opc.lib.da.browser;

import java.net.UnknownHostException;
import java.util.Collection;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIVariant;
import org.openscada.opc.dcom.da.OPCBROWSETYPE;
import org.openscada.opc.dcom.da.impl.OPCBrowseServerAddressSpace;

public class FlatBrowser
{
    private OPCBrowseServerAddressSpace _browser = null;

    public FlatBrowser ( OPCBrowseServerAddressSpace browser )
    {
        _browser = browser;
    }

    public Collection<String> browse ( String filterCriteria ) throws IllegalArgumentException, UnknownHostException, JIException
    {
        return _browser.browse ( OPCBROWSETYPE.OPC_FLAT, filterCriteria, 0, JIVariant.VT_EMPTY ).asCollection ();
    }

}
