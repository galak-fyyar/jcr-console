package com.sokolenko.jcrconsole.shared.protocol;

import net.customware.gwt.dispatch.shared.Result;

import java.util.List;

/**
 * @author Anatoliy Sokolenko
 */
public class GetNodesResult implements Result {
    private List<NodeInfo> nodeInfos;

    public GetNodesResult() {
    }

    public List<NodeInfo> getNodeInfos() {
        return nodeInfos;
    }

    public void setNodeInfos( List<NodeInfo> nodeInfos ) {
        this.nodeInfos = nodeInfos;
    }
}
