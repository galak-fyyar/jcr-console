package com.sokolenko.jcrconsole.client.presenter;

import com.extjs.gxt.ui.client.data.BaseTreeLoader;
import com.extjs.gxt.ui.client.data.DataProxy;
import com.google.inject.Inject;
import com.sokolenko.jcrconsole.client.model.NodeInfoTreeModel;

import java.util.List;

public class NodesTreeLoader extends BaseTreeLoader<NodeInfoTreeModel> {
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