package com.sokolenko.jcrconsole.client.event;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.EventType;

/**
 * @author Anatoliy Sokolenko
 */
public class ExecuteScriptEvent extends BaseEvent {
    public static final EventType EVENT_TYPE = new EventType();

    public ExecuteScriptEvent() {
        super( EVENT_TYPE );
    }
}
