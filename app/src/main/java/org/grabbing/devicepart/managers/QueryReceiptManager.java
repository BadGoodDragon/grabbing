package org.grabbing.devicepart.managers;

import android.content.Context;

import org.grabbing.devicepart.converters.JsonToListOfQueryData;
import org.grabbing.devicepart.data.http.HttpGet;
import org.grabbing.devicepart.data.http.HttpPost;
import org.grabbing.devicepart.data.http.HttpQuery;
import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.hooks.QueryReceiptManagerHook;
import org.grabbing.devicepart.livedata.QueryDataListLive;

import java.util.ArrayList;
import java.util.List;

public class QueryReceiptManager {
    private final Context context;
    private QueryDataListLive data;
    private QueryData query;

    public QueryReceiptManager(Context context) {
        this.context = context;
    }

    public void setQuery(QueryData query) {this.query = query;}
    public void setData(QueryDataListLive data) {this.data = data;}

    public void run() {
        data.clearAll();

        query.setAddedUrl("/receive");

        QueryReceiptManagerHook hook = new QueryReceiptManagerHook(data);
        HttpQuery httpGet = new HttpGet(context);
        httpGet.runRightAway(query, hook);
    }

    public QueryData getQuery() {
        return query;
    }
}
