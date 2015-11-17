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
package org.openscada.opc.xmlda;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ItemRequest
{
    private final String clientHandle;

    private final String itemName;

    private final String itemPath;

    public ItemRequest ( final String clientHandle, final String itemName, final String itemPath )
    {
        this.clientHandle = clientHandle;
        this.itemName = itemName;
        this.itemPath = itemPath;
    }

    public String getClientHandle ()
    {
        return this.clientHandle;
    }

    public String getItemName ()
    {
        return this.itemName;
    }

    @Override
    public String toString ()
    {
        return String.format ( "[ItemRequest: %s - %s - %s]", this.itemName, this.itemPath, this.clientHandle );
    }

    public static List<ItemRequest> makeRequests ( final Collection<String> itemNames )
    {
        final List<ItemRequest> result = new CopyOnWriteArrayList<> ();

        for ( final String itemName : itemNames )
        {
            result.add ( new ItemRequest ( itemName, itemName, null ) );
        }

        return result;
    }

}
