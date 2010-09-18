package com.sokolenko.jcrconsole.client.model;

import com.sokolenko.jcrconsole.shared.protocol.NodeInfo;

/**
 * @author Anatoliy Sokolenko
 */
public class NodesBucketTreeModel extends NodeInfoTreeModel {
    public static final String BUNCH_START = "bunchStart";

    public static final String BUNCH_LENGTH = "bunchLength";

    public NodesBucketTreeModel( NodeInfo nodeInfo, long start, long length ) {
        super( nodeInfo );

        set( BUNCH_START, start );
        set( BUNCH_LENGTH, length );
    }
}
