package com.sokolenko.jcrconsole.shared.protocol;

import net.customware.gwt.dispatch.shared.Result;

/**
 * @author Anatoliy Sokolenko
 */
public class GetNodeDataResult implements Result {
    private NodeData nodeData;

    public GetNodeDataResult() {
    }

    public GetNodeDataResult( NodeData nodeData ) {
        this.nodeData = nodeData;
    }

    public NodeData getNodeData() {
        return nodeData;
    }

    public void setNodeData( NodeData nodeData ) {
        this.nodeData = nodeData;
    }
}
