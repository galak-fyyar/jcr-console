package com.sokolenko.jcrconsole.server.handlers;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.jcr.Session;

/**
 * @author Anatoliy Sokolenko
 */
@Scope( "request" )
public abstract class JcrActionHandler<A extends Action<R>, R extends Result> implements ActionHandler<A, R> {
    @Autowired
    private Session session;

    public Session getSession() {
        return session;
    }
}
