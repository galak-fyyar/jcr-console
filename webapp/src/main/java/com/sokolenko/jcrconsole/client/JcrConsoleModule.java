package com.sokolenko.jcrconsole.client;

import com.extjs.gxt.ui.client.data.ModelIconProvider;
import com.extjs.gxt.ui.client.data.ModelStringProvider;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.sokolenko.jcrconsole.client.model.NodeInfoTreeModel;
import com.sokolenko.jcrconsole.client.model.NodeInfoTreeModelIconProvider;
import com.sokolenko.jcrconsole.client.model.NodeIntoTreeLabelProvider;
import com.sokolenko.jcrconsole.client.presenter.MainPresenter;
import com.sokolenko.jcrconsole.client.presenter.NodesTreePresenter;
import com.sokolenko.jcrconsole.client.presenter.ScriptConsolePresenter;
import com.sokolenko.jcrconsole.client.view.MainView;
import com.sokolenko.jcrconsole.client.view.NodesTreeView;
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
        bindConstant().annotatedWith( Names.named( "nodesBucketSize" ) ).to( 50 );

        bind( EventBus.class ).to( DefaultEventBus.class ).in( Singleton.class );

        bindPresenter( MainPresenter.class, MainPresenter.Display.class, MainView.class );
        bindPresenter( NodesTreePresenter.class, NodesTreePresenter.Display.class, NodesTreeView.class );
        bindPresenter( ScriptConsolePresenter.class, ScriptConsolePresenter.Display.class, ScriptConsoleView.class );

        bind( new TypeLiteral<ModelStringProvider<NodeInfoTreeModel>>() {
        } )
                .to( NodeIntoTreeLabelProvider.class );
        bind( new TypeLiteral<ModelIconProvider<NodeInfoTreeModel>>() {
        } )
                .to( NodeInfoTreeModelIconProvider.class );
    }
}
