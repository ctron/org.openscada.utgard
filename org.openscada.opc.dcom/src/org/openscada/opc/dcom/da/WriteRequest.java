/*
 * This file is part of the OpenSCADA project
 * 
 * Copyright (C) 2006-2010 TH4 SYSTEMS GmbH (http://th4-systems.com)
 * Copyright (C) 2013 Jens Reimann (ctron@dentrassi.de)
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openscada.opc.dcom.da;

import org.jinterop.dcom.core.JIVariant;

/**
 * Data for a write request to the server
 * 
 * @author Jens Reimann
 */
public class WriteRequest
{
    private int serverHandle = 0;

    private JIVariant value = JIVariant.EMPTY ();

    public WriteRequest ()
    {
    }

    public WriteRequest ( final WriteRequest request )
    {
        this.serverHandle = request.serverHandle;
        this.value = request.value;
    }

    /**
     * Create a new write request with pre-fille data
     * 
     * @param serverHandle
     *            the server handle of the item to write to
     * @param value
     *            the value to write.
     */
    public WriteRequest ( final int serverHandle, final JIVariant value )
    {
        this.serverHandle = serverHandle;
        this.value = value;
    }

    public int getServerHandle ()
    {
        return this.serverHandle;
    }

    public void setServerHandle ( final int serverHandle )
    {
        this.serverHandle = serverHandle;
    }

    public JIVariant getValue ()
    {
        return this.value;
    }

    public void setValue ( final JIVariant value )
    {
        this.value = value;
    }
}
