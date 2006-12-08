package org.openscada.opc.lib.da;

public interface ServerConnectionStateListener
{
    public abstract void connectionStateChanged ( boolean connected );
}
