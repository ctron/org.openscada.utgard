package org.openscada.opc.da.test;

public class MatrikonSimulationServerConfiguration implements TestConfiguration
{

    public String getCLSID ()
    {
        return "F8582CF2-88FB-11D0-B850-00C0F0104305";
    }

    public String[] getTestItems ()
    {
        return new String [] { "Saw-toothed Waves.Int2", "Saw-toothed Waves.Int4" };
    }

}
