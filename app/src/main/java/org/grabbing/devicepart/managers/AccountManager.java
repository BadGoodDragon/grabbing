package org.grabbing.devicepart.managers;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.grabbing.devicepart.data.http.HttpPost;
import org.grabbing.devicepart.data.http.HttpQuery;
import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.hooks.BooleanHook;
import org.grabbing.devicepart.livedata.BooleanLive;
import org.grabbing.devicepart.livedata.StringLive;
import org.grabbing.devicepart.hooks.AccountManagerHook;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class AccountManager {
    private final Context context;
    private StringLive token;
    private BooleanLive booleanLive;
    private QueryData query;
    private AccountManagerHook hook;


    public AccountManager(Context context) {
        this.context = context;
    }

    public void setQuery(QueryData query) {this.query = query;}
    public void setToken(StringLive token) {this.token = token;}
    public void setBooleanLive(BooleanLive booleanLive) {this.booleanLive = booleanLive;}
    public void setToken(String token) {
        this.token.setData(token);
        this.token.setStatus(true);
    }

    public void generateToken(String username, String password) {
        Map<String, String> body = new HashMap<>();
        body.put("type", "generateToken");

        String usernameAndPassword = username + ":" + password;

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Basic " + Base64.getEncoder().encodeToString(usernameAndPassword.getBytes()));

        Gson gson = new Gson();

        query.setQueryBody(gson.toJson(body));
        query.setAuthorizationHeaders(headers);
        query.setAddedUrl("/generatetoken");

        HttpQuery httpPost = new HttpPost(context);

        hook = new AccountManagerHook(token);

        httpPost.runRightAway(query, hook);
    }
    public void register(String username, String password) {
        Map<String, String> body = new HashMap<>();
        body.put("type", "/register");

        String usernameAndPassword = username + ":" + password;

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Basic " + Base64.getEncoder().encodeToString(usernameAndPassword.getBytes()));

        Gson gson = new Gson();

        query.setQueryBody(gson.toJson(body));
        query.setAuthorizationHeaders(headers);
        query.setAddedUrl("/register");

        HttpQuery httpPost = new HttpPost(context);

        httpPost.runRightAway(query, new BooleanHook(booleanLive));
    }
    public QueryData authorizeQuery(QueryData unauthorizedQuery) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token.getData());

        unauthorizedQuery.setAuthorizationHeaders(headers);

        return unauthorizedQuery;
    }

    public static QueryData authorizeQuery(QueryData unauthorizedQuery, String token) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);

        unauthorizedQuery.setAuthorizationHeaders(headers);

        return unauthorizedQuery;
    }

}
