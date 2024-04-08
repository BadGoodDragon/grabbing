package org.grabbing.devicepart.managers;

import android.content.Context;


import org.grabbing.devicepart.data.http.HttpGet;
import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.hooks.QueryExecutionManagerHook;

import java.util.ArrayList;
import java.util.List;

public class QueryExecutionManager {
    private final Context context;
    private List<QueryData> data;
    private List<QueryData> outputData;

    private QueryExecutionManagerHook hook;

    public QueryExecutionManager(Context context) {
        this.context = context;
    }

    public void setData(List<QueryData> data) {this.data = data;}

    public void run() {
        outputData = new ArrayList<>();
        hook = new QueryExecutionManagerHook(outputData);

        HttpGet httpGet = new HttpGet(context);
        httpGet.setData(data);
        httpGet.setHook(hook);
        httpGet.run();
    }

    public boolean isDone() {
        return hook.getCurrentQuantity() == data.size();
    }

    public List<QueryData> getData() {return outputData;}
}
