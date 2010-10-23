package com.sokolenko.jcrconsole.client.view;

import com.extjs.gxt.ui.client.data.DataProxy;
import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.Loader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.ModelIconProvider;
import com.extjs.gxt.ui.client.data.ModelStringProvider;
import com.extjs.gxt.ui.client.data.TreeLoader;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.Observable;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.SelectionProvider;
import com.extjs.gxt.ui.client.event.TreePanelEvent;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sokolenko.jcrconsole.client.model.NodeInfoTreeModel;
import com.sokolenko.jcrconsole.client.presenter.NodesTreeLoader;
import com.sokolenko.jcrconsole.client.presenter.NodesTreePresenter;
import com.sokolenko.jcrconsole.client.util.Assert;
import com.sokolenko.jcrconsole.client.util.DataProxyWrapper;

import java.util.List;

/**
 * @author Anatoliy Sokolenko
 */
@Singleton
public class NodesTreeView extends ContentPanel implements NodesTreePresenter.Display {
    private final ViewResources viewResources;

    private final ModelStringProvider<NodeInfoTreeModel> treeLabelProvider;

    private final ModelIconProvider<NodeInfoTreeModel> treeIconProvider;

    private TreeLoader<NodeInfoTreeModel> loader;

    private final DataProxyWrapper<List<NodeInfoTreeModel>> dataProxyWrapper = new DataProxyWrapper<List<NodeInfoTreeModel>>();

    private TreePanel<NodeInfoTreeModel> tree;

    private ToolBar toolBar;


    @Inject
    public NodesTreeView( ViewResources viewResources, ModelStringProvider<NodeInfoTreeModel> treeLabelProvider,
                          ModelIconProvider<NodeInfoTreeModel> treeIconProvider ) {
        Assert.notNull( viewResources, "viewResources" );

        this.viewResources = viewResources;
        this.treeLabelProvider = treeLabelProvider;
        this.treeIconProvider = treeIconProvider;

        setHeading( "Workspace Data" );
        setLayout( new FitLayout() );

        initializeToolBar();

        initializeTreePanel();
    }

    protected void initializeToolBar() {
        toolBar = new ToolBar();

        AbstractImagePrototype refreshIcon = AbstractImagePrototype.create( viewResources.refreshIcon() );

        Button refreshButton = new Button();
        refreshButton.setIcon( refreshIcon );
        refreshButton.addSelectionListener( new RefreshButtonListener() );
        toolBar.add( refreshButton );

        setTopComponent( toolBar );
    }

    protected void initializeTreePanel() {
        loader = new NodesTreeLoader( dataProxyWrapper );

        AfterLoadListener afterLoadListener = new AfterLoadListener();

        loader.addListener( Loader.BeforeLoad, new BeforeLoadListener() );
        loader.addListener( Loader.Load, afterLoadListener );
        loader.addListener( Loader.LoadException, afterLoadListener );

        TreeStore<NodeInfoTreeModel> store = new TreeStore<NodeInfoTreeModel>( loader );

        tree = new NotExpandableTreePanel<NodeInfoTreeModel>( store );
        tree.setDisplayProperty( NodeInfoTreeModel.NODE_NAME );
        tree.setIconProvider( treeIconProvider );
        tree.setLabelProvider( treeLabelProvider );

        add( tree );
    }

    @Override
    public SelectionProvider<NodeInfoTreeModel> getTreeSelectionProvider() {
        return tree.getSelectionModel();
    }

    @Override
    public void setDataProxy( DataProxy<List<NodeInfoTreeModel>> dataProxy ) {
        dataProxyWrapper.setDataProxy( dataProxy );
    }

    @Override
    public DataProxy<List<NodeInfoTreeModel>> getDataProxy() {
        return dataProxyWrapper.getDataProxy();
    }

    @Override
    public Observable getTreeObservable() {
        return tree;
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
            loader.load();
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

    protected class NotExpandableTreePanel<M extends ModelData> extends TreePanel<M> {

        /**
         * Creates a new tree panel.
         *
         * @param treeStore the tree store
         */
        public NotExpandableTreePanel( TreeStore treeStore ) {
            super( treeStore );
        }

        @Override
        protected void onDoubleClick( TreePanelEvent tpe ) {
            //do not use double click for expand
        }
    }
}
