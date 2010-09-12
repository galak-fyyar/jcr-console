package com.sokolenko.jcrconsole.shared.protocol;

import net.customware.gwt.dispatch.shared.Result;

/**
 * @author Anatoliy Sokolenko
 */
public class ScriptExecuteResult implements Result {
    private String result;

    public ScriptExecuteResult() {
    }

    public ScriptExecuteResult( String result ) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
