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
        query.setQueryBody(name);
        query.setAddedUrl("/register");

        HttpQuery httpPost = new HttpPost(context);

        httpPost.runRightAway(query, new BooleanHook(responses));

    }
    public void attach(String username) {
        query.setQueryBody(username);
        query.setAddedUrl("/attach");

        HttpQuery httpPost = new HttpPost(context);

        httpPost.runRightAway(query, new BooleanHook(responses));
    }
    public void detach(String username) {
        query.setQueryBody(username);
        query.setAddedUrl("/detach");

        HttpQuery httpPost = new HttpPost(context);

        httpPost.runRightAway(query, new BooleanHook(responses));
    }

    public void getCurrentName() {
        query.setQueryBody("");
        query.setAddedUrl("/getcurrentname");

        HttpQuery httpPost = new HttpPost(context);

        httpPost.runRightAway(query, new StringHook(stringLive));
    }

    public void setLinkedUsers(ListOfUsersLive linkedUsers) {this.linkedUsers = linkedUsers;}

    public void getListOfLinkedUsers() {
        query.setQueryBody("");
        query.setAddedUrl("/getlistoflinkedusers");

        FaceManagerHook hook = new FaceManagerHook(linkedUsers);

        HttpQuery httpPost = new HttpPost(context);
        httpPost.runRightAway(query, hook);
    }


    public QueryData getQuery() {return query;}
}
