package com.sokolenko.jcrconsole.client.view;

import com.extjs.gxt.ui.client.event.Observable;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;
import com.sokolenko.jcrconsole.client.presenter.NodeDetailsPresenter;
import com.sokolenko.jcrconsole.client.util.Assert;

/**
 * @author Anatoliy Sokolenko
 */
@Singleton
public class NodeDetailsView extends TabPanel implements NodeDetailsPresenter.Display {
    protected static final String NO_NODE_MESSAGE = "Please, select node.";

    @Override
    public void setSelection( Widget widget ) {
        Assert.isTrue( widget instanceof TabItem, "widget should be TabItem" );

        setSelection( ( TabItem ) widget );
    }

    @Override
    public void addItem( Widget widget ) {
        Assert.isTrue( widget instanceof TabItem, "widget should be TabItem" );

        add( ( TabItem ) widget );
    }

    @Override
    public Observable getTabPanelObservable() {
        return this;
    }

    @Override
    public Widget asWidget() {
        return this;
    }

    @Override
    public void startProcessing() {
        //TODO
    }

    @Override
    public void stopProcessing() {
        //TODO
    }
}
