package com.sokolenko.jcrconsole.client.view;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.BaseObservable;
import com.extjs.gxt.ui.client.event.ButtonEvent;
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
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;
import com.sokolenko.jcrconsole.client.event.ExecuteScriptEvent;
import com.sokolenko.jcrconsole.client.presenter.ScriptConsolePresenter;

/**
 * @author Anatoliy Sokolenko
 */
@Singleton
public class ScriptConsoleView extends ContentPanel implements ScriptConsolePresenter.Display {
    private final Observable observable = new BaseObservable();

    private final TextArea scriptTextArea;

    private final Html outputText;

    public ScriptConsoleView() {
        setLayout( new BorderLayout() );
        setHeading( "Script" );
        setAnimCollapse( true );

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

        BorderLayoutData layoutData;

        scriptTextArea = new TextArea();
        scriptTextArea.setHideLabel( true );

        LayoutContainer scriptTextAreaContainer = new LayoutContainer( new FitLayout() );
        scriptTextAreaContainer.setHeight( "100%" );
        layoutData = new BorderLayoutData( Style.LayoutRegion.NORTH );
        layoutData.setMargins( new Margins( 0, 0, 5, 0 ) );
        layoutData.setCollapsible( true );
        layoutData.setSplit( true );

        scriptTextAreaContainer.add( scriptTextArea );
        add( scriptTextAreaContainer, layoutData );

        outputText = new Html();
        outputText.setHeight( "100%" );
        ContentPanel outputTextContainer = new ContentPanel( new FitLayout() );
        outputTextContainer.setHeaderVisible( false );
        outputTextContainer.setBodyStyle( "padding: 5px;" );
        outputTextContainer.setScrollMode( Style.Scroll.AUTO );
        layoutData = new BorderLayoutData( Style.LayoutRegion.SOUTH );
        layoutData.setMargins( new Margins( 0, 0, 0, 0 ) );

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