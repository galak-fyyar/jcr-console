package com.sokolenko.jcrconsole.client.model;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * @author Anatoliy Sokolenko
 */
public interface ModelResources extends ClientBundle {
    @Source( "assets/node-bucket.png" )
    ImageResource nodeBucket();

    @Source( "assets/node-empty.png" )
    ImageResource nodeEmpty();

    @Source( "assets/node-parent.png" )
    ImageResource nodeParent();
}
