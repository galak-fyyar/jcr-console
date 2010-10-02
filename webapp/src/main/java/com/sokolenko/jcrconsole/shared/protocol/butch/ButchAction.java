package com.sokolenko.jcrconsole.shared.protocol.butch;

import net.customware.gwt.dispatch.shared.Action;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Anatoliy Sokolenko
 */
public class ButchAction implements Action<ButchResult> {
    Map<String, Action> actions;

    public ButchAction() {
    }

    public ButchAction( Map<String, Action> actions ) {
        this.actions = actions;
    }

    public Map<String, Action> getActions() {
        if ( actions == null ) {
            actions = new HashMap<String, Action>();
        }

        return actions;
    }

    public void setActions( Map<String, Action> actions ) {
        this.actions = actions;
    }
}
