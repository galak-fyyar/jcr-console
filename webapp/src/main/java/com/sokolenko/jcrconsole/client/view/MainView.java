package com.sokolenko.jcrconsole.client.view;

import com.extjs.gxt.ui.client.Style;
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
    private Widget leftCenterComponent;
    private Widget rightCenterComponent;
    private Widget bottomComponent;

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
            layoutData.setCollapsible( true );
            layoutData.setSplit( true );
            add( leftComponent, layoutData );
        }

        LayoutContainer centerContainer = new LayoutContainer();
        centerContainer.setLayout( new BorderLayout() );

        if ( leftCenterComponent != null ) {
            layoutData = new BorderLayoutData( Style.LayoutRegion.WEST );
            centerContainer.add( leftCenterComponent, layoutData );
        }

        if ( rightCenterComponent != null ) {
            layoutData = new BorderLayoutData( Style.LayoutRegion.EAST );
            layoutData.setCollapsible( true );
            layoutData.setSplit( true );
            centerContainer.add( rightCenterComponent, layoutData );
        }

        if ( bottomComponent != null ) {
            layoutData = new BorderLayoutData( Style.LayoutRegion.SOUTH );
            layoutData.setCollapsible( true );
            layoutData.setSplit( true );
            centerContainer.add( bottomComponent, layoutData );
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
    public Widget getLeftCenterComponent() {
        return leftCenterComponent;
    }

    @Override
    public void setLeftCenterComponent( Widget leftCenterComponent ) {
        this.leftCenterComponent = leftCenterComponent;
    }

    @Override
    public Widget getRightCenterComponent() {
        return rightCenterComponent;
    }

    @Override
    public void setRightCenterComponent( Widget rightCenterComponent ) {
        this.rightCenterComponent = rightCenterComponent;
    }

    @Override
    public Widget getBottomComponent() {
        return bottomComponent;
    }

    @Override
    public void setBottomComponent( Widget bottomComponent ) {
        this.bottomComponent = bottomComponent;
    }
}
