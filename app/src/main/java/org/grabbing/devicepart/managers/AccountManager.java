package org.grabbing.devicepart.managers;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.grabbing.devicepart.data.http.HttpGet;
import org.grabbing.devicepart.data.http.HttpPost;
import org.grabbing.devicepart.data.http.HttpQuery;
import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.domain.StringStorage;
import org.grabbing.devicepart.hooks.AccountManagerGetTokenHook;
import org.grabbing.devicepart.hooks.Hook;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountManager {
    private final Context context;
    private StringStorage token;
    private QueryData query;
    private AccountManagerGetTokenHook hook;


    public AccountManager(Context context) {
        this.context = context;
        this.token = new StringStorage();
    }

    public void setQuery(QueryData query) {this.query = query;}
    public void setToken(String token) {this.token.setData(token);}

    public void generateToken(String username, String password) {
        Map<String, String> body = new HashMap<>();
        body.put("type", "generateToken");

        String usernameAndPassword = username + ":" + password;

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Basic " + Base64.getEncoder().encodeToString(usernameAndPassword.getBytes()));

        Gson gson = new Gson();

        query.setQueryBody(gson.toJson(body));
        query.setAuthorizationHeaders(headers);

        HttpQuery httpPost = new HttpPost(context);

        hook = new AccountManagerGetTokenHook(token);

        httpPost.runRightAway(query, hook);
    }
    public QueryData authorizeQuery(QueryData unauthorizedQuery) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token.getData());

        unauthorizedQuery.setAuthorizationHeaders(headers);

        return unauthorizedQuery;
    }

    public String getToken() {return token.getData();}
    public boolean isHasResponse() {return hook.isHasResponse();}
}
