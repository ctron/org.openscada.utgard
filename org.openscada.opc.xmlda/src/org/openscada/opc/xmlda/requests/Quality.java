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

public enum Quality
{
    BAD ( Boolean.FALSE ),

    GOOD ( Boolean.TRUE ),
    GOOD_LOCAL_OVERRIDE ( Boolean.TRUE ),

    UNCERTAIN ( null ),
    UNCERTAIN_EU_EXCEEDED ( null ),
    UNCERTAIN_LAST_USABLE_VALUE ( null ),
    UNCERTAIN_SENSOR_NOT_ACCURATE ( null ),
    UNCERTAIN_SUB_NORMAL ( null ),

    BAD_COMM_FAILURE ( Boolean.FALSE ),
    BAD_CONFIGURATION_ERROR ( Boolean.FALSE ),
    BAD_DEVICE_FAILURE ( Boolean.FALSE ),
    BAD_LAST_KNOWN_VALUE ( Boolean.FALSE ),
    BAD_NOT_CONNECTED ( Boolean.FALSE ),
    BAD_OUT_OF_SERVICE ( Boolean.FALSE ),
    BAD_SENSOR_FAILURE ( Boolean.FALSE ),
    BAD_WAITING_FOR_INITIAL_DATA ( Boolean.FALSE ),

    UNKNOWN ( Boolean.FALSE );

    private Boolean good;

    private Quality ( final Boolean good )
    {
        this.good = good;
    }

    public boolean isGood ()
    {
        return this.good == Boolean.TRUE;
    }

    public boolean isBad ()
    {
        return this.good == Boolean.FALSE;
    }

    public boolean isUncertain ()
    {
        return this.good == null;
    }
}
