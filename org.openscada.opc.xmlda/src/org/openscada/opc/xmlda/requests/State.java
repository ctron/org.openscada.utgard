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
package org.openscada.opc.xmlda.requests;

public class State
{
    public static final State GOOD = new State ( Quality.GOOD, null, null );

    private final Quality quality;

    private final Limit limit;

    private final Short vendor;

    public State ( final Quality quality, final Limit limit, final Short vendor )
    {
        this.quality = quality;
        this.limit = limit;
        this.vendor = vendor;
    }

    public Quality getQuality ()
    {
        return this.quality;
    }

    public Limit getLimit ()
    {
        return this.limit;
    }

    public Short getVendor ()
    {
        return this.vendor;
    }

    public boolean isGood ()
    {
        return this.quality.isGood ();
    }

    public boolean isBad ()
    {
        return this.quality.isBad ();
    }

    public boolean isUncertain ()
    {
        return this.quality.isUncertain ();
    }

    @Override
    public String toString ()
    {
        return String.format ( "[State - quality: %s, limit: %s, vendorCode: %s]", this.quality, this.limit, this.vendor );
    }
}
