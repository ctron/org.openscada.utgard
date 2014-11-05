/*
 * This file is part of the OpenSCADA project
 * Copyright (C) 2006-2010 TH4 SYSTEMS GmbH (http://th4-systems.com)
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

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

    public AddFailedException ( final Map<String, Integer> errors, final Map<String, Item> items )
    {
        super ();
        this._errors = errors;
        this._items = items;
    }

    /**
     * Get the map of item id to error code
     * @return the result map containing the failed items
     */
    public Map<String, Integer> getErrors ()
    {
        return this._errors;
    }

    /**
     * Get the map of item it to item object
     * @return the result map containing the succeeded items
     */
    public Map<String, Item> getItems ()
    {
        return this._items;
    }
}
