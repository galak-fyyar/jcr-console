package com.sokolenko.jcrconsole.client.view;

import com.extjs.gxt.ui.client.store.GroupingStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.google.gwt.user.client.ui.Widget;
import com.sokolenko.jcrconsole.client.model.NodeDetailsModel;
import com.sokolenko.jcrconsole.client.model.PropertyDetailModel;
import com.sokolenko.jcrconsole.client.presenter.NodeDetailsPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anatoliy Sokolenko
 */
public class NodeDetailsView extends ContentPanel implements NodeDetailsPresenter.Display {
    protected static final String NO_NODE_MESSAGE = "Please, select node.";

    private GroupingStore<PropertyDetailModel> store;

    private Grid<PropertyDetailModel> grid;

    public NodeDetailsView() {
        setHeaderVisible( false );

        store = new GroupingStore<PropertyDetailModel>();
        store.groupBy( PropertyDetailModel.PROPERTY_NAME );

        List<ColumnConfig> columnConfigs = new ArrayList<ColumnConfig>();
        ColumnConfig columnConfig;

        columnConfig = new ColumnConfig();
        columnConfig.setId( PropertyDetailModel.PROPERTY_NAME );
        columnConfigs.add( columnConfig );

        columnConfig = new ColumnConfig();
        columnConfig.setId( PropertyDetailModel.PROPERTY_VALUE );
        columnConfigs.add( columnConfig );

        ColumnModel columnModel = new ColumnModel( columnConfigs );

        NodeDetailsGroupingView gridView = new NodeDetailsGroupingView();
        gridView.setShowGroupedColumn( false );
        gridView.setEnableGroupingMenu( false );

        grid = new Grid<PropertyDetailModel>( store, columnModel );
        grid.setHideHeaders( true );

        add( grid );
    }

    @Override
    public void setNodeDetailsModel( NodeDetailsModel nodeDetailsModel ) {
        unmask();

        if ( store != null ) {
            store.removeAll();
        }

        if ( nodeDetailsModel != null ) {
            if ( store != null ) {
                store.add( nodeDetailsModel.getPropertyDetailModels() );
            }
        } else {
            mask( NO_NODE_MESSAGE );
        }

        //TODO
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

    @Override
    protected void afterRender() {
        super.afterRender();

        mask( NO_NODE_MESSAGE );
    }
}
