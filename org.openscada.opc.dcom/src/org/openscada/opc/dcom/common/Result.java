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

package org.openscada.opc.dcom.common;

public class Result<T>
{
    private T value;

    private int errorCode;

    public Result ()
    {
    }

    public Result ( final T value, final int errorCode )
    {
        this.value = value;
        this.errorCode = errorCode;
    }

    public int getErrorCode ()
    {
        return this.errorCode;
    }

    public void setErrorCode ( final int errorCode )
    {
        this.errorCode = errorCode;
    }

    public T getValue ()
    {
        return this.value;
    }

    public void setValue ( final T value )
    {
        this.value = value;
    }

    public boolean isFailed ()
    {
        return this.errorCode != 0;
    }
}
