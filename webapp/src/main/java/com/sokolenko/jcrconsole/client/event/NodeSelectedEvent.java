package com.sokolenko.jcrconsole.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.sokolenko.jcrconsole.shared.protocol.NodeInfo;

/**
 * @author Anatoliy Sokolenko
 */
public class NodeSelectedEvent extends GwtEvent<NodeSelectedHandler> {
    public static final Type<NodeSelectedHandler> EVENT_TYPE = new Type<NodeSelectedHandler>();

    private final NodeInfo nodeInfo;

    public NodeSelectedEvent( NodeInfo nodeInfo ) {
        this.nodeInfo = nodeInfo;
    }

    @Override
    public Type<NodeSelectedHandler> getAssociatedType() {
        return EVENT_TYPE;
    }

    @Override
    protected void dispatch( NodeSelectedHandler handler ) {
        if ( !isLive() ) {
            return;
        }

        handler.nodeSelected( nodeInfo );
    }
}
