package com.sokolenko.jcrconsole.client.core;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sokolenko.jcrconsole.client.util.Assert;
import net.customware.gwt.dispatch.client.AbstractDispatchAsync;
import net.customware.gwt.dispatch.client.ExceptionHandler;
import net.customware.gwt.dispatch.client.standard.StandardDispatchServiceAsync;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

/**
 * This class is the default implementation of {@link net.customware.gwt.dispatch.client.DispatchAsync}, which is
 * essentially the client-side access to the {@link net.customware.gwt.dispatch.server.Dispatch} class on the
 * server-side.
 *
 * @author David Peterson
 */
@Singleton
@Deprecated
public class StandardDispatchAsync extends AbstractDispatchAsync {
    private final StandardDispatchServiceAsync standardDispatchServiceAsync;

    @Inject
    public StandardDispatchAsync( StandardDispatchServiceAsync standardDispatchServiceAsync, ExceptionHandler exceptionHandler ) {
        super( exceptionHandler );

        Assert.notNull( standardDispatchServiceAsync, "standardDispatchServiceAsync" );

        this.standardDispatchServiceAsync = standardDispatchServiceAsync;
    }

    public <A extends Action<R>, R extends Result> void execute( final A action, final AsyncCallback<R> callback ) {
        standardDispatchServiceAsync.execute( action, new AsyncCallback<Result>() {
            public void onFailure( Throwable caught ) {
                StandardDispatchAsync.this.onFailure( action, caught, callback );
            }

            public void onSuccess( Result result ) {
                StandardDispatchAsync.this.onSuccess( action, ( R ) result, callback );
            }
        } );
    }

    protected StandardDispatchServiceAsync getStandardDispatchServiceAsync() {
        return standardDispatchServiceAsync;
    }
}
