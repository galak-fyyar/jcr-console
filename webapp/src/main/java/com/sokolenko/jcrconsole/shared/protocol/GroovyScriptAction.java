package com.sokolenko.jcrconsole.shared.protocol;

import net.customware.gwt.dispatch.shared.Action;

/**
 * @author Anatoliy Sokolenko
 */
public class GroovyScriptAction implements Action<GroovyScriptResult> {
    private String script;

    public GroovyScriptAction( String script ) {
        this.script = script;
    }

    public String getScript() {
        return script;
    }
}
