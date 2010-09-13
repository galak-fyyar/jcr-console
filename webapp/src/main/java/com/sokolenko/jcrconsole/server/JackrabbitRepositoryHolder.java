package com.sokolenko.jcrconsole.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.jcr.Repository;

/**
 * @author Anatoliy Sokolenko
 */
@Component
@Scope( "singleton" )
public class JackrabbitRepositoryHolder {
    @Autowired
    private Repository repository;

    public Repository getRepository() {
        return repository;
    }
}
