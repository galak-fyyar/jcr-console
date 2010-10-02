package com.sokolenko.jcrconsole.server;

import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.server.standard.AbstractStandardDispatchServlet;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.NoSuchElementException;

/**
 * @author Anatoliy Sokolenko
 */
@Component
public class StandardDispatchServlet extends AbstractStandardDispatchServlet implements Controller, InitializingBean {
    private String servletName = null;

    @Autowired
    private Dispatch dispatch;

    @Autowired
    @SuppressWarnings( { "SpringJavaAutowiringInspection" } )
    private ServletContext servletContext;

    @Override
    public ModelAndView handleRequest( HttpServletRequest request, HttpServletResponse response ) throws Exception {
        service( request, response );

        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init( new ServletConfigStub() );
    }

    @Override
    protected Dispatch getDispatch() {
        return dispatch;
    }

    protected class ServletConfigStub implements ServletConfig {
        public String getServletName() {
            return servletName;
        }

        public ServletContext getServletContext() {
            return servletContext;
        }

        public String getInitParameter( String paramName ) {
            return null;
        }

        public Enumeration getInitParameterNames() {
            return new Enumeration() {
                @Override
                public boolean hasMoreElements() {
                    return false;
                }

                @Override
                public Object nextElement() {
                    throw new NoSuchElementException();
                }
            };
        }
    }
}
