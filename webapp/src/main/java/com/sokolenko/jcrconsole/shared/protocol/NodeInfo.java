package com.sokolenko.jcrconsole.shared.protocol;

import java.io.Serializable;

/**
 * @author Anatoliy Sokolenko
 */
public class NodeInfo implements Serializable {
    private String nodeName;

    private String nodeTypeName;

    private long totalChildrenCount;

    private String nodePath;

    public NodeInfo() {
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName( String nodeName ) {
        this.nodeName = nodeName;
    }

    public String getNodeTypeName() {
        return nodeTypeName;
    }

    public void setNodeTypeName( String nodeTypeName ) {
        this.nodeTypeName = nodeTypeName;
    }

    public long getTotalChildrenCount() {
        return totalChildrenCount;
    }

    public void setTotalChildrenCount( long totalChildrenCount ) {
        this.totalChildrenCount = totalChildrenCount;
    }

    public String getNodePath() {
        return nodePath;
    }

    public void setNodePath( String nodePath ) {
        this.nodePath = nodePath;
    }
}
