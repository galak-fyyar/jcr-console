package com.sokolenko.jcrconsole.shared.protocol;

import java.io.Serializable;
import java.util.List;

/**
 * @author Anatoliy Sokolenko
 */
public class NodeData implements Serializable {
    private String path;

    private String nodeTypeName;

    private List<PropertyData> propertyDatas;

    public NodeData() {
    }

    public String getPath() {
        return path;
    }

    public void setPath( String path ) {
        this.path = path;
    }

    public String getNodeTypeName() {
        return nodeTypeName;
    }

    public void setNodeTypeName( String nodeTypeName ) {
        this.nodeTypeName = nodeTypeName;
    }

    public List<PropertyData> getPropertyDatas() {
        return propertyDatas;
    }

    public void setPropertyDatas( List<PropertyData> propertyDatas ) {
        this.propertyDatas = propertyDatas;
    }
}
