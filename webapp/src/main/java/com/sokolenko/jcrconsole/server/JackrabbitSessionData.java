package com.sokolenko.jcrconsole.server;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author Anatoliy Sokolenko
 */
@Component
@Scope( "session" )
public class JackrabbitSessionData implements Serializable {
    private String workspaceName;

    public String getWorkspaceName() {
        return workspaceName;
    }

    public void setWorkspaceName( String workspaceName ) {
        this.workspaceName = workspaceName;
    }
}
