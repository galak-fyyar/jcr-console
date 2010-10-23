package com.sokolenko.jcrconsole.shared.protocol;

import net.customware.gwt.dispatch.shared.Action;

/**
 * @author Anatoliy Sokolenko
 */
public class GetNodeDataAction implements Action<GetNodeDataResult> {
    private String nodePath;

    public GetNodeDataAction() {
    }

    public GetNodeDataAction( String nodePath ) {
        this.nodePath = nodePath;
    }

    public String getNodePath() {
        return nodePath;
    }

    public void setNodePath( String nodePath ) {
        this.nodePath = nodePath;
    }
}
