package com.sokolenko.jcrconsole.server;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * This filter makes any browser not to cache GWT's main javascript file - *.nocache.js.
 *
 * @author Anatoliy Sokolenko
 */
public class GwtSuppressCacheFilter implements Filter {
    protected static final String NOCACHE_SUFFIX = ".nocache.";

    protected static final String DATE_HEADER_NAME = "Date";
    protected static final String EXPIRES_HEADER_NAME = "Expires";

    protected static final String PRAGMA_HEADER_NAME = "Pragma";
    protected static final String PRAGMA_HEADER_VALUE = "no-cache";

    protected static final String CACHE_CONTROL_HEADER_NAME = "Cache-control";
    protected static final String CACHE_CONTROL_HEADER_VALUE = "no-cache, no-store, must-revalidate";

    @Override
    public void init( FilterConfig filterConfig ) throws ServletException {
    }

    @Override
    public void doFilter( ServletRequest request, ServletResponse response, FilterChain filterChain )
            throws IOException, ServletException {
        HttpServletRequest httpRequest = ( HttpServletRequest ) request;
        String requestURI = httpRequest.getRequestURI();

        if ( requestURI.contains( NOCACHE_SUFFIX ) ) {
            Date now = new Date();
            HttpServletResponse httpResponse = ( HttpServletResponse ) response;
            httpResponse.setDateHeader( DATE_HEADER_NAME, now.getTime() );
            // one day old
            httpResponse.setDateHeader( EXPIRES_HEADER_NAME, now.getTime() - 86400000L );
            httpResponse.setHeader( PRAGMA_HEADER_NAME, PRAGMA_HEADER_VALUE );
            httpResponse.setHeader( CACHE_CONTROL_HEADER_NAME, CACHE_CONTROL_HEADER_VALUE );
        }

        filterChain.doFilter( request, response );
    }

    @Override
    public void destroy() {
    }
}