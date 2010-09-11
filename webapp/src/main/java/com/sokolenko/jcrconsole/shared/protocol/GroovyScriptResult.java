package com.sokolenko.jcrconsole.shared.protocol;

import net.customware.gwt.dispatch.shared.Result;

/**
 * @author Anatoliy Sokolenko
 */
public class GroovyScriptResult implements Result {
    private String result;

    public GroovyScriptResult( String result ) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
