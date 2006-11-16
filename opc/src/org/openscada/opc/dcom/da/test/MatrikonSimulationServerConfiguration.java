package org.openscada.opc.dcom.da.test;

import org.jinterop.dcom.core.JIVariant;

public class MatrikonSimulationServerConfiguration implements TestConfiguration
{

    public String getCLSID ()
    {
        return "F8582CF2-88FB-11D0-B850-00C0F0104305";
    }

    public String[] getReadItems ()
    {
        return new String[] { "Saw-toothed Waves.Int2", "Saw-toothed Waves.Int4" };
    }

    public WriteTest[] getWriteItems ()
    {
        return new WriteTest[] { new WriteTest ( "Write Only.Int2", new JIVariant ( (short)1202, false ) ),
                new WriteTest ( "Write Only.Int4", new JIVariant ( 1202, false ) ) };
    }

}
