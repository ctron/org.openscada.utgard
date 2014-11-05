/*
 * This file is part of the OpenSCADA project
 * Copyright (C) 2006-2010 TH4 SYSTEMS GmbH (http://th4-systems.com)
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openscada.opc.dcom.list;

/**
 * Details about an OPC server class
 * @author Jens Reimann &lt;jens.reimann@th4-systems.com&gt;
 * @since 0.1.8
 */
public class ClassDetails
{
    private String _clsId;

    private String _progId;

    private String _description;

    public String getClsId ()
    {
        return this._clsId;
    }

    public void setClsId ( final String clsId )
    {
        this._clsId = clsId;
    }

    public String getDescription ()
    {
        return this._description;
    }

    public void setDescription ( final String description )
    {
        this._description = description;
    }

    public String getProgId ()
    {
        return this._progId;
    }

    public void setProgId ( final String progId )
    {
        this._progId = progId;
    }
}
