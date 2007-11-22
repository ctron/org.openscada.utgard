package org.openscada.opc.lib.common;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jinterop.dcom.core.IJIBindingSelector;
import org.jinterop.dcom.core.JIDefaultBindingSelector;
import org.jinterop.dcom.core.JITweakableBindingSelector;

public class OPC
{
    private static Logger _log = Logger.getLogger ( OPC.class );
    
    protected static IJIBindingSelector defaultBindingSelector = new JIDefaultBindingSelector ();

    static
    {
        defaultBindingSelector = createBindingSelector ( createPreferredHosts ( System.getProperty ( "openscada.opc.preferredHosts" ) ) );
        _log.debug ( String.format ( "Using defaultBindingSelector class: %s", defaultBindingSelector.getClass () ) );
    }

    /**
     * Set the default binding selector which will be used by all others as a default
     * @param selector the default binding selector
     */
    public static void setDefaultBindingSelector ( IJIBindingSelector selector )
    {
        defaultBindingSelector = selector;
    }

    public static IJIBindingSelector getDefaultBindingSelector ()
    {
        return defaultBindingSelector;
    }

    /**
     * Create the set of preferred hosts from a string
     * @param preferredHosts the preferred hosts string
     * @return
     */
    public static Set<String> createPreferredHosts ( String preferredHosts )
    {
        if ( preferredHosts == null )
        {
            return null;
        }
        return new HashSet<String> ( Arrays.asList ( preferredHosts.split ( "[\\s,]+" ) ) );
    }

    public static IJIBindingSelector createBindingSelector ( Set<String> preferredHosts )
    {
        if ( preferredHosts == null )
        {
            return OPC.defaultBindingSelector;
        }
        else
        {
            return new JITweakableBindingSelector ( preferredHosts );
        }
    }
}
