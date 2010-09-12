package com.sokolenko.jcrconsole.server.handlers;

import com.sokolenko.jcrconsole.shared.protocol.GetNodeDataAction;
import com.sokolenko.jcrconsole.shared.protocol.GetNodeDataResult;
import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Anatoliy Sokolenko
 */
@Component
public class GetNodeDataActionHandler extends JcrActionHandler<GetNodeDataAction, GetNodeDataResult> {
    protected static final Class<GetNodeDataAction> ACTION_CLASS = GetNodeDataAction.class;

    @Override
    public Class<GetNodeDataAction> getActionType() {
        return ACTION_CLASS;
    }

    @Override
    public GetNodeDataResult execute( GetNodeDataAction action, ExecutionContext context ) throws DispatchException {
        return null;
    }

    @Override
    public void rollback( GetNodeDataAction action, GetNodeDataResult result, ExecutionContext context ) throws DispatchException {

    }
}
