package com.sokolenko.jcrconsole.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author Anatoliy Sokolenko
 */
@Component
@Scope( "session" )
public class JackrabbitSessionData implements Serializable {
    @Value( "${sample.workspace.name}" )
    private String sampleWorkspace;

    private String workspaceName;

    public String getSampleWorkspace() {
        return sampleWorkspace;
    }

    public String getWorkspaceName() {
        return workspaceName;
    }

    public void setWorkspaceName( String workspaceName ) {
        this.workspaceName = workspaceName;
    }
}
