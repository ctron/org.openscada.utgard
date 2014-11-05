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

import java.util.Calendar;

import org.jinterop.dcom.core.JIVariant;

public class ValueData
{
    private JIVariant value;

    private short quality;

    private Calendar timestamp;

    public short getQuality ()
    {
        return this.quality;
    }

    public void setQuality ( final short quality )
    {
        this.quality = quality;
    }

    public Calendar getTimestamp ()
    {
        return this.timestamp;
    }

    public void setTimestamp ( final Calendar timestamp )
    {
        this.timestamp = timestamp;
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
