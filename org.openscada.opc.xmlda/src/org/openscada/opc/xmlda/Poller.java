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

import java.util.List;

public interface Poller extends AutoCloseable
{
    public void addItem ( ItemRequest item );

    public void removeItem ( ItemRequest item );

    public void setItems ( List<ItemRequest> items );

    public void setItems ( String... items );

    @Override
    public void close (); // we promise not to throw anything
}
