package org.openscada.opc.lib.da;

public interface ServerStateListener
{
    public abstract void connectionStateChanged ( boolean connected );
}
