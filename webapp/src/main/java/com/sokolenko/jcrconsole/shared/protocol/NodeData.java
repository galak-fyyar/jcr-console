package com.sokolenko.jcrconsole.shared.protocol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Anatoliy Sokolenko
 */
public class NodeData implements Serializable {
    private String path;

    private List<String> nodeTypeNames;

    private List<PropertyData> propertyDatas;

    public NodeData() {
    }

    public String getPath() {
        return path;
    }

    public void setPath( String path ) {
        this.path = path;
    }

    public List<String> getNodeTypeNames() {
        if ( nodeTypeNames == null ) {
            nodeTypeNames = new ArrayList<String>();
        }

        return nodeTypeNames;
    }

    public void setNodeTypeNames( List<String> nodeTypeNames ) {
        this.nodeTypeNames = nodeTypeNames;
    }

    public List<PropertyData> getPropertyDatas() {
        if ( propertyDatas == null ) {
            propertyDatas = new ArrayList<PropertyData>();
        }

        return propertyDatas;
    }

    public void setPropertyDatas( List<PropertyData> propertyDatas ) {
        this.propertyDatas = propertyDatas;
    }
}
