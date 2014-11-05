/*
 * This file is part of the OpenSCADA project
 * Copyright (C) 2006-2010 TH4 SYSTEMS GmbH (http://th4-systems.com)
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openscada.opc.dcom.common;

public class KeyedResult<K, V> extends Result<V>
{
    private K key;

    public KeyedResult ()
    {
        super ();
    }

    public KeyedResult ( final K key, final V value, final int errorCode )
    {
        super ( value, errorCode );
        this.key = key;
    }

    public K getKey ()
    {
        return this.key;
    }

    public void setKey ( final K key )
    {
        this.key = key;
    }
}
