package com.sokolenko.jcrconsole.client.core;

import com.sokolenko.jcrconsole.client.util.Assert;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

/**
 * @author Anatoliy Sokolenko
 */
public abstract class SingletonAction<R extends Result> implements Action<R> {
    private String space;

    /**
     * Constructor only to satisfy serializaton rules.
     */
    public SingletonAction() {
    }

    public SingletonAction( String space ) {
        Assert.hasText( space, "space" );

        this.space = space;
    }

    public String getSpace() {
        return space;
    }
}
