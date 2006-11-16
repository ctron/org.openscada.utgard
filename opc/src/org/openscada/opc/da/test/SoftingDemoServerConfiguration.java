package org.openscada.opc.da.test;

public class SoftingDemoServerConfiguration implements TestConfiguration
{

    public String getCLSID ()
    {
        return "2E565242-B238-11D3-842D-0008C779D775";
    }

    public String[] getTestItems ()
    {
        return new String[] { "increment.I2", "increment.I4" };
    }

}
