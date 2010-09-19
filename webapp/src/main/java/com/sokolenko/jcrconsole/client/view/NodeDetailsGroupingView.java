package com.sokolenko.jcrconsole.client.view;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.GroupColumnData;
import com.extjs.gxt.ui.client.widget.grid.GroupingView;

import java.util.List;

/**
 * @author Anatoliy Sokolenko
 */
public class NodeDetailsGroupingView extends GroupingView {
    @Override
    protected void doGroupStart( StringBuilder buf, GroupColumnData g, List<ColumnData> cs, int colCount ) {
        super.doGroupStart( buf, g, cs, colCount );        //TODO
    }

    @Override
    protected void onMouseDown( GridEvent<ModelData> ge ) {
        super.onMouseDown( ge );        //TODO
    }
}
