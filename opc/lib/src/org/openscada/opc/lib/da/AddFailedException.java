package org.openscada.opc.lib.da;

import java.util.HashMap;
import java.util.Map;

public class AddFailedException extends Exception
{

    /**
     * 
     */
    private static final long serialVersionUID = 5299486640366935298L;

    private Map<String, Integer> _errors = new HashMap<String, Integer> ();

    private Map<String, Item> _items = new HashMap<String, Item> ();

    public AddFailedException ( Map<String, Integer> errors, Map<String, Item> items )
    {
        super ();
        _errors = errors;
        _items = items;
    }

    public Map<String, Integer> getErrors ()
    {
        return _errors;
    }

    public Map<String, Item> getItems ()
    {
        return _items;
    }
}
