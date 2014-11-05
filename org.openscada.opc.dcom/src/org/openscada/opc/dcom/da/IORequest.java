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

public class IORequest
{
    private String itemID;

    private int maxAge;

    public IORequest ( final String itemID, final int maxAge )
    {
        this.itemID = itemID;
        this.maxAge = maxAge;
    }

    public String getItemID ()
    {
        return this.itemID;
    }

    public void setItemID ( final String itemID )
    {
        this.itemID = itemID;
    }

    public int getMaxAge ()
    {
        return this.maxAge;
    }

    public void setMaxAge ( final int maxAge )
    {
        this.maxAge = maxAge;
    }

}