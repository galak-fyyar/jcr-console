package com.sokolenko.jcrconsole.server;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.jcr.Credentials;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

/**
 * @author Anatoliy Sokolenko
 */
@Component
@Scope( "request" )
public class JackrabbitSessionManager implements FactoryBean<Session>, DisposableBean {
    protected static final Credentials CREDENTIALS = new SimpleCredentials( "admin", "".toCharArray() );

    @Value( "${sample.workspace.name}" )
    private String sampleWorkspace;

    @Autowired
    private JackrabbitSessionData sessionData;

    @Autowired
    private JackrabbitRepositoryHolder jackrabbitRepositoryHolder;

    private Session session;

    @Override
    public void destroy() {
        if ( session != null && session.isLive() ) {
            session.logout();
            session = null;
        }
    }

    @Override
    public Session getObject() throws Exception {
        if ( session == null ) {
            String workspaceName = sessionData.getWorkspaceName();
            if ( workspaceName == null ) {
                Session tmpSession = jackrabbitRepositoryHolder.getRepository().login( CREDENTIALS );

                workspaceName = RandomStringUtils.randomAlphabetic( 15 );
                if ( StringUtils.isNotBlank( sampleWorkspace ) ) {
                    tmpSession.getWorkspace().createWorkspace( workspaceName, sampleWorkspace );
                } else {
                    tmpSession.getWorkspace().createWorkspace( workspaceName );
                }
            }

            sessionData.setWorkspaceName( workspaceName );

            session = jackrabbitRepositoryHolder.getRepository().login( CREDENTIALS, workspaceName );
        }

        return session;
    }

    @Override
    public Class<?> getObjectType() {
        return Session.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
