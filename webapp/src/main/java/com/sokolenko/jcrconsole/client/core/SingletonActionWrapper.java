package com.sokolenko.jcrconsole.client.core;

import com.sokolenko.jcrconsole.client.util.Assert;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

/**
 * @author Anatoliy Sokolenko
 */
public class SingletonActionWrapper<R extends Result> extends SingletonAction<R> {
    private Action action;

    /**
     * Constructor only to satisfy serializaton rules.
     */
    public SingletonActionWrapper() {
    }

    public SingletonActionWrapper( String space, Action action ) {
        super( space );

        Assert.notNull( action, "action" );

        this.action = action;
    }

    public Action getAction() {
        return action;
    }
}
