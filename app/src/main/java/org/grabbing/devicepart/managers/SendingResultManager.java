package org.grabbing.devicepart.managers;

import android.content.Context;

import org.grabbing.devicepart.converters.ListOfQueryDataToJson;
import org.grabbing.devicepart.data.http.HttpPost;
import org.grabbing.devicepart.data.http.HttpQuery;
import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.hooks.EmptyHook;

import java.util.ArrayList;
import java.util.List;

public class SendingResultManager {
    private final Context context;
    private List<QueryData> data;
    private QueryData query;

    public SendingResultManager(Context context) {
        this.context = context;
    }

    public void setData(List<QueryData> data) {this.data = data;}
    public void setQuery(QueryData query) {this.query = query;}

    public void run() {
        ListOfQueryDataToJson listOfQueryDataToJson = new ListOfQueryDataToJson();
        listOfQueryDataToJson.setData(data);
        listOfQueryDataToJson.convert();

        query.setQueryBody(listOfQueryDataToJson.getJson());
        query.setUrl(query.getUrl() + "/send");


        HttpQuery httpPost = new HttpPost(context);

        httpPost.runRightAway(query, new EmptyHook());
    }

    public List<QueryData> getData() {return data;}
    public QueryData getQuery() {return query;}
}
