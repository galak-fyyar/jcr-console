package com.sokolenko.jcrconsole.client.presenter;

import com.extjs.gxt.ui.client.data.BaseTreeLoader;
import com.extjs.gxt.ui.client.data.DataProxy;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.sokolenko.jcrconsole.client.model.NodeInfoTreeModel;
import com.sokolenko.jcrconsole.client.model.NodesBucketTreeModel;
import com.sokolenko.jcrconsole.client.util.Assert;
import com.sokolenko.jcrconsole.shared.protocol.GetNodesAction;
import com.sokolenko.jcrconsole.shared.protocol.GetNodesResult;
import com.sokolenko.jcrconsole.shared.protocol.NodeInfo;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.Place;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anatoliy Sokolenko
 */
@Singleton
public class NodesTreePresenter extends WidgetPresenter<NodesTreePresenter.Display> {
    private final DispatchAsync dispatchAsync;

    @Inject
    @Named( "nodesBucketSize" )
    private int nodesBucketSize;

    @Inject
    public NodesTreePresenter( Display display, EventBus eventBus, DispatchAsync dispatchAsync ) {
        super( display, eventBus );

        Assert.notNull( dispatchAsync, "dispatchAsync" );

        this.dispatchAsync = dispatchAsync;

        bind();
    }

    @Override
    protected void onBind() {
        NodesTreeRpcProxy rpcProxy = new NodesTreeRpcProxy();

        NodesTreeLoader loader = new NodesTreeLoader( rpcProxy );

        TreeStore<NodeInfoTreeModel> store = new TreeStore<NodeInfoTreeModel>( loader );

        getDisplay().setStore( store );
    }

    @Override
    protected void onUnbind() {

    }

    @Override
    public Place getPlace() {
        return null;
    }

    @Override
    protected void onPlaceRequest( PlaceRequest placeRequest ) {

    }

    @Override
    public void refreshDisplay() {

    }

    @Override
    public void revealDisplay() {

    }

    protected List<NodeInfoTreeModel> convert( GetNodesResult getNodeTreeResult ) {
        List<NodeInfoTreeModel> nodeInfoTreeModels = new ArrayList<NodeInfoTreeModel>();

        List<NodeInfo> nodeInfos = getNodeTreeResult.getNodeInfos();

        if ( nodeInfos != null ) {
            for ( NodeInfo nodeInfo : nodeInfos ) {
                NodeInfoTreeModel nodeInfoTreeModel = new NodeInfoTreeModel( nodeInfo );

                long totalChildrenCount = nodeInfo.getTotalChildrenCount();
                if ( totalChildrenCount > nodesBucketSize ) {
                    int numberOfBuckets = ( int ) Math.ceil( totalChildrenCount / ( ( double ) nodesBucketSize ) );

                    for ( int bucketId = 0; bucketId < numberOfBuckets; bucketId++ ) {
                        long bucketStart = bucketId * nodesBucketSize;
                        long bucketLength = ( ( ( bucketId + 1 ) * nodesBucketSize ) > totalChildrenCount )
                                ? totalChildrenCount - bucketId * nodesBucketSize
                                : nodesBucketSize;

                        NodesBucketTreeModel nodesBucketTreeModel = new NodesBucketTreeModel( nodeInfo, bucketStart, bucketLength );
                        nodeInfoTreeModel.add( nodesBucketTreeModel );
                    }
                }

                nodeInfoTreeModels.add( nodeInfoTreeModel );
            }
        }

        return nodeInfoTreeModels;
    }

    public static interface Display extends WidgetDisplay {
        public TreeStore<NodeInfoTreeModel> getStore();

        public void setStore( TreeStore<NodeInfoTreeModel> store );

    }

    protected class NodesTreeLoader extends BaseTreeLoader<NodeInfoTreeModel> {
        public NodesTreeLoader( DataProxy proxy ) {
            super( proxy );
        }

        @Override
        protected void loadData( Object config ) {
            if ( config instanceof NodeInfoTreeModel ) {
                NodeInfoTreeModel nodeInfoTreeModel = ( NodeInfoTreeModel ) config;
                List children = nodeInfoTreeModel.getChildren();

                if ( children.size() > 0 ) {
                    onLoadSuccess( config, children );
                    return;
                }
            }

            super.loadData( config );
        }

        @Override
        public boolean hasChildren( NodeInfoTreeModel parent ) {
            long totalChildrenCount = ( Long ) parent.get( NodeInfoTreeModel.TOTAL_CHILDREN_COUNT );

            return totalChildrenCount > 0;
        }
    }

    protected class NodesTreeRpcProxy extends RpcProxy<List<NodeInfoTreeModel>> {
        @Override
        protected void load( Object loadConfig, AsyncCallback<List<NodeInfoTreeModel>> listAsyncCallback ) {
            GetNodesAction getNodeTreeAction;
            NodeInfoTreeModel nodeInfoTreeModels = ( NodeInfoTreeModel ) loadConfig;

            if ( nodeInfoTreeModels != null ) {
                String nodePath = nodeInfoTreeModels.get( NodeInfoTreeModel.NODE_PATH );

                getNodeTreeAction = new GetNodesAction( nodePath, false );
                if ( loadConfig instanceof NodesBucketTreeModel ) {
                    NodesBucketTreeModel nodesBucketTreeModel = ( NodesBucketTreeModel ) loadConfig;

                    long bunchLength = ( Long ) nodesBucketTreeModel.get( NodesBucketTreeModel.BUCKET_LENGTH );
                    long bunchStart = ( Long ) nodesBucketTreeModel.get( NodesBucketTreeModel.BUCKET_START );

                    getNodeTreeAction.setBucketStart( bunchStart );
                    getNodeTreeAction.setBucketLength( bunchLength );
                }
            } else {
                getNodeTreeAction = new GetNodesAction( "/", true );
            }

            dispatchAsync.execute( getNodeTreeAction, new NodesTreeAsyncCallback( listAsyncCallback ) );
        }
    }

    protected class NodesTreeAsyncCallback implements AsyncCallback<GetNodesResult> {
        private AsyncCallback<List<NodeInfoTreeModel>> listAsyncCallback;

        public NodesTreeAsyncCallback( AsyncCallback<List<NodeInfoTreeModel>> listAsyncCallback ) {
            Assert.notNull( listAsyncCallback, "listAsyncCallback" );

            this.listAsyncCallback = listAsyncCallback;
        }

        @Override
        public void onSuccess( GetNodesResult getNodeTreeResult ) {
            List<NodeInfoTreeModel> nodeInfoTreeModels = convert( getNodeTreeResult );

            listAsyncCallback.onSuccess( nodeInfoTreeModels );
        }

        @Override
        public void onFailure( Throwable throwable ) {
            listAsyncCallback.onFailure( throwable );
        }
    }
}
