package org.openscada.opc.da.test;

public interface TestConfiguration
{
    public String getCLSID ();

    public String[] getReadItems ();

    public WriteTest[] getWriteItems ();
}
