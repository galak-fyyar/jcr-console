package com.sokolenko.jcrconsole.client.model;

import com.sokolenko.jcrconsole.shared.protocol.NodeInfo;

/**
 * @author Anatoliy Sokolenko
 */
public class NodesBucketTreeModel extends NodeInfoTreeModel {
    public static final String BUCKET_START = "bunchStart";

    public static final String BUCKET_LENGTH = "bunchLength";

    public NodesBucketTreeModel( NodeInfo nodeInfo, long start, long length ) {
        super( nodeInfo );

        set( BUCKET_START, start );
        set( BUCKET_LENGTH, length );
    }
}
