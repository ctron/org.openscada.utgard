/*
 * This file is part of the OpenSCADA project
 * Copyright (C) 2006-2010 TH4 SYSTEMS GmbH (http://th4-systems.com)
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openscada.opc.dcom.common.impl;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.core.JIFrameworkHelper;
import org.openscada.opc.dcom.common.EventHandler;

public class EventHandlerImpl implements EventHandler
{
    private String identifier = null;

    private IJIComObject object = null;

    public String getIdentifier ()
    {
        return this.identifier;
    }

    public synchronized IJIComObject getObject ()
    {
        return this.object;
    }

    public synchronized void setInfo ( final IJIComObject object, final String identifier )
    {
        this.object = object;
        this.identifier = identifier;
    }

    public synchronized void detach () throws JIException
    {
        if ( this.object != null && this.identifier != null )
        {
            try
            {
                JIFrameworkHelper.detachEventHandler ( this.object, this.identifier );
            }
            finally
            {
                this.object = null;
                this.identifier = null;
            }
        }
    }

}
