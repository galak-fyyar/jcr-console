package com.sokolenko.jcrconsole.client.presenter;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.sokolenko.jcrconsole.client.core.DataLoadingPresenter;
import com.sokolenko.jcrconsole.client.model.PropertyDetailModel;
import com.sokolenko.jcrconsole.shared.protocol.GetNodeDataAction;
import com.sokolenko.jcrconsole.shared.protocol.GetNodeDataResult;
import com.sokolenko.jcrconsole.shared.protocol.NodeData;
import com.sokolenko.jcrconsole.shared.protocol.NodeInfo;
import com.sokolenko.jcrconsole.shared.protocol.PropertyData;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.Place;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anatoliy Sokolenko
 */
public class NodeDataPresenter extends DataLoadingPresenter<NodeDataPresenter.Display> {
    private NodeInfo nodeInfo;

    @Inject
    public NodeDataPresenter( Display display, EventBus eventBus, DispatchAsync dispatchAsync ) {
        super( display, eventBus, dispatchAsync );
    }

    public NodeInfo getNodeInfo() {
        return nodeInfo;
    }

    public void setNodeInfo( NodeInfo nodeInfo ) {
        this.nodeInfo = nodeInfo;

        String nodePath = nodeInfo.getNodePath();
        getDisplay().setTabTitle( nodePath );
        send( new GetNodeDataAction( nodePath ), new NodeDetailsCallback() );
    }

    @Override
    public Place getPlace() {
        return null;     //TODO
    }

    @Override
    public void refreshDisplay() {
        //TODO
    }

    @Override
    public void revealDisplay() {
        //TODO
    }

    @Override
    protected void onBind() {
        //TODO
    }

    @Override
    protected void onUnbind() {
        //TODO
    }

    @Override
    protected void onPlaceRequest( PlaceRequest request ) {
        //TODO
    }


    protected void setNodeData( NodeData nodeData ) {
        List<PropertyDetailModel> propertyDetailModels = new ArrayList<PropertyDetailModel>();

        if ( nodeData != null ) {

            List<PropertyData> propertyDatas = nodeData.getPropertyDatas();
            if ( propertyDatas != null ) {
                for ( PropertyData propertyData : propertyDatas ) {
                    propertyDetailModels.add( buildPropertyDetailModel( propertyData ) );
                }
            }
        }

        getDisplay().setPropertiesModels( propertyDetailModels );
    }

    protected PropertyDetailModel buildPropertyDetailModel( PropertyData propertyData ) {
        PropertyDetailModel result = new PropertyDetailModel();
        result.set( PropertyDetailModel.PROPERTY_NAME, propertyData.getName() );
        result.set( PropertyDetailModel.PROPERTY_VALUE, propertyData.getValues().size() > 0 ? propertyData.getValues().get( 0 ) : null );

        //TODO

        return result;
    }

    public static interface Display extends WidgetDisplay {
        void setTabTitle( String title );

        void setNodePath( String nodePath );

        void setNodeTypeName( String nodeTypaName );

        void setPropertiesModels( List<PropertyDetailModel> models );
    }

    protected class NodeDetailsCallback implements AsyncCallback<GetNodeDataResult> {
        @Override
        public void onSuccess( GetNodeDataResult result ) {
            if ( result != null ) {
                NodeData nodeDatas = result.getNodeData();
                setNodeData( nodeDatas );
            } else {
                setNodeData( null );
            }
        }

        @Override
        public void onFailure( Throwable caught ) {
            //TODO
        }
    }
}
