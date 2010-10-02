package com.sokolenko.jcrconsole.shared.protocol.butch;

import net.customware.gwt.dispatch.shared.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Anatoliy Sokolenko
 */
public class ButchResult implements Result {
    private Map<String, Result> results;

    public Map<String, Result> getResults() {
        if ( results == null ) {
            results = new HashMap<String, Result>();
        }

        return results;
    }

    public void setResults( Map<String, Result> results ) {
        this.results = results;
    }
}
