package com.sokolenko.jcrconsole.server;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.jackrabbit.core.RepositoryImpl;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.jcr.Credentials;
import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

/**
 * @author Anatoliy Sokolenko
 */
@Component
@Scope( "session" )
public class JackrabbitSessionFactory implements FactoryBean<Session> {
    @Value( "${sample.workspace.name}" )
    private String sampleWorkspace;

    @Autowired
    private Repository repository;

    private static final Credentials CREDENTIALS = new SimpleCredentials( "admin", "".toCharArray() );

    private Session cacheSession;

    @Override
    public Session getObject() throws Exception {
        if ( cacheSession == null ) {
            Session tmpSession = repository.login( CREDENTIALS );

            String newWorkspaceName = RandomStringUtils.randomAlphabetic( 15 );
            if ( StringUtils.isNotBlank( sampleWorkspace ) ) {
                tmpSession.getWorkspace().createWorkspace( newWorkspaceName, sampleWorkspace );
            } else {
                tmpSession.getWorkspace().createWorkspace( newWorkspaceName );
            }

            cacheSession = repository.login( CREDENTIALS, newWorkspaceName );
        }

        return cacheSession;
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
