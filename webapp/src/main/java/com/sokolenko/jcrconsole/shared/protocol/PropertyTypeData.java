package com.sokolenko.jcrconsole.shared.protocol;

import com.sokolenko.jcrconsole.client.util.Assert;

/**
 * @author Anatoliy Sokolenko
 */
public enum PropertyTypeData {
    STRING( "string" ),
    BINARY( "binary" ),
    LONG( "long" ),
    DOUBLE( "double" ),
    DATE( "date" ),
    BOOLEAN( "boolean" ),
    NAME( "name" ),
    PATH( "path" ),
    REFERENCE( "reference" ),
    WEAK_REFERENCE( "weak reference" ),
    URI( "uri" ),
    DECIMAL( "decimal" ),
    UNDEFINED( "undefined" );

    private String jcrName;

    PropertyTypeData( String jcrName ) {
        Assert.notNull( jcrName, "jcrName" );

        this.jcrName = jcrName;
    }

    public String getJcrName() {
        return jcrName;
    }
}
