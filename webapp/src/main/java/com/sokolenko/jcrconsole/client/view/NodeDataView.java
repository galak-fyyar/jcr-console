package com.sokolenko.jcrconsole.client.view;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.store.GroupingStore;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.google.gwt.user.client.ui.Widget;
import com.sokolenko.jcrconsole.client.model.PropertyDetailModel;
import com.sokolenko.jcrconsole.client.presenter.NodeDataPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anatoliy Sokolenko
 */
public class NodeDataView extends TabItem implements NodeDataPresenter.Display {
    protected static final String LOADING_MESSAGE = "Loading node data";

    private GroupingStore<PropertyDetailModel> store;

    private Grid<PropertyDetailModel> grid;

    public NodeDataView() {
        setClosable( true );

        RowLayout layout = new RowLayout();
        layout.setOrientation( Style.Orientation.VERTICAL );
        setLayout( layout );

        store = new GroupingStore<PropertyDetailModel>();
        store.groupBy( PropertyDetailModel.PROPERTY_NAME );

        List<ColumnConfig> columnConfigs = new ArrayList<ColumnConfig>();
        ColumnConfig columnConfig;

        columnConfig = new ColumnConfig();
        columnConfig.setId( PropertyDetailModel.PROPERTY_NAME );
        columnConfig.setWidth( 300 );
        columnConfigs.add( columnConfig );

        columnConfig = new ColumnConfig();
        columnConfig.setId( PropertyDetailModel.PROPERTY_VALUE );
        columnConfig.setWidth( 300 );
        columnConfigs.add( columnConfig );

        ColumnModel columnModel = new ColumnModel( columnConfigs );

        NodeDetailsGroupingView gridView = new NodeDetailsGroupingView();
        gridView.setShowGroupedColumn( false );
        gridView.setEnableGroupingMenu( false );

        grid = new Grid<PropertyDetailModel>( store, columnModel );
        grid.setAutoExpandColumn( PropertyDetailModel.PROPERTY_VALUE );
        grid.setHideHeaders( true );

        add( grid, new RowData( 1, 1 ) );
    }

    @Override
    public void setTabTitle( String title ) {
        setText( title );
    }

    @Override
    public void setNodePath( String nodePath ) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setNodeTypeName( String nodeTypaName ) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setPropertiesModels( List<PropertyDetailModel> models ) {
        store.removeAll();

        if ( models != null ) {
            store.add( models );
        }
    }

    @Override
    public Widget asWidget() {
        return this;
    }

    @Override
    public void startProcessing() {
        mask( LOADING_MESSAGE );
    }

    @Override
    public void stopProcessing() {
        unmask();
    }
}
