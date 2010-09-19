package com.sokolenko.jcrconsole.client.model;

import java.util.List;

/**
 * @author Anatoliy Sokolenko
 */
public class NodeDetailsModel {
    private String nodePath;

    private String nodeTypeName;

    private List<PropertyDetailModel> propertyDetailModels;

    public NodeDetailsModel() {
        //TODO
    }

    public String getNodePath() {
        return nodePath;
    }

    public String getNodeTypeName() {
        return nodeTypeName;
    }

    public List<PropertyDetailModel> getPropertyDetailModels() {
        return propertyDetailModels;
    }
}
