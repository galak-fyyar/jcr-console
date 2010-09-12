package com.sokolenko.jcrconsole.client.presenter;

import com.extjs.gxt.ui.client.event.Observable;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.Place;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

/**
 * @author Anatoliy Sokolenko
 */
@Singleton
public class MainPresenter extends WidgetPresenter<MainPresenter.Display> {
    private ScriptConsolePresenter scriptConsolePresenter;

    @Inject
    public MainPresenter( Display display, EventBus eventBus, ScriptConsolePresenter scriptConsolePresenter ) {
        super( display, eventBus );

        this.scriptConsolePresenter = scriptConsolePresenter;

        bind();
    }

    @Override
    protected void onBind() {
        getDisplay().setRightCenterComponent( scriptConsolePresenter.getDisplay().asWidget() );

        getDisplay().initLayout();
    }

    @Override
    protected void onUnbind() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Place getPlace() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void onPlaceRequest( PlaceRequest placeRequest ) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void refreshDisplay() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void revealDisplay() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public static interface Display extends WidgetDisplay {
        void initLayout();

        Widget getLeftComponent();

        void setLeftComponent( Widget leftComponent );

        Widget getLeftCenterComponent();

        void setLeftCenterComponent( Widget leftCenterComponent );

        Widget getRightCenterComponent();

        void setRightCenterComponent( Widget rightCenterComponent );

        Widget getBottomComponent();

        void setBottomComponent( Widget bottomComponent );
    }
}
