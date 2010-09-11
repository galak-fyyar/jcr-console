package com.sokolenko.jcrconsole.server;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ActionHandlerRegistry;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Anatoliy Sokolenko
 */
@Component
public class SpringActionHandlerRegistry implements ActionHandlerRegistry {
    @Value( "#{actionHandlers}" )
    private Set<ActionHandler<? extends Action, ? extends Result>> actionHandlers;

    @Override
    @SuppressWarnings( { "unchecked" } )
    public <A extends Action<R>, R extends Result> ActionHandler<A, R> findHandler( A action ) {
        if ( action == null ) {
            return null;
        }

        for ( ActionHandler<? extends Action, ? extends Result> actionHandler : actionHandlers ) {
            if ( actionHandler.getActionType().isAssignableFrom( action.getClass() ) ) {
                return ( ActionHandler<A, R> ) actionHandler;
            }
        }

        return null;
    }

    @Override
    public void clearHandlers() {
        actionHandlers.clear();
    }
}
