/*
 * This file is part of the OpenSCADA project
 * Copyright (C) 2006-2010 TH4 SYSTEMS GmbH (http://th4-systems.com)
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openscada.opc.lib.da;

public class UnknownGroupException extends Exception
{
    private String _name = null;

    public UnknownGroupException ( final String name )
    {
        super ();
        this._name = name;
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1771564928794033075L;

    public String getName ()
    {
        return this._name;
    }

    public void setName ( final String name )
    {
        this._name = name;
    }

}
