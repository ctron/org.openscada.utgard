/*******************************************************************************
 * Copyright (c) 2016 IBH SYSTEMS GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBH SYSTEMS GmbH - initial API and implementation
 *******************************************************************************/
package org.openscada.opc.xmlda;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * this is basically a copy of the enum of the same name found in the model
 * 
 * the difference is the additional value for an undefined value
 */
public enum OpcType
{
    UNDEFINED ( -1, "undefined", "undefined" ), //$NON-NLS-1$ //$NON-NLS-2$
    STRING ( 0, "string", "string" ), //$NON-NLS-1$ //$NON-NLS-2$
    BOOLEAN ( 1, "boolean", "boolean" ), //$NON-NLS-1$ //$NON-NLS-2$
    FLOAT ( 2, "float", "float" ), //$NON-NLS-1$ //$NON-NLS-2$
    DOUBLE ( 3, "double", "double" ), //$NON-NLS-1$ //$NON-NLS-2$
    DECIMAL ( 4, "decimal", "decimal" ), //$NON-NLS-1$ //$NON-NLS-2$
    LONG ( 5, "long", "long" ), //$NON-NLS-1$ //$NON-NLS-2$
    INT ( 6, "int", "int" ), //$NON-NLS-1$ //$NON-NLS-2$
    SHORT ( 7, "short", "short" ), //$NON-NLS-1$ //$NON-NLS-2$
    BYTE ( 8, "byte", "byte" ), //$NON-NLS-1$ //$NON-NLS-2$
    UNSIGNED_LONG ( 9, "unsignedLong", "unsignedLong" ), //$NON-NLS-1$ //$NON-NLS-2$
    UNSIGNED_INT ( 10, "unsignedInt", "unsignedInt" ), //$NON-NLS-1$ //$NON-NLS-2$
    UNSIGNED_SHORT ( 11, "unsignedShort", "unsignedShort" ), //$NON-NLS-1$ //$NON-NLS-2$
    UNSIGNED_BYTE ( 12, "unsignedByte", "unsignedByte" ), //$NON-NLS-1$ //$NON-NLS-2$
    BASE64_BINARY ( 13, "base64Binary", "base64Binary" ), //$NON-NLS-1$ //$NON-NLS-2$
    DATE_TIME ( 14, "dateTime", "dateTime" ), //$NON-NLS-1$ //$NON-NLS-2$
    TIME ( 15, "time", "time" ), //$NON-NLS-1$ //$NON-NLS-2$
    DATE ( 16, "date", "date" ), //$NON-NLS-1$ //$NON-NLS-2$
    DURATION ( 17, "duration", "duration" ), //$NON-NLS-1$ //$NON-NLS-2$
    QNAME ( 18, "QName", "QName" ), //$NON-NLS-1$ //$NON-NLS-2$
    ARRAY_OF_ANY_TYPE ( 19, "ArrayOfAnyType", "ArrayOfAnyType" ), //$NON-NLS-1$ //$NON-NLS-2$
    ARRAY_OF_STRING ( 20, "ArrayOfString", "ArrayOfString" ), //$NON-NLS-1$ //$NON-NLS-2$
    ARRAY_OF_BOOLEAN ( 21, "ArrayOfBoolean", "ArrayOfBoolean" ), //$NON-NLS-1$ //$NON-NLS-2$
    ARRAY_OF_FLOAT ( 22, "ArrayOfFloat", "ArrayOfFloat" ), //$NON-NLS-1$ //$NON-NLS-2$
    ARRAY_OF_DOUBLE ( 23, "ArrayOfDouble", "ArrayOfDouble" ), //$NON-NLS-1$ //$NON-NLS-2$
    ARRAY_OF_DECIMAL ( 24, "ArrayOfDecimal", "ArrayOfDecimal" ), //$NON-NLS-1$ //$NON-NLS-2$
    ARRAY_OF_LONG ( 25, "ArrayOfLong", "ArrayOfLong" ), //$NON-NLS-1$ //$NON-NLS-2$
    ARRAY_OF_INT ( 26, "ArrayOfInt", "ArrayOfInt" ), //$NON-NLS-1$ //$NON-NLS-2$
    ARRAY_OF_SHORT ( 27, "ArrayOfShort", "ArrayOfShort" ), //$NON-NLS-1$ //$NON-NLS-2$
    ARRAY_OF_BYTE ( 28, "ArrayOfByte", "ArrayOfByte" ), //$NON-NLS-1$ //$NON-NLS-2$
    ARRAY_OF_UNSIGNED_LONG ( 29, "ArrayOfUnsignedLong", "ArrayOfUnsignedLong" ), //$NON-NLS-1$ //$NON-NLS-2$
    ARRAY_OF_UNSIGNED_INT ( 30, "ArrayOfUnsignedInt", "ArrayOfUnsignedInt" ), //$NON-NLS-1$ //$NON-NLS-2$
    ARRAY_OF_UNSIGNED_SHORT ( 31, "ArrayOfUnsignedShort", "ArrayOfUnsignedShort" ), //$NON-NLS-1$ //$NON-NLS-2$
    ARRAY_OF_UNSIGNED_BYTE ( 32, "ArrayOfUnsignedByte", "ArrayOfUnsignedByte" ), //$NON-NLS-1$ //$NON-NLS-2$
    ARRAY_OF_DATE_TIME ( 33, "ArrayOfDateTime", "ArrayOfDateTime" ); //$NON-NLS-1$ //$NON-NLS-2$

    private final int value;

    private final String name;

    private final String literal;

    private OpcType ( int value, String name, String literal )
    {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

    public int getValue ()
    {
        return value;
    }

    public String getName ()
    {
        return name;
    }

    public String getLiteral ()
    {
        return literal;
    }

    @Override
    public String toString ()
    {
        return literal;
    }

    private static final Map<String, OpcType> names = new HashMap<> ();

    static
    {
        for ( OpcType t : EnumSet.allOf ( OpcType.class ) )
        {
            names.put ( t.getName (), t );
        }
    }

    public static OpcType getByName ( String name )
    {
        return names.get ( name );
    }
}
