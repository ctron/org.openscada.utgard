/*******************************************************************************
 * Copyright (c) 2015 IBH SYSTEMS GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBH SYSTEMS GmbH - initial API and implementation
 *******************************************************************************/
package org.openscada.opc.xmlda.requests;

import java.util.Collections;
import java.util.Map;

public class BrowseEntry
{
    private final String name;

    private final boolean item;

    private final boolean parent;

    private final String itemName;

    private final String itemPath;

    private final Map<String, ItemProperty> properties;

    public BrowseEntry ( final String name, final boolean item, final boolean parent, final String itemName, final String itemPath, final Map<String, ItemProperty> properties )
    {
        this.name = name;
        this.item = item;
        this.parent = parent;
        this.itemName = itemName;
        this.itemPath = itemPath;
        this.properties = Collections.unmodifiableMap ( properties );
    }

    public String getName ()
    {
        return this.name;
    }

    public boolean isItem ()
    {
        return this.item;
    }

    public boolean isParent ()
    {
        return this.parent;
    }

    public String getItemName ()
    {
        return this.itemName;
    }

    public String getItemPath ()
    {
        return this.itemPath;
    }

    public Map<String, ItemProperty> getProperties ()
    {
        return this.properties;
    }
}
