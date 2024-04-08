package org.grabbing.devicepart.managers;

import android.content.Context;

import com.google.gson.Gson;

import org.grabbing.devicepart.data.http.HttpGet;
import org.grabbing.devicepart.data.http.HttpPost;
import org.grabbing.devicepart.data.http.HttpQuery;
import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.hooks.EmptyHook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FaceManager {
    private final Context context;
    private QueryData query;

    public FaceManager(Context context) {
        this.context = context;
    }

    public void setQuery(QueryData query) {this.query = query;}

    public void register(String name) {
        Map<String, String> body = new HashMap<>();
        body.put("type", "register");
        body.put("name", name);

        Gson gson = new Gson();

        query.setQueryBody(gson.toJson(body));

        HttpQuery httpPost = new HttpPost(context);

        httpPost.runRightAway(query, new EmptyHook());

    }
    public void attach(String username) {
        Map<String, String> body = new HashMap<>();
        body.put("type", "attach");
        body.put("username", username);

        Gson gson = new Gson();

        query.setQueryBody(gson.toJson(body));

        HttpQuery httpPost = new HttpPost(context);

        httpPost.runRightAway(query, new EmptyHook());
    }
    public void detach(String username) {
        Map<String, String> body = new HashMap<>();
        body.put("type", "detach");
        body.put("username", username);

        Gson gson = new Gson();

        query.setQueryBody(gson.toJson(body));

        HttpQuery httpPost = new HttpPost(context);

        httpPost.runRightAway(query, new EmptyHook());
    }

    public void getListOfLinkedUsersInit() {
        Map<String, String> body = new HashMap<>();
        body.put("type", "getListOfLinkedUsers");

        Gson gson = new Gson();

        query.setQueryBody(gson.toJson(body));

        HttpQuery httpGet = new HttpGet(context);

    }

    public QueryData getQuery() {return query;}
}
