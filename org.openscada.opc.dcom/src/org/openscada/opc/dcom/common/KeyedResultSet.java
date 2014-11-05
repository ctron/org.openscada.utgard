/*
 * This file is part of the OpenSCADA project
 * Copyright (C) 2006-2012 TH4 SYSTEMS GmbH (http://th4-systems.com)
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openscada.opc.dcom.common;

import java.util.ArrayList;

public class KeyedResultSet<K, V> extends ArrayList<KeyedResult<K, V>>
{
    private static final long serialVersionUID = 1L;

    public KeyedResultSet ()
    {
        super ();
    }

    public KeyedResultSet ( final int size )
    {
        super ( size ); // me
    }
}
