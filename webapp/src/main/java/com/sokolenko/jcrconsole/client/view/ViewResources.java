package com.sokolenko.jcrconsole.client.view;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * @author Anatoliy Sokolenko
 */
public interface ViewResources extends ClientBundle {
    @Source( "assets/refresh.png" )
    ImageResource refreshIcon();

    @Source( "assets/execute.png" )
    ImageResource executeIcon();
}
