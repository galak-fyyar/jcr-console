package com.sokolenko.jcrconsole.client.core;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sokolenko.jcrconsole.client.util.Assert;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Anatoliy Sokolenko
 */
public abstract class DataLoadingPresenter<D extends WidgetDisplay> extends WidgetPresenter<D> {
    private final DispatchAsync dispatchAsync;

    private final Map<ActionKey, CheckingResultHandler> waitingActions = new HashMap<ActionKey, CheckingResultHandler>();

    public DataLoadingPresenter( D display, EventBus eventBus, DispatchAsync dispatchAsync ) {
        super( display, eventBus );

        this.dispatchAsync = dispatchAsync;
    }

    @SuppressWarnings( { "unchecked" } )
    protected final void send( Action action, AsyncCallback<? extends Result> asyncCallback ) {
        CheckingResultHandler callback = new CheckingResultHandler( action, ( AsyncCallback<Result> ) asyncCallback );

        if ( action instanceof SingletonActionWrapper ) {
            Action realAction = ( ( SingletonActionWrapper ) action ).getAction();

            dispatchAsync.execute( realAction, callback );
        } else {
            dispatchAsync.execute( action, callback );
        }

        waitForResult( action, callback );

        suppressCrossingRequests( action );

        checkStatus();
    }

    protected final void waitForResult( Action action, CheckingResultHandler asyncCallback ) {
        waitingActions.put( new ActionKey( action ), asyncCallback );
    }

    protected final void suppressCrossingRequests( Action action ) {
        if ( action instanceof SingletonAction ) {
            SingletonAction singletonRequest = ( SingletonAction ) action;

            for ( Map.Entry<ActionKey, CheckingResultHandler> waitingActionResult : waitingActions.entrySet() ) {
                ActionKey waitingActionKey = waitingActionResult.getKey();
                Action waitingAction = waitingActionKey.getAction();
                CheckingResultHandler waitingResult = waitingActionResult.getValue();

                if ( waitingAction instanceof SingletonAction ) {
                    SingletonAction waitingSingletonRequest = ( SingletonAction ) waitingAction;

                    if ( singletonRequest != waitingSingletonRequest
                            && singletonRequest.getSpace().equals( waitingSingletonRequest.getSpace() ) ) {
                        waitingActions.remove( waitingActionKey );
                        waitingResult.cancel();
                    }
                }
            }
        }
    }

    protected final void resultArrived( Action action ) {
        waitingActions.remove( new ActionKey( action ) );

        checkStatus();
    }

    protected final void checkStatus() {
        if ( waitingActions.isEmpty() ) {
            getDisplay().stopProcessing();
        } else {
            getDisplay().startProcessing();
        }
    }

    protected class CheckingResultHandler implements AsyncCallback<Result> {
        private final Action action;
        private final AsyncCallback<Result> asyncCallback;

        private boolean canceled;

        public CheckingResultHandler( Action action, AsyncCallback<Result> asyncCallback ) {
            Assert.notNull( action, "action" );
            Assert.notNull( asyncCallback, "asyncCallback" );

            this.action = action;
            this.asyncCallback = asyncCallback;
        }

        @Override
        public void onSuccess( Result r ) {
            if ( canceled ) {
                return;
            }

            try {
                asyncCallback.onSuccess( r );
            } finally {
                resultArrived( action );
            }
        }

        @Override
        public void onFailure( Throwable throwable ) {
            if ( canceled ) {
                return;
            }

            try {
                asyncCallback.onFailure( throwable );
            } finally {
                resultArrived( action );
            }
        }

        public void cancel() {
            canceled = true;
        }

        public boolean isCanceled() {
            return canceled;
        }
    }

    protected class ActionKey {
        private Action action;

        public ActionKey( Action action ) {
            Assert.notNull( action, "action" );

            this.action = action;
        }

        public Action getAction() {
            return action;
        }

        @Override
        @SuppressWarnings( { "unchecked" } )
        public boolean equals( Object o ) {
            if ( this == o ) return true;
            if ( o == null || getClass() != o.getClass() ) return false;

            ActionKey actionKey = ( ActionKey ) o;

            if ( !action.equals( actionKey.action ) ) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return action.hashCode();
        }
    }
}
