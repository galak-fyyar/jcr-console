package com.sokolenko.jcrconsole.client.presenter;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sokolenko.jcrconsole.client.util.Assert;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anatoliy Sokolenko
 */
public abstract class DataLoadingPresenter<D extends WidgetDisplay> extends WidgetPresenter<D> {
    private final List<Action<Result>> waitingActions = new ArrayList<Action<Result>>();

    private final DispatchAsync dispatchAsync;

    public DataLoadingPresenter( D display, EventBus eventBus, DispatchAsync dispatchAsync ) {
        super( display, eventBus );

        Assert.notNull( dispatchAsync, "dispatchAsync" );

        this.dispatchAsync = dispatchAsync;
    }

    protected <A extends Action<R>, R extends Result> void execute( A action, AsyncCallback<R> callback ) {
        Assert.notNull( action, "action" );
        Assert.notNull( callback, "callback" );

        dispatchAsync.execute( action, new AsyncCallbackWrapper( action, callback ) );

        waitForResult( action );
    }

    protected void waitForResult( Action action ) {
        waitingActions.add( action );

        checkStatus();
    }

    protected void resultArrived( Action action ) {
        //removing only THE SAME request, not equal
        for ( int i = 0; i < waitingActions.size(); i++ ) {
            Action waitingRequest = waitingActions.get( i );
            if ( waitingRequest == action ) {
                waitingActions.remove( i );
                break;
            }
        }

        checkStatus();
    }

    protected void checkStatus() {
        if ( waitingActions.isEmpty() ) {
            getDisplay().stopProcessing();
        } else {
            getDisplay().startProcessing();
        }
    }

    protected class AsyncCallbackWrapper<A extends Action<R>, R extends Result> implements AsyncCallback<R> {
        private final A action;

        private final AsyncCallback<R> asyncCallback;

        public AsyncCallbackWrapper( A action, AsyncCallback<R> asyncCallback ) {
            this.action = action;
            this.asyncCallback = asyncCallback;
        }

        @Override
        public void onSuccess( R result ) {
            try {
                asyncCallback.onSuccess( result );
            } finally {
                resultArrived( action );
            }
        }

        @Override
        public void onFailure( Throwable throwable ) {
            try {
                asyncCallback.onFailure( throwable );
            } finally {
                resultArrived( action );
            }
        }
    }
}
