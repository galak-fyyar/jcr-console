package com.sokolenko.jcrconsole.server.handlers;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.util.Assert;

import javax.jcr.Session;

/**
 * @author Anatoliy Sokolenko
 */
@Scope( "request" )
@Lazy( true )
public abstract class AbstractJcrActionHandler<A extends Action<R>, R extends Result> implements ActionHandler<A, R> {
    @Autowired
    private Session session;

    private Class<A> actionClass;

    protected AbstractJcrActionHandler( Class<A> actionClass ) {
        Assert.notNull( actionClass, "actionClass" );

        this.actionClass = actionClass;
    }

    @Override
    public Class<A> getActionType() {
        return actionClass;
    }

    public Session getSession() {
        return session;
    }
}
