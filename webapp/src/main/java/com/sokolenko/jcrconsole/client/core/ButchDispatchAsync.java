package com.sokolenko.jcrconsole.client.core;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sokolenko.jcrconsole.client.util.Assert;
import com.sokolenko.jcrconsole.client.util.RandomStringUtils;
import com.sokolenko.jcrconsole.shared.protocol.butch.ButchAction;
import com.sokolenko.jcrconsole.shared.protocol.butch.ButchResult;
import com.sokolenko.jcrconsole.shared.protocol.butch.FailureResult;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.ExceptionHandler;
import net.customware.gwt.dispatch.client.standard.StandardDispatchServiceAsync;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Anatoliy Sokolenko
 */
@Singleton
public class ButchDispatchAsync implements DispatchAsync {
    protected static final int BEGINNING_KEY_LENGTH = 3;

    private final StandardDispatchServiceAsync standardDispatchServiceAsync;

    private final ExceptionHandler exceptionHandler;

    private final Map<Action, AsyncCallback> pendingActions = new HashMap<Action, AsyncCallback>();

    private boolean commandInitialize = false;

    @Inject
    public ButchDispatchAsync( StandardDispatchServiceAsync standardDispatchServiceAsync, ExceptionHandler exceptionHandler ) {
        Assert.notNull( standardDispatchServiceAsync, "standardDispatchServiceAsync" );
        Assert.notNull( exceptionHandler, "exceptionHandler" );

        this.standardDispatchServiceAsync = standardDispatchServiceAsync;
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public <A extends Action<R>, R extends Result> void execute( A action, AsyncCallback<R> callback ) {
        addPendingAction( action, callback );
    }

    protected void addPendingAction( Action action, AsyncCallback callback ) {
        pendingActions.put( action, callback );

        if ( !commandInitialize ) {
            DeferredCommand.addCommand( new Command() {
                @Override
                public void execute() {
                    commandInitialize = false;

                    sendActions();
                }
            } );

            commandInitialize = true;
        }
    }

    protected void sendActions() {
        Iterator<Map.Entry<Action, AsyncCallback>> pendingActionsIt = pendingActions.entrySet().iterator();

        Action sendableAction = null;
        AsyncCallback sendableCallback = null;

        if ( pendingActions.size() > 1 ) {
            Map<String, Action> actionsToSent = new HashMap<String, Action>();
            Map<String, AsyncCallback> waitingCallbacks = new HashMap<String, AsyncCallback>();

            while ( pendingActionsIt.hasNext() ) {
                Map.Entry<Action, AsyncCallback> pendingActionEntry = pendingActionsIt.next();

                Action action = pendingActionEntry.getKey();
                AsyncCallback callback = pendingActionEntry.getValue();

                String key = generateUniqueKey( waitingCallbacks );

                actionsToSent.put( key, action );
                waitingCallbacks.put( key, callback );

                pendingActionsIt.remove();
            }

            sendableAction = new ButchAction( actionsToSent );
            sendableCallback = new ButchResultCallback( waitingCallbacks );
        } else if ( pendingActions.size() == 1 ) {
            Map.Entry<Action, AsyncCallback> pendingActionEntry = pendingActionsIt.next();
            pendingActionsIt.remove();

            sendableAction = pendingActionEntry.getKey();
            sendableCallback = new SingleResultCallback( pendingActionEntry.getValue() );
        }

        if ( sendableAction != null ) {
            standardDispatchServiceAsync.execute( sendableAction, sendableCallback );
        } else {
            Log.warn( "Action to sent was not found" );
        }
    }

    protected String generateUniqueKey( Map<String, AsyncCallback> waitingCallbacks ) {
        for ( int keyLength = BEGINNING_KEY_LENGTH; keyLength < Integer.MAX_VALUE; keyLength++ ) {
            String key = RandomStringUtils.randomAlphabetic( keyLength );

            if ( !waitingCallbacks.containsKey( key ) ) {
                return key;
            }
        }

        throw new IllegalStateException( "Unable to generate key of appropriate length" );
    }

    @SuppressWarnings( { "ThrowableResultOfMethodCallIgnored", "unchecked" } )
    protected void onSuccess( AsyncCallback callback, Result result ) {
        try {
            if ( result instanceof FailureResult ) {
                FailureResult failureResult = ( FailureResult ) result;

                onFailure( callback, failureResult.getError() );
            } else {
                callback.onSuccess( result );
            }
        } catch ( Exception e ) {
            Log.error( "Unable to execute callback.onSuccess() ", e );
        }
    }

    protected void onFailure( AsyncCallback callback, Throwable throwable ) {
        if ( exceptionHandler != null && exceptionHandler.onFailure( throwable ) == ExceptionHandler.Status.STOP ) {
            return;
        }

        try {
            callback.onFailure( throwable );
        } catch ( Exception e ) {
            Log.error( "Unable to execute callback.onFailure() " + callback );
        }
    }

    protected class SingleResultCallback implements AsyncCallback<Result> {
        private final AsyncCallback originalCallback;

        public SingleResultCallback( AsyncCallback originalCallback ) {
            Assert.notNull( originalCallback, "originalCallback" );

            this.originalCallback = originalCallback;
        }

        @Override
        public void onSuccess( Result result ) {
            ButchDispatchAsync.this.onSuccess( originalCallback, result );
        }


        @Override
        public void onFailure( Throwable throwable ) {
            ButchDispatchAsync.this.onFailure( originalCallback, throwable );
        }
    }

    protected class ButchResultCallback implements AsyncCallback<Result> {
        private final Map<String, AsyncCallback> waitingCallbacks;

        public ButchResultCallback( Map<String, AsyncCallback> waitingCallbacks ) {
            Assert.notNull( waitingCallbacks, "waitingCallbacks" );

            this.waitingCallbacks = waitingCallbacks;
        }

        @Override
        public void onSuccess( Result simpleResult ) {
            ButchResult butchResult = ( ButchResult ) simpleResult;

            for ( Map.Entry<String, Result> resultEntry : butchResult.getResults().entrySet() ) {
                String key = resultEntry.getKey();
                Result result = resultEntry.getValue();

                AsyncCallback callback = waitingCallbacks.get( key );
                if ( callback != null ) {
                    ButchDispatchAsync.this.onSuccess( callback, result );
                } else {
                    Log.warn( "Callback with specified key was not found " + key );
                }
            }
        }

        @Override
        public void onFailure( Throwable throwable ) {
            for ( AsyncCallback callback : waitingCallbacks.values() ) {
                ButchDispatchAsync.this.onFailure( callback, throwable );
            }
        }
    }
}
