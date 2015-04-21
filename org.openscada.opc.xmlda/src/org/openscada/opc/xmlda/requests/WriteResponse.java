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

import java.util.HashMap;
import java.util.Map;

public class WriteResponse extends BaseResponse
{
    private final Map<String, ItemValue> values = new HashMap<> ();

    private WriteResponse ()
    {
    }

    static class Builder extends BaseResponse.Builder
    {
        private final Map<String, ItemValue> values = new HashMap<> ();

        protected void apply ( final WriteResponse result )
        {
            super.apply ( result );
            result.values.putAll ( this.values );
        }

        public WriteResponse build ()
        {
            final WriteResponse result = new WriteResponse ();
            apply ( result );
            return result;
        }

        public void addValue ( final ItemValue itemValue )
        {
            this.values.put ( itemValue.getItemName (), itemValue );
        }
    }

    @Override
    public String toString ()
    {
        final StringBuilder sb = new StringBuilder ();
        sb.append ( "ReadResponse" + NL );
        sb.append ( LINE + NL );

        sb.append ( " Items:" + NL );
        for ( final ItemValue value : this.values.values () )
        {
            sb.append ( "  - " ).append ( value ).append ( NL );
        }

        sb.append ( super.toString () );

        return sb.toString ();
    }
}
