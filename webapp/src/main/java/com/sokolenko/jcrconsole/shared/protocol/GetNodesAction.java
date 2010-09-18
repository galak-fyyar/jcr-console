package com.sokolenko.jcrconsole.shared.protocol;

import net.customware.gwt.dispatch.shared.Action;

/**
 * @author Anatoliy Sokolenko
 */
public class GetNodesAction implements Action<GetNodesResult> {
    private String path;

    private boolean selfNode;

    private long bucketStart = 0;

    private long bucketLength = Integer.MAX_VALUE;

    public GetNodesAction() {
    }

    public GetNodesAction( String path, boolean selfNode ) {
        this.path = path;
        this.selfNode = selfNode;
    }

    public String getPath() {
        return path;
    }

    public void setPath( String path ) {
        this.path = path;
    }

    public boolean isSelfNode() {
        return selfNode;
    }

    public void setSelfNode( boolean selfNode ) {
        this.selfNode = selfNode;
    }

    public long getBucketStart() {
        return bucketStart;
    }

    public void setBucketStart( long bucketStart ) {
        this.bucketStart = bucketStart;
    }

    public long getBucketLength() {
        return bucketLength;
    }

    public void setBucketLength( long bucketLength ) {
        this.bucketLength = bucketLength;
    }
}
