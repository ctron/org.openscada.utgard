/*
 * This file is part of the OpenSCADA project
 * Copyright (C) 2006-2007 inavare GmbH (http://inavare.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
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

    public AddFailedException ( Map<String, Integer> errors, Map<String, Item> items )
    {
        super ();
        _errors = errors;
        _items = items;
    }

    /**
     * Get the map of item id to error code
     * @return the result map containing the failed items
     */
    public Map<String, Integer> getErrors ()
    {
        return _errors;
    }

    /**
     * Get the map of item it to item object
     * @return the result map containing the succeeded items
     */
    public Map<String, Item> getItems ()
    {
        return _items;
    }
}
