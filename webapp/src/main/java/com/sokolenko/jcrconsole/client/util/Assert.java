package com.sokolenko.jcrconsole.client.util;

/**
 * @author Anatoliy Sokolenko
 */
public abstract class Assert {
    private Assert() {
    }

    public static void notNull( Object o, String parameter ) {
        if ( o == null ) {
            throw new IllegalArgumentException( "Attribute " + parameter + " should be specified" );
        }
    }

    public static void notNullState( Object o, String field ) {
        if ( o == null ) {
            throw new IllegalStateException( "Field " + field + " should be specified" );
        }
    }

    public static void hasText( String text, String parameter ) {
        if ( StringUtils.isEmpty( text ) ) {
            throw new IllegalArgumentException( "Attribute " + parameter + " should be specified" );
        }
    }
}
