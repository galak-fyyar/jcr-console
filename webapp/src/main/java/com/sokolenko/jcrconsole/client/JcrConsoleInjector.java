package com.sokolenko.jcrconsole.client;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.sokolenko.jcrconsole.client.core.DispatchModule;
import com.sokolenko.jcrconsole.client.presenter.MainPresenter;
import net.customware.gwt.dispatch.client.standard.StandardDispatchServiceAsync;

/**
 * @author Anatoliy Sokolenko
 */
@GinModules( { DispatchModule.class, JcrConsoleModule.class } )
public interface JcrConsoleInjector extends Ginjector {
    MainPresenter getMainPresenter();

    StandardDispatchServiceAsync getStandardDispatchService();
}
