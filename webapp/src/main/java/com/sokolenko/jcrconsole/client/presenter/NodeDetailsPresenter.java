package com.sokolenko.jcrconsole.client.presenter;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.Observable;
import com.extjs.gxt.ui.client.event.TabPanelEvent;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sokolenko.jcrconsole.client.JcrConsoleInjector;
import com.sokolenko.jcrconsole.client.event.NodeSelectedEvent;
import com.sokolenko.jcrconsole.client.event.NodeSelectedHandler;
import com.sokolenko.jcrconsole.shared.protocol.NodeInfo;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.Place;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Anatoliy Sokolenko
 */
@Singleton
public class NodeDetailsPresenter extends WidgetPresenter<NodeDetailsPresenter.Display> implements NodeSelectedHandler {
    private final Set<NodeDataPresenter> nodeDataPresenters = new HashSet<NodeDataPresenter>();

    private final JcrConsoleInjector injector;

    @Inject
    public NodeDetailsPresenter( Display display, EventBus eventBus, JcrConsoleInjector injector ) {
        super( display, eventBus );

        this.injector = injector;

        bind();
    }

    @Override
    public void nodeSelected( NodeInfo selected ) {
        NodeDataPresenter dataPresenter = findDataPresenter( selected );

        if ( dataPresenter != null ) {
            getDisplay().setSelection( dataPresenter.getDisplay().asWidget() );
        } else {
            dataPresenter = injector.createNodeDataPresenter();
            dataPresenter.setNodeInfo( selected );

            getDisplay().addItem( dataPresenter.getDisplay().asWidget() );
            getDisplay().setSelection( dataPresenter.getDisplay().asWidget() );

            nodeDataPresenters.add( dataPresenter );
        }
    }

    protected NodeDataPresenter findDataPresenter( NodeInfo nodeInfoTreeModel ) {
        for ( NodeDataPresenter dataPresenter : nodeDataPresenters ) {
            if ( dataPresenter != null && dataPresenter.getNodeInfo().equals( nodeInfoTreeModel ) ) {
                return dataPresenter;
            }
        }

        return null;
    }

    protected NodeDataPresenter findDataPresenter( Widget widget ) {
        for ( NodeDataPresenter dataPresenter : nodeDataPresenters ) {
            if ( dataPresenter != null && dataPresenter.getDisplay().asWidget().equals( widget ) ) {
                return dataPresenter;
            }
        }

        return null;
    }

    @Override
    public Place getPlace() {
        return null;     //TODO
    }

    @Override
    public void refreshDisplay() {
        //TODO
    }

    @Override
    public void revealDisplay() {
        //TODO
    }

    @Override
    protected void onPlaceRequest( PlaceRequest placeRequest ) {
        //TODO
    }

    @Override
    protected void onBind() {
        eventBus.addHandler( NodeSelectedEvent.EVENT_TYPE, this );

        getDisplay().getTabPanelObservable().addListener( Events.Remove, new Listener<TabPanelEvent>() {
            @Override
            public void handleEvent( TabPanelEvent be ) {
                TabItem removedItem = be.getItem();

                NodeDataPresenter dataPresenter = findDataPresenter( removedItem );
                if ( dataPresenter != null ) {
                    dataPresenter.unbind();

                    nodeDataPresenters.remove( dataPresenter );
                }
            }
        } );
    }

    @Override
    protected void onUnbind() {
        //TODO
    }

    public static interface Display extends WidgetDisplay {
        void setSelection( Widget widget );

        void addItem( Widget widget );

        Observable getTabPanelObservable();
    }
}
