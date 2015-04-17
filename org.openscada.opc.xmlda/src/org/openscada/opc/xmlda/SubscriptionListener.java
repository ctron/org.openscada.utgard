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

import java.util.Map;

import org.openscada.opc.xmlda.requests.ItemValue;

public interface SubscriptionListener
{
    public void stateChange ( SubscriptionState state );

    public void dataChange ( Map<String, ItemValue> values );
}
