package com.sokolenko.jcrconsole.client.util;

import java.util.Random;

/**
 * @author Anatoliy Sokolenko
 */
public abstract class StringUtils {
    protected static final Random RANDOM = new Random();

    public static final String EMPTY = "";

    private StringUtils() {
    }

    public static boolean isEmpty( String text ) {
        return text == null || text.equals( EMPTY );
    }

    public static boolean isNotEmpty( String text ) {
        return !isEmpty( text );
    }

    public static String random( int count ) {
        if ( count == 0 ) {
            return EMPTY;
        } else if ( count < 0 ) {
            throw new IllegalArgumentException( "Requested random string length " + count + " is less than 0." );
        } else {
            StringBuilder builder = new StringBuilder();
            for ( int i = 0; i < count; i++ ) {
                char ch = ( char ) RANDOM.nextInt();

                builder.append( ch );
            }
            return builder.toString();
        }
    }
}
