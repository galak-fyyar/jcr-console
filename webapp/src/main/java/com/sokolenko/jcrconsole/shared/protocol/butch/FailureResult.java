package com.sokolenko.jcrconsole.shared.protocol.butch;

import net.customware.gwt.dispatch.shared.Result;

import java.io.Serializable;

/**
 * @author Anatoliy Sokolenko
 */
public class FailureResult<E extends Throwable & Serializable> implements Result {
    private E error;

    public FailureResult() {
    }

    public FailureResult( E error ) {
        this.error = error;
    }

    public E getError() {
        return error;
    }

    public void setError( E error ) {
        this.error = error;
    }
}
