package com.sokolenko.jcrconsole.server.handlers;

import com.sokolenko.jcrconsole.shared.protocol.GroovyScriptAction;
import com.sokolenko.jcrconsole.shared.protocol.GroovyScriptResult;
import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyRuntimeException;
import groovy.lang.Script;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;
import org.apache.commons.lang.StringUtils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Anatoliy Sokolenko
 */
public class GroovyScriptActionHandler extends JcrActionHandler<GroovyScriptAction, GroovyScriptResult> {
    protected static final Class<GroovyScriptAction> ACTION_CLASS = GroovyScriptAction.class;

    @Override
    public Class<GroovyScriptAction> getActionType() {
        return ACTION_CLASS;
    }

    @Override
    public GroovyScriptResult execute( GroovyScriptAction action, ExecutionContext context ) throws DispatchException {
        String scriptText = action.getScript();
        scriptText = preprocessScript( scriptText );

        Binding binding = new Binding();
        binding.setVariable( "session", getSession() );

        Script script = null;
        try {
            GroovyClassLoader loader = new GroovyClassLoader( getClass().getClassLoader() );
            Class scriptClass = loader.parseClass( scriptText );
            script = ( Script ) scriptClass.newInstance();
            script.setBinding( binding );
        } catch ( ClassCastException e ) {
            //TODO
        } catch ( Exception e ) {
            //TODO
        }

        Object result = null;
        try {
            if ( script != null ) {
                result = script.run();
            }
        } catch ( GroovyRuntimeException e ) {
            result = e;
        } catch ( Exception e ) {
            result = e;
        }

        String scriptResultText = parseScriptResult( result );

        return new GroovyScriptResult( scriptResultText );
    }

    @Override
    public void rollback( GroovyScriptAction action, GroovyScriptResult result, ExecutionContext context ) throws DispatchException {

    }

    private String preprocessScript( String script ) {
        if ( script == null ) {
            return StringUtils.EMPTY;
        }

        script = script.trim();

        int importSectionPlace = -1;
        int indexOfPackage = script.indexOf( "package" );
        if ( indexOfPackage != -1 ) {
            int indexOfPackageEol = script.indexOf( "\n", indexOfPackage );

            if ( indexOfPackageEol != -1 ) {
                int indexOfFirstImport = script.indexOf( "import", indexOfPackageEol );

                if ( indexOfFirstImport != -1 ) {
                    importSectionPlace = indexOfFirstImport;
                } else {
                    importSectionPlace = indexOfPackageEol + 1;
                }
            } else {
                indexOfPackageEol = script.indexOf( ";", indexOfPackage );

                if ( indexOfPackageEol != -1 ) {
                    importSectionPlace = indexOfPackageEol + 1;
                } else {
                    importSectionPlace = script.length();
                }
            }
        } else {
            int indexOfFirstImport = script.indexOf( "import" );

            if ( indexOfFirstImport != -1 ) {
                importSectionPlace = indexOfFirstImport;
            } else {
                importSectionPlace = 0;
            }
        }

        StringBuilder importsBuilder = new StringBuilder();
        importsBuilder.append( "import javax.jcr.*" );

        return insertString( script, importsBuilder.toString(), importSectionPlace );

    }

    protected String parseScriptResult( Object o ) {
        if ( o == null ) {
            return "";
        } else if ( o instanceof Throwable ) {
            Throwable throwable = ( Throwable ) o;

            StringWriter throwableWriter = new StringWriter();
            throwableWriter.append( throwable.getClass().getName() );

            PrintWriter throwablePrintWriter = new PrintWriter( throwableWriter );
            throwable.printStackTrace( throwablePrintWriter );

            return throwableWriter.toString();
        } else {
            return o.toString();
        }
    }

    protected String insertString( String original, String fragment, int startingFrom ) {
        return original; //TODO
    }
}
