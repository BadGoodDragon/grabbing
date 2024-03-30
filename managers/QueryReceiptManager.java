package org.grabbing.devicepart.managers;

import android.content.Context;

import org.grabbing.devicepart.converters.JsonToListOfQueryData;
import org.grabbing.devicepart.data.http.HttpGet;
import org.grabbing.devicepart.data.http.HttpPost;
import org.grabbing.devicepart.data.http.HttpQuery;
import org.grabbing.devicepart.domain.QueryData;

import java.util.ArrayList;
import java.util.List;

public class QueryReceiptManager {
    private final Context context;
    private List<QueryData> data;
    private QueryData query;

    public QueryReceiptManager(Context context) {
        this.context = context;
    }

    public void setData(List<QueryData> data) {
        this.data = data;
    }

    public void setQuery(QueryData query) {
        this.query = query;
    }


    public void run() {
        HttpGet httpGet = new HttpGet(context);

        List<QueryData> queryData = new ArrayList<>();
        queryData.add(query);

        httpGet.setData(queryData);
        httpGet.run();

        JsonToListOfQueryData jsonToListOfQueryData = new JsonToListOfQueryData();

        jsonToListOfQueryData.setJson(httpGet.getData().get(0).getResponseBody());
        jsonToListOfQueryData.convert();
        data = jsonToListOfQueryData.getData();
    }


    public List<QueryData> getData() {
        return data;
    }
}
