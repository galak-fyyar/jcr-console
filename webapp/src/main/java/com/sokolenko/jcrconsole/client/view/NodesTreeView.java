package com.sokolenko.jcrconsole.client.view;

import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.Loader;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sokolenko.jcrconsole.client.model.NodeInfoTreeModel;
import com.sokolenko.jcrconsole.client.model.NodeIntoTreeLabelProvider;
import com.sokolenko.jcrconsole.client.presenter.NodesTreePresenter;
import com.sokolenko.jcrconsole.client.util.Assert;

/**
 * @author Anatoliy Sokolenko
 */
@Singleton
public class NodesTreeView extends ContentPanel implements NodesTreePresenter.Display {
    private final ViewResources viewResources;

    private final BeforeLoadListener beforeLoadListener = new BeforeLoadListener();

    private final AfterLoadListener afterLoadListener = new AfterLoadListener();

    private TreeStore<NodeInfoTreeModel> store;

    private ToolBar toolBar;

    @Inject
    public NodesTreeView( ViewResources viewResources ) {
        Assert.notNull( viewResources, "viewResources" );

        this.viewResources = viewResources;

        initializeToolBar();
    }

    @Override
    protected void onRender( Element parent, int pos ) {
        Assert.notNullState( store, "store" );

        super.onRender( parent, pos );

        setHeading( "Workspace Data" );
        setLayout( new FitLayout() );

        TreePanel<NodeInfoTreeModel> tree = new TreePanel<NodeInfoTreeModel>( store );
        tree.setDisplayProperty( NodeInfoTreeModel.NODE_NAME );
        tree.setLabelProvider( new NodeIntoTreeLabelProvider() );
        add( tree );
    }

    protected void initializeToolBar() {
        toolBar = new ToolBar();

        AbstractImagePrototype refreshIcon = AbstractImagePrototype.create( viewResources.refreshIcon() );

        Button refreshButton = new Button( "Refresh" );
        refreshButton.setIcon( refreshIcon );
        refreshButton.addSelectionListener( new RefreshButtonListener() );
        toolBar.add( refreshButton );

        setTopComponent( toolBar );
    }

    public TreeStore<NodeInfoTreeModel> getStore() {
        return store;
    }

    public void setStore( TreeStore<NodeInfoTreeModel> store ) {
        if ( this.store != null ) {
            this.store.getLoader().removeListener( Loader.BeforeLoad, beforeLoadListener );
            this.store.getLoader().removeListener( Loader.Load, afterLoadListener );
        }

        this.store = store;

        if ( this.store != null ) {
            this.store.getLoader().addListener( Loader.BeforeLoad, beforeLoadListener );
            this.store.getLoader().addListener( Loader.Load, afterLoadListener );
        }
    }

    @Override
    public Widget asWidget() {
        return this;
    }

    @Override
    public void startProcessing() {

    }

    @Override
    public void stopProcessing() {

    }

    protected class RefreshButtonListener extends SelectionListener<ButtonEvent> {
        @Override
        public void componentSelected( ButtonEvent ce ) {
            if ( store != null ) {
                store.getLoader().load();
            }
        }
    }

    protected class BeforeLoadListener implements Listener<LoadEvent> {
        @Override
        public void handleEvent( LoadEvent be ) {
            if ( toolBar != null ) {
                for ( Component item : toolBar.getItems() ) {
                    item.setEnabled( false );
                }
            }
        }
    }

    protected class AfterLoadListener implements Listener<LoadEvent> {
        @Override
        public void handleEvent( LoadEvent be ) {
            if ( toolBar != null ) {
                for ( Component item : toolBar.getItems() ) {
                    item.setEnabled( true );
                }
            }
        }
    }
}
