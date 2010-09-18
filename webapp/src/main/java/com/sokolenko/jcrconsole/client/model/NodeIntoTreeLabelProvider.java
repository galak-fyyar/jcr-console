package com.sokolenko.jcrconsole.client.model;

import com.extjs.gxt.ui.client.data.ModelStringProvider;

/**
 * @author Anatoliy Sokolenko
 */
public class NodeIntoTreeLabelProvider implements ModelStringProvider<NodeInfoTreeModel> {
    @Override
    public String getStringValue( NodeInfoTreeModel model, String property ) {
        StringBuilder labelBuilder = new StringBuilder();
        labelBuilder.append( "<span>" );
        labelBuilder.append( model.get( NodeInfoTreeModel.NODE_NAME ) );

        labelBuilder.append( " " );

        labelBuilder.append( "<span style='color: gray'>" );
        labelBuilder.append( "&lt;" );
        labelBuilder.append( model.get( NodeInfoTreeModel.NODE_TYPE_NAME ) );
        labelBuilder.append( "&gt;" );
        labelBuilder.append( "</span>" );

        labelBuilder.append( "</span>" );

        return labelBuilder.toString();
    }
}
