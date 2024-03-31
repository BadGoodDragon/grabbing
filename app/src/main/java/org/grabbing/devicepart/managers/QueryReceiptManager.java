package org.grabbing.devicepart.managers;

import android.content.Context;

import org.grabbing.devicepart.converters.JsonToListOfQueryData;
import org.grabbing.devicepart.data.http.HttpGet;
import org.grabbing.devicepart.data.http.HttpPost;
import org.grabbing.devicepart.data.http.HttpQuery;
import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.hooks.QueryReceiptManagerHook;

import java.util.ArrayList;
import java.util.List;

public class QueryReceiptManager {
    private final Context context;
    private List<QueryData> data;
    private QueryData query;
    private QueryReceiptManagerHook hook;

    public QueryReceiptManager(Context context) {
        this.context = context;
    }

    public void setQuery(QueryData query) {this.query = query;}

    public void run() {
        data = new ArrayList<>();
        hook = new QueryReceiptManagerHook(data);

        HttpQuery httpGet = new HttpGet(context);

        httpGet.runRightAway(query, hook);
    }

    public List<QueryData> getData() {return data;}
    public boolean isHasResponse() {return hook.isHasResponse();}
}
