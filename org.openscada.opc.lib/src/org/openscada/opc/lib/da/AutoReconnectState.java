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

/**
 * A state for the auto-reconnect controller
 * @author Jens Reimann
 *
 */
public enum AutoReconnectState
{
    /**
     * Auto reconnect is disabled.
     */
    DISABLED,
    /**
     * Auto reconnect is enabled, but the connection is currently not established.
     */
    DISCONNECTED,
    /**
     * Auto reconnect is enabled, the connection is not established and the controller
     * is currently waiting the delay until it will reconnect.
     */
    WAITING,
    /**
     * Auto reconnect is enabled, the connection is not established but the controller
     * currently tries to establish the connection.
     */
    CONNECTING,
    /**
     * Auto reconnect is enabled and the connection is established.
     */
    CONNECTED
}