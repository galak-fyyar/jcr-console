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

        if ( model instanceof NodesBucketTreeModel ) {
            NodesBucketTreeModel nodesBucketTreeModel = ( NodesBucketTreeModel ) model;

            long bucketStart = ( Long ) nodesBucketTreeModel.get( NodesBucketTreeModel.BUCKET_START );
            long bucketLength = ( Long ) nodesBucketTreeModel.get( NodesBucketTreeModel.BUCKET_LENGTH );

            long bucketEnd = bucketStart + bucketLength - 1;

            labelBuilder.append( "children" );
            labelBuilder.append( " " );
            labelBuilder.append( bucketStart );
            labelBuilder.append( " \u2014 " );
            labelBuilder.append( bucketEnd );
        } else {
            labelBuilder.append( model.get( NodeInfoTreeModel.NODE_NAME ) );

            labelBuilder.append( " " );

            labelBuilder.append( "<span style='color: gray'>" );
            labelBuilder.append( "&lt;" );
            labelBuilder.append( model.get( NodeInfoTreeModel.NODE_TYPE_NAME ) );
            labelBuilder.append( "&gt;" );
            labelBuilder.append( "</span>" );
        }

        labelBuilder.append( "</span>" );

        return labelBuilder.toString();
    }
}
