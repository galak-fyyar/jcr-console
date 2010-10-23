package com.sokolenko.jcrconsole.client.model;

import com.extjs.gxt.ui.client.data.BaseTreeModel;
import com.sokolenko.jcrconsole.shared.protocol.NodeInfo;

/**
 * @author Anatoliy Sokolenko
 */
public class NodeInfoTreeModel extends BaseTreeModel {
    public static final String NODE_NAME = "nodeName";

    public static final String NODE_TYPE_NAME = "nodeTypeName";

    public static final String TOTAL_CHILDREN_COUNT = "totalChildrenCount";

    public static final String NODE_PATH = "nodePath";

    public static final String NODE_INFO_INSTANCE = "nodeInfoInstance";

    public NodeInfoTreeModel( NodeInfo nodeInfo ) {
        set( NODE_NAME, nodeInfo.getNodeName() );
        set( NODE_TYPE_NAME, nodeInfo.getNodeTypeName() );
        set( TOTAL_CHILDREN_COUNT, nodeInfo.getTotalChildrenCount() );
        set( NODE_PATH, nodeInfo.getNodePath() );

        set( NODE_INFO_INSTANCE, nodeInfo );
    }
}
