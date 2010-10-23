package com.sokolenko.jcrconsole.client.util;

import com.allen_sauer.gwt.log.client.Log;
import com.extjs.gxt.ui.client.data.DataProxy;
import com.extjs.gxt.ui.client.data.DataReader;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Anatoliy Sokolenko
 */
public class DataProxyWrapper<D> implements DataProxy<D> {
    private DataProxy<D> dataProxy;

    @Override
    public void load( DataReader<D> reader, Object loadConfig, AsyncCallback<D> asyncCallback ) {
        if ( dataProxy != null ) {
            dataProxy.load( reader, loadConfig, asyncCallback );
        } else {
            Log.warn( "Data proxy wrapper called before proxy initialized" );
        }
    }

    public DataProxy<D> getDataProxy() {
        return dataProxy;
    }

    public void setDataProxy( DataProxy<D> dataProxy ) {
        this.dataProxy = dataProxy;
    }
}
