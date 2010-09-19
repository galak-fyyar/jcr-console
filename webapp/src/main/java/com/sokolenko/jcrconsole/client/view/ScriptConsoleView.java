package com.sokolenko.jcrconsole.client.view;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.BaseObservable;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.Observable;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sokolenko.jcrconsole.client.event.ExecuteScriptEvent;
import com.sokolenko.jcrconsole.client.presenter.ScriptConsolePresenter;

/**
 * @author Anatoliy Sokolenko
 */
@Singleton
public class ScriptConsoleView extends ContentPanel implements ScriptConsolePresenter.Display {
    protected static final String OUTPUT_TEXT_FONT_FAMILY = "tahoma,arial,verdana,sans-serif";

    protected static final String OUTPUT_TEXT_FONT_SIZE = "12px";

    private final Observable observable = new BaseObservable();

    private final TextArea scriptTextArea;

    private final Html outputText;

    @Inject
    public ScriptConsoleView( ViewResources viewResources ) {
        setLayout( new BorderLayout() );
        setHeading( "Script" );
        setAnimCollapse( true );

        ToolBar toolBar = new ToolBar();

        AbstractImagePrototype executeIcon = AbstractImagePrototype.create( viewResources.executeIcon() );

        Button executeButton = new Button( "Execute" );
        executeButton.setIcon( executeIcon );
        executeButton.addSelectionListener( new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected( ButtonEvent ce ) {
                observable.fireEvent( ExecuteScriptEvent.EVENT_TYPE, new ExecuteScriptEvent() );
            }
        } );
        toolBar.add( executeButton );

        setTopComponent( toolBar );

        BorderLayoutData layoutData;

        scriptTextArea = new TextArea();
        scriptTextArea.setHideLabel( true );
        scriptTextArea.addKeyListener( new KeyListener() {
            @Override
            public void componentKeyPress( ComponentEvent event ) {
                FieldEvent fieldEvent = ( FieldEvent ) event;

                if ( fieldEvent.isControlKey() && fieldEvent.getKeyCode() == KeyCodes.KEY_ENTER ) {
                    observable.fireEvent( ExecuteScriptEvent.EVENT_TYPE, new ExecuteScriptEvent() );
                }
            }
        } );
        scriptTextArea.addListener( Events.Render, new Listener<ComponentEvent>() {
            @Override
            public void handleEvent( ComponentEvent be ) {
                scriptTextArea.el().firstChild().setStyleAttribute( "padding", "5px" );
            }
        } );

        LayoutContainer scriptTextAreaContainer = new LayoutContainer( new FitLayout() );
        layoutData = new BorderLayoutData( Style.LayoutRegion.CENTER );
        layoutData.setMargins( new Margins( 0, 0, 5, 0 ) );
        layoutData.setCollapsible( true );

        scriptTextAreaContainer.add( scriptTextArea );
        add( scriptTextAreaContainer, layoutData );

        outputText = new Html();
        outputText.setStyleAttribute( "font-family", OUTPUT_TEXT_FONT_FAMILY );
        outputText.setStyleAttribute( "font-size", OUTPUT_TEXT_FONT_SIZE );

        ContentPanel outputTextContainer = new ContentPanel( new FitLayout() );
        outputTextContainer.setHeaderVisible( false );
        outputTextContainer.setBodyStyle( "padding: 5px;" );
        outputTextContainer.setScrollMode( Style.Scroll.AUTO );
        layoutData = new BorderLayoutData( Style.LayoutRegion.SOUTH );
        layoutData.setMargins( new Margins( 0, 0, 0, 0 ) );
        layoutData.setSplit( true );

        outputTextContainer.add( outputText );
        add( outputTextContainer, layoutData );
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
        scriptTextArea.setValue( scriptText );
    }

    @Override
    public String getScriptText() {
        return scriptTextArea.getValue();
    }

    @Override
    public void setOutputText( String text ) {
        outputText.setHtml( text );
    }

    @Override
    public String getOutputText() {
        return outputText.getHtml();
    }

    @Override
    public Observable getViewObservable() {
        return observable;
    }
}