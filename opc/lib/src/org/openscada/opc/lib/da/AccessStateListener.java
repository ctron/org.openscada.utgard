package org.openscada.opc.lib.da;

public interface AccessStateListener
{
    public abstract void stateChanged ( boolean state );
    public abstract void errorOccured ( Throwable t );
}
