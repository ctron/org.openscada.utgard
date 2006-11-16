package org.openscada.opc.dcom.common;

import java.util.ArrayList;

public class KeyedResultSet<K, V> extends ArrayList<KeyedResult<K, V>>
{

    /**
     * 
     */
    private static final long serialVersionUID = -5068583248318042184L;

    public KeyedResultSet ()
    {
        super ();
    }

    public KeyedResultSet ( int size )
    {
        super ( size ); // me
    }
}
