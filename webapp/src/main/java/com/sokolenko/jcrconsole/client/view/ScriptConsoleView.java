package com.sokolenko.jcrconsole.client.view;

import com.extjs.gxt.ui.client.event.BaseObservable;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Observable;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;
import com.sokolenko.jcrconsole.client.event.ExecuteScriptEvent;
import com.sokolenko.jcrconsole.client.presenter.ScriptConsolePresenter;

/**
 * @author Anatoliy Sokolenko
 */
@Singleton
public class ScriptConsoleView extends ContentPanel implements ScriptConsolePresenter.Display {
    protected static final String AUTO_FILL = "100%";

    private final Observable observable = new BaseObservable();

    private final TextArea textArea;

    public ScriptConsoleView() {
        setHeading( "Script" );

        ToolBar toolBar = new ToolBar();

        Button executeButton = new Button( "Execute" );
        executeButton.addSelectionListener( new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected( ButtonEvent ce ) {
                observable.fireEvent( ExecuteScriptEvent.EVENT_TYPE, new ExecuteScriptEvent() );
            }
        } );
        toolBar.add( executeButton );

        setTopComponent( toolBar );

        textArea = new TextArea();
        textArea.setHideLabel( true );
        textArea.setWidth( AUTO_FILL );
        textArea.setHeight( AUTO_FILL );

        add( textArea );
    }

    @Override
    public Widget asWidget() {
        return this;
    }

    @Override
    public void startProcessing() {
        mask();
    }

    @Override
    public void stopProcessing() {
        unmask();
    }

    @Override
    public void setScriptText( String scriptText ) {
        textArea.setValue( scriptText );
    }

    @Override
    public String getScriptText() {
        return textArea.getValue();
    }

    @Override
    public Observable getViewObservable() {
        return observable;
    }
}
