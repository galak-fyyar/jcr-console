package com.sokolenko.jcrconsole.client.presenter;

import com.allen_sauer.gwt.log.client.Log;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.Observable;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sokolenko.jcrconsole.client.core.DataLoadingPresenter;
import com.sokolenko.jcrconsole.client.core.SingletonActionWrapper;
import com.sokolenko.jcrconsole.client.event.ExecuteScriptEvent;
import com.sokolenko.jcrconsole.client.util.StringUtils;
import com.sokolenko.jcrconsole.shared.protocol.ScriptExecuteAction;
import com.sokolenko.jcrconsole.shared.protocol.ScriptExecuteResult;
import net.customware.gwt.dispatch.client.standard.StandardDispatchServiceAsync;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.Place;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;

/**
 * @author Anatoliy Sokolenko
 */
@Singleton
public class ScriptConsolePresenter extends DataLoadingPresenter<ScriptConsolePresenter.Display>
        implements Listener<ExecuteScriptEvent> {
    protected static final String SCRIPT_EXECUTE_SPACE = StringUtils.random( 10 );

    @Inject
    public ScriptConsolePresenter( Display display, EventBus eventBus, StandardDispatchServiceAsync dispatchAsync ) {
        super( display, eventBus, dispatchAsync );

        bind();
    }

    @Override
    public void handleEvent( ExecuteScriptEvent be ) {
        String scriptText = getDisplay().getScriptText();
        ScriptExecuteAction action = new ScriptExecuteAction( scriptText );
        SingletonActionWrapper actionWrapper = new SingletonActionWrapper( SCRIPT_EXECUTE_SPACE, action );

        send( actionWrapper, new AsyncCallback<ScriptExecuteResult>() {
            @Override
            public void onSuccess( ScriptExecuteResult scriptExecuteResult ) {
                //TODO
            }

            @Override
            public void onFailure( Throwable throwable ) {
                //TODO
                Log.error( "Unable perform request", throwable );
            }
        } );
    }

    @Override
    protected void onBind() {
        getDisplay().getViewObservable().addListener( ExecuteScriptEvent.EVENT_TYPE, this );
    }

    @Override
    protected void onUnbind() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Place getPlace() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void onPlaceRequest( PlaceRequest placeRequest ) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void refreshDisplay() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void revealDisplay() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public static interface Display extends WidgetDisplay {
        void setScriptText( String scriptText );

        String getScriptText();

        Observable getViewObservable();
    }
}
