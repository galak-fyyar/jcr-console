package com.sokolenko.jcrconsole.server.handlers;

import com.sokolenko.jcrconsole.shared.protocol.butch.ButchAction;
import com.sokolenko.jcrconsole.shared.protocol.butch.ButchResult;
import com.sokolenko.jcrconsole.shared.protocol.butch.FailureResult;
import net.customware.gwt.dispatch.server.AbstractActionHandler;
import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.DispatchException;
import net.customware.gwt.dispatch.shared.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Anatoliy Sokolenko
 */
@Component
@Scope( "request" )
public class ButchActionHandler extends AbstractActionHandler<ButchAction, ButchResult> {
    @Autowired
    private Dispatch dispatch;

    public ButchActionHandler() {
        super( ButchAction.class );
    }

    @Override
    public ButchResult execute( ButchAction butchAction, ExecutionContext context ) throws DispatchException {
        ButchResult butchResult = new ButchResult();

        for ( Map.Entry<String, Action> actionEntry : butchAction.getActions().entrySet() ) {
            String key = actionEntry.getKey();
            Action action = actionEntry.getValue();

            Result result;
            try {
                result = dispatch.execute( action );
            } catch ( DispatchException e ) {
                result = new FailureResult<DispatchException>( e );
            }

            butchResult.getResults().put( key, result );
        }

        return butchResult;
    }

    @Override
    public void rollback( ButchAction action, ButchResult result, ExecutionContext context ) throws DispatchException {

    }
}
