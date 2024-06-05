package org.grabbing.devicepart.managers;

import android.content.Context;


import com.google.gson.reflect.TypeToken;

import org.grabbing.devicepart.data.http.HttpPost;
import org.grabbing.devicepart.data.http.HttpQuery;
import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.hooks.TypeHook;
import org.grabbing.devicepart.livedata.TypeLive;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FaceManager {
    private final Context context;
    private final String token;

    private static final String queryUrl = "http://192.168.0.75:8090/facemanagement";


    public FaceManager(Context context, String token) {
        this.context = context;
        this.token = token;
    }


    public void register(String name, TypeLive<Boolean> typeLive) {
        QueryData query = new QueryData(queryUrl, -1);

        query.setQueryBody(name);
        query.setAddedUrl("/register");

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        query.setAuthorizationHeaders(headers);

        HttpQuery httpPost = new HttpPost(context);

        httpPost.runRightAway(query, new TypeHook<Boolean>(typeLive, Boolean.class));
    }
    public void attach(String username, TypeLive<Boolean> typeLive) {
        QueryData query = new QueryData(queryUrl, -1);

        query.setQueryBody(username);
        query.setAddedUrl("/attach");

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        query.setAuthorizationHeaders(headers);

        HttpQuery httpPost = new HttpPost(context);

        httpPost.runRightAway(query, new TypeHook<Boolean>(typeLive, Boolean.class));
    }
    public void detach(String username, TypeLive<Boolean> typeLive) {
        QueryData query = new QueryData(queryUrl, -1);

        query.setQueryBody(username);
        query.setAddedUrl("/detach");

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        query.setAuthorizationHeaders(headers);

        HttpQuery httpPost = new HttpPost(context);

        httpPost.runRightAway(query, new TypeHook<Boolean>(typeLive, Boolean.class));
    }

    public void getCurrentName(TypeLive<String> typeLive) {
        QueryData query = new QueryData(queryUrl, -1);

        query.setAddedUrl("/getcurrentname");

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        query.setAuthorizationHeaders(headers);

        HttpQuery httpPost = new HttpPost(context);

        httpPost.runRightAway(query, new TypeHook<String>(typeLive, String.class));
    }

    public void getListOfLinkedUsers(TypeLive<List<String>> typeLive) {
        QueryData query = new QueryData(queryUrl, -1);

        query.setAddedUrl("/getlistoflinkedusers");

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        query.setAuthorizationHeaders(headers);

        HttpQuery httpPost = new HttpPost(context);

        httpPost.runRightAway(query, new TypeHook<List<String>>(typeLive));
    }

}
