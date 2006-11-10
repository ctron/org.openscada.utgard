package org.openscada.opc.common;

public class KeyedResult<K,V> extends Result<V>
{
    private K _key = null;
    
    public KeyedResult ()
    {
        super ();
    }
    
    public KeyedResult ( K key, V value, int errorCode )
    {
        super ( value, errorCode );
        _key = key;
    }

    public K getKey ()
    {
        return _key;
    }

    public void setKey ( K key )
    {
        _key = key;
    }
}
