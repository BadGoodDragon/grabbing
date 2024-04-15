package org.grabbing.devicepart.managers;

import android.content.Context;

import com.google.gson.Gson;

import org.grabbing.devicepart.data.http.HttpGet;
import org.grabbing.devicepart.data.http.HttpPost;
import org.grabbing.devicepart.data.http.HttpQuery;
import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.hooks.BooleanHook;
import org.grabbing.devicepart.hooks.EmptyHook;
import org.grabbing.devicepart.hooks.FaceManagerHook;
import org.grabbing.devicepart.hooks.StringHook;
import org.grabbing.devicepart.livedata.BooleanLive;
import org.grabbing.devicepart.livedata.ListOfUsersLive;
import org.grabbing.devicepart.livedata.StringLive;
import org.grabbing.devicepart.wrappers.BooleanWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FaceManager {
    private final Context context;
    private QueryData query;
    private ListOfUsersLive linkedUsers;
    private BooleanLive responses;
    private StringLive stringLive;

    public FaceManager(Context context) {
        this.context = context;
    }

    public void setQuery(QueryData query) {this.query = query;}
    public void setResponses(BooleanLive responses) {this.responses = responses;}
    public void setStringLive(StringLive stringLive) {this.stringLive = stringLive;}

    public void register(String name) {
        Map<String, String> body = new HashMap<>();
        body.put("type", "register");
        body.put("name", name);

        Gson gson = new Gson();

        query.setQueryBody(gson.toJson(body));

        HttpQuery httpPost = new HttpPost(context);

        httpPost.runRightAway(query, new BooleanHook(responses));

    }
    public void attach(String username) {
        Map<String, String> body = new HashMap<>();
        body.put("type", "attach");
        body.put("username", username);

        Gson gson = new Gson();

        query.setQueryBody(gson.toJson(body));

        HttpQuery httpPost = new HttpPost(context);

        httpPost.runRightAway(query, new BooleanHook(responses));
    }
    public void detach(String username) {
        Map<String, String> body = new HashMap<>();
        body.put("type", "detach");
        body.put("username", username);

        Gson gson = new Gson();

        query.setQueryBody(gson.toJson(body));

        HttpQuery httpPost = new HttpPost(context);

        httpPost.runRightAway(query, new BooleanHook(responses));
    }

    public void getCurrentName() {
        Map<String, String> body = new HashMap<>();
        body.put("type", "getCurrentName");

        Gson gson = new Gson();

        query.setQueryBody(gson.toJson(body));

        HttpQuery httpPost = new HttpPost(context);

        httpPost.runRightAway(query, new StringHook(stringLive));
    }

    public void setLinkedUsers(ListOfUsersLive linkedUsers) {this.linkedUsers = linkedUsers;}

    public void getListOfLinkedUsers() {
        Map<String, String> body = new HashMap<>();
        body.put("type", "getListOfLinkedUsers");

        Gson gson = new Gson();

        query.setQueryBody(gson.toJson(body));

        FaceManagerHook hook = new FaceManagerHook(linkedUsers);

        HttpQuery httpGet = new HttpGet(context);
        httpGet.runRightAway(query, hook);
    }


    public QueryData getQuery() {return query;}
}
