package com.sokolenko.jcrconsole.client.model;

import com.extjs.gxt.ui.client.data.ModelIconProvider;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sokolenko.jcrconsole.client.util.Assert;

/**
 * @author Anatoliy Sokolenko
 */
@Singleton
public class NodeInfoTreeModelIconProvider implements ModelIconProvider<NodeInfoTreeModel> {
    private final ModelResources modelResources;

    @Inject
    public NodeInfoTreeModelIconProvider( ModelResources modelResources ) {
        Assert.notNull( modelResources, "modelResources" );

        this.modelResources = modelResources;
    }

    @Override
    public AbstractImagePrototype getIcon( NodeInfoTreeModel model ) {
        AbstractImagePrototype icon = null;

        if ( model instanceof NodesBucketTreeModel ) {
            icon = AbstractImagePrototype.create( modelResources.nodeBucket() );
        } else {
            long totalChildrenCount = ( Long ) model.get( NodeInfoTreeModel.TOTAL_CHILDREN_COUNT );
            if ( totalChildrenCount > 0 ) {
                icon = AbstractImagePrototype.create( modelResources.nodeParent() );
            } else {
                icon = AbstractImagePrototype.create( modelResources.nodeEmpty() );
            }
        }

        return icon;
    }
}
