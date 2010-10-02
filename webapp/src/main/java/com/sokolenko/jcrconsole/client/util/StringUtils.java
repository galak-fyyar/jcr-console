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
}
