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

package org.openscada.opc.dcom.common.impl;

import org.jinterop.dcom.core.IJIComObject;

public class BaseCOMObject
{
    private IJIComObject comObject = null;

    /**
     * Create a new base COM object
     * 
     * @param comObject
     *            The COM object to wrap but be addRef'ed
     */
    public BaseCOMObject ( final IJIComObject comObject )
    {
        this.comObject = comObject;
    }

    protected synchronized IJIComObject getCOMObject ()
    {
        return this.comObject;
    }
}
