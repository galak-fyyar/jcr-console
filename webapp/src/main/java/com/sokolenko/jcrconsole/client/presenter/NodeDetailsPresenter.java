package com.sokolenko.jcrconsole.client.presenter;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sokolenko.jcrconsole.client.model.NodeDetailsModel;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.Place;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

/**
 * @author Anatoliy Sokolenko
 */
@Singleton
public class NodeDetailsPresenter extends WidgetPresenter<NodeDetailsPresenter.Display> {
    @Inject
    public NodeDetailsPresenter( Display display, EventBus eventBus ) {
        super( display, eventBus );
    }

    @Override
    protected void onBind() {
        //TODO
    }

    @Override
    protected void onUnbind() {
        //TODO
    }

    @Override
    public Place getPlace() {
        return null;     //TODO
    }

    @Override
    protected void onPlaceRequest( PlaceRequest placeRequest ) {
        //TODO
    }

    @Override
    public void refreshDisplay() {
        //TODO
    }

    @Override
    public void revealDisplay() {
        //TODO
    }

    public static interface Display extends WidgetDisplay {
        void setNodeDetailsModel( NodeDetailsModel nodeDetailsModel );
    }
}
