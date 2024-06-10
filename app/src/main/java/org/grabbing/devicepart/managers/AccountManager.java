package org.grabbing.devicepart.managers;

import android.content.Context;

import com.google.gson.Gson;

import org.grabbing.devicepart.data.http.HttpPost;
import org.grabbing.devicepart.data.http.HttpQuery;
import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.hooks.TypeHook;
import org.grabbing.devicepart.livedata.TypeLive;

import java.util.HashMap;
import java.util.Map;

public class AccountManager {
    private final Context context;
    private static final String queryUrl = "http://quickqueries.org/accountmanagement";


    public AccountManager(Context context) {
        this.context = context;
    }

    public void setQuery(QueryData query) {}

    public void generateToken(String username, String password, TypeLive<String> typeLive, TypeLive<Integer> statusCode) {
        QueryData query = new QueryData(queryUrl, -1);

        Map<String, String> body = new HashMap<>();
        body.put("username", username);
        body.put("password", password);

        Gson gson = new Gson();

        query.setQueryBody(gson.toJson(body));
        query.setAddedUrl("/generatetoken");

        HttpQuery httpPost = new HttpPost(context);

        httpPost.runRightAway(query, new TypeHook<String>(typeLive, String.class), statusCode);
    }
    public void register(String username, String password, TypeLive<Boolean> typeLive) {
        QueryData query = new QueryData(queryUrl, -1);

        Map<String, String> headers = new HashMap<>();
        headers.put("X-Username", username);
        headers.put("X-Password", password);

        query.setRegistrationHeaders(headers);
        query.setAddedUrl("/register");

        HttpQuery httpPost = new HttpPost(context);

        httpPost.runRightAway(query, new TypeHook<Boolean>(typeLive, Boolean.class));
    }

    public static QueryData authorizeQuery(QueryData unauthorizedQuery, String token) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);

        unauthorizedQuery.setAuthorizationHeaders(headers);

        return unauthorizedQuery;
    }



}


/*public class AccountManager {
    private final Context context;
    private StringLive token;
    private BooleanLive booleanLive;
    private QueryData query;
    private TypeHook<String> hook;


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
        QueryData query = new QueryData(this.query);

        Map<String, String> body = new HashMap<>();
        body.put("username", username);
        body.put("password", password);

        Gson gson = new Gson();

        query.setQueryBody(gson.toJson(body));
        query.setAddedUrl("/generatetoken");

        HttpQuery httpPost = new HttpPost(context);

        hook = new AccountManagerHook(token);

        httpPost.runRightAway(query, hook);
    }
    public void register(String username, String password) {
        QueryData query = new QueryData(this.query);

        Map<String, String> headers = new HashMap<>();
        headers.put("X-Username", username);
        headers.put("X-Password", password);

        query.setRegistrationHeaders(headers);
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

}*/
