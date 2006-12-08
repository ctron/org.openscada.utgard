package org.openscada.opc.lib.da;

public interface SyncAccessStateListener
{
    public abstract void stateChanged ( boolean state );
    public abstract void errorOccured ( Throwable t );
}
