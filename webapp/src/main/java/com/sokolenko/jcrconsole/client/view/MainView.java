package com.sokolenko.jcrconsole.client.view;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;
import com.sokolenko.jcrconsole.client.presenter.MainPresenter;

/**
 * @author Anatoliy Sokolenko
 */
@Singleton
public class MainView extends Viewport implements MainPresenter.Display {
    private Widget leftComponent;
    private Widget centerComponent;
    private Widget rightComponent;

    @Override
    public Widget asWidget() {
        return this;
    }

    @Override
    public void startProcessing() {

    }

    @Override
    public void stopProcessing() {

    }

    @Override
    public void initLayout() {
        setLayout( new BorderLayout() );

        BorderLayoutData layoutData;

        if ( leftComponent != null ) {
            layoutData = new BorderLayoutData( Style.LayoutRegion.WEST );
            layoutData.setMargins( new Margins( 0, 5, 0, 0 ) );
            layoutData.setCollapsible( true );
            layoutData.setSplit( true );
            add( leftComponent, layoutData );
        }

        LayoutContainer centerContainer = new LayoutContainer();
        centerContainer.setLayout( new BorderLayout() );

        if ( centerComponent != null ) {
            layoutData = new BorderLayoutData( Style.LayoutRegion.WEST );
            layoutData.setMargins( new Margins( 0 ) );
            centerContainer.add( centerComponent, layoutData );
        }

        if ( rightComponent != null ) {
            layoutData = new BorderLayoutData( Style.LayoutRegion.EAST );
            layoutData.setMargins( new Margins( 0, 0, 0, 5 ) );
            layoutData.setCollapsible( true );
            layoutData.setSplit( true );
            centerContainer.add( rightComponent, layoutData );
        }

        if ( centerContainer.getItemCount() > 0 ) {
            add( centerContainer, new BorderLayoutData( Style.LayoutRegion.CENTER ) );
        }
    }

    @Override
    public Widget getLeftComponent() {
        return leftComponent;
    }

    @Override
    public void setLeftComponent( Widget leftComponent ) {
        this.leftComponent = leftComponent;
    }

    @Override
    public Widget getCenterComponent() {
        return centerComponent;
    }

    @Override
    public void setCenterComponent( Widget centerComponent ) {
        this.centerComponent = centerComponent;
    }

    @Override
    public Widget getRightComponent() {
        return rightComponent;
    }

    @Override
    public void setRightComponent( Widget rightComponent ) {
        this.rightComponent = rightComponent;
    }
}
