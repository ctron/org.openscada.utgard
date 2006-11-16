package org.openscada.opc.common;

import java.util.ArrayList;

public class ResultSet<T> extends ArrayList<Result<T>>
{

    /**
     * 
     */
    private static final long serialVersionUID = 6392417310208978252L;

    public ResultSet ()
    {
        super ();
    }

    public ResultSet ( int size )
    {
        super ( size ); // me
    }
}
