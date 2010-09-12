package com.sokolenko.jcrconsole.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.RootPanel;
import com.sokolenko.jcrconsole.client.presenter.MainPresenter;

/**
 * @author Anatoliy Sokolenko
 */
public class JcrConsoleEntryPoint implements EntryPoint {
    @Override
    public void onModuleLoad() {
        DeferredCommand.addCommand( new Command() {
            @Override
            public void execute() {
                JcrConsoleInjector injector = GWT.create( JcrConsoleInjector.class );

                ServiceDefTarget endpoint = ( ServiceDefTarget ) injector.getStandardDispatchService();
                String moduleRelativeUrl = GWT.getModuleBaseURL() + "../gwt-rpc/dispatch";
                endpoint.setServiceEntryPoint( moduleRelativeUrl );

                MainPresenter mainPresenter = injector.getMainPresenter();
                RootPanel.get().add( mainPresenter.getDisplay().asWidget() );
            }
        } );
    }
}
