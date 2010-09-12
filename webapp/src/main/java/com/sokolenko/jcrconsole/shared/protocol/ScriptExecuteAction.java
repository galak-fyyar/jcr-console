package com.sokolenko.jcrconsole.shared.protocol;

import net.customware.gwt.dispatch.shared.Action;

/**
 * @author Anatoliy Sokolenko
 */
public class ScriptExecuteAction implements Action<ScriptExecuteResult> {
    private String script;

    public ScriptExecuteAction() {
    }

    public ScriptExecuteAction( String script ) {
        this.script = script;
    }

    public String getScript() {
        return script;
    }
}
