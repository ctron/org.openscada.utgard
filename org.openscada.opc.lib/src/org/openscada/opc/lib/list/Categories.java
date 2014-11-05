/*
 * This file is part of the OpenSCADA project
 * Copyright (C) 2006-2010 TH4 SYSTEMS GmbH (http://th4-systems.com)
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openscada.opc.lib.list;

public interface Categories
{
    /**
     * Category of the OPC DA 1.0 Servers
     */
    public final static Category OPCDAServer10 = new Category ( org.openscada.opc.dcom.common.Categories.OPCDAServer10 );

    /**
     * Category of the OPC DA 2.0 Servers
     */
    public final static Category OPCDAServer20 = new Category ( org.openscada.opc.dcom.common.Categories.OPCDAServer20 );

    /**
     * Category of the OPC DA 3.0 Servers
     */
    public final static Category OPCDAServer30 = new Category ( org.openscada.opc.dcom.common.Categories.OPCDAServer30 );

    /**
     * Category of the XML DA 1.0 Servers
     */
    public final static Category XMLDAServer10 = new Category ( org.openscada.opc.dcom.common.Categories.XMLDAServer10 );
}
