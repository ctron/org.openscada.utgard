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
package org.openscada.opc.xmlda.browse;

import java.util.List;

import org.openscada.opc.xmlda.requests.BrowseEntry;

public interface BrowserListener
{
    public void stateChange ( BrowserState state, Throwable error );

    public void dataChange ( List<BrowseEntry> entries );
}
