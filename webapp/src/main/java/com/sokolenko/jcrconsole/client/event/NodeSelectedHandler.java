package com.sokolenko.jcrconsole.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.sokolenko.jcrconsole.shared.protocol.NodeInfo;

/**
 * @author Anatoliy Sokolenko
 */
public interface NodeSelectedHandler extends EventHandler {
    void nodeSelected( NodeInfo nodeInfo );
}
