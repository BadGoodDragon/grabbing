package org.grabbing.devicepart.managers;

import android.content.Context;

import org.grabbing.devicepart.data.http.HttpPost;
import org.grabbing.devicepart.data.http.HttpQuery;
import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.hooks.EmptyHook;

public class AddQueryManager {
    /*private final Context context;
    private QueryData query;
    private ListOfUsersLive list;

    public void setList(ListOfUsersLive list) {
        this.list = list;
    }

    public AddQueryManager(Context context) {
        this.context = context;
    }

    public void setQuery(QueryData query) {this.query = query;}

    public QueryData getQuery() {
        return query;
    }

    public void add(String url) {
        query.setQueryBody(url);
        query.setAddedUrl("/add");

        HttpQuery httpQuery = new HttpPost(context);
        httpQuery.runRightAway(query, new EmptyHook());
    }

    public void get() {
        query.setQueryBody("");
        query.setAddedUrl("/get");

        HttpQuery httpQuery = new HttpPost(context);
        //httpQuery.runRightAway(query, new FaceManagerHook(list));
    }*/
}
