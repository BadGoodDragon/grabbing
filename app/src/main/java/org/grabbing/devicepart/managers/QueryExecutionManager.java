package org.grabbing.devicepart.managers;

import android.content.Context;


import org.grabbing.devicepart.data.http.HttpGet;
import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.hooks.QueryExecutionManagerHook;
import org.grabbing.devicepart.livedata.TypeLive;

import java.util.ArrayList;
import java.util.List;

public class QueryExecutionManager {
    private final Context context;
    private List<QueryData> data;
    private TypeLive<List<QueryData>> outputData;

    private QueryExecutionManagerHook hook;

    public QueryExecutionManager(Context context) {
        this.context = context;
    }

    public void setData(List<QueryData> data) {this.data = data;}
    public void setOutputData(TypeLive<List<QueryData>> outputData) {this.outputData = outputData;}

    public void run() {
        outputData.setStatus(false);
        hook = new QueryExecutionManagerHook(outputData, data.size());

        HttpGet httpGet = new HttpGet(context);
        httpGet.setData(data);
        httpGet.setHook(hook);
        httpGet.run();
    }


}
