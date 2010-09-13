package com.sokolenko.jcrconsole.server;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
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
public class JackrabbitSessionFactory implements FactoryBean<Session> {
    protected static final Credentials CREDENTIALS = new SimpleCredentials( "admin", "".toCharArray() );

    @Autowired
    private JackrabbitSessionData sessionData;

    @Autowired
    private JackrabbitRepositoryHolder jackrabbitRepositoryHolder;

    @Override
    public Session getObject() throws Exception {
        String workspaceName = sessionData.getWorkspaceName();
        if ( workspaceName == null ) {
            Session tmpSession = jackrabbitRepositoryHolder.getRepository().login( CREDENTIALS );

            workspaceName = RandomStringUtils.randomAlphabetic( 15 );
            if ( StringUtils.isNotBlank( sessionData.getSampleWorkspace() ) ) {
                tmpSession.getWorkspace().createWorkspace( workspaceName, sessionData.getSampleWorkspace() );
            } else {
                tmpSession.getWorkspace().createWorkspace( workspaceName );
            }
        }

        sessionData.setWorkspaceName( workspaceName );

        return jackrabbitRepositoryHolder.getRepository().login( CREDENTIALS, workspaceName );
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
