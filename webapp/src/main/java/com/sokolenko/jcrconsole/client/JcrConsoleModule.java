package com.sokolenko.jcrconsole.client;

import com.sokolenko.jcrconsole.client.presenter.MainPresenter;
import com.sokolenko.jcrconsole.client.presenter.ScriptConsolePresenter;
import com.sokolenko.jcrconsole.client.view.MainView;
import com.sokolenko.jcrconsole.client.view.ScriptConsoleView;
import net.customware.gwt.presenter.client.DefaultEventBus;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.gin.AbstractPresenterModule;

/**
 * @author Anatoliy Sokolenko
 */
public class JcrConsoleModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bind( EventBus.class ).to( DefaultEventBus.class );

        bindPresenter( MainPresenter.class, MainPresenter.Display.class, MainView.class );
        bindPresenter( ScriptConsolePresenter.class, ScriptConsolePresenter.Display.class, ScriptConsoleView.class );
    }
}
