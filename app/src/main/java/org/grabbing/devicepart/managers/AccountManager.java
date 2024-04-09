package org.grabbing.devicepart.managers;

import android.content.Context;

import com.google.gson.Gson;

import org.grabbing.devicepart.data.http.HttpPost;
import org.grabbing.devicepart.data.http.HttpQuery;
import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.livedata.TokenLive;
import org.grabbing.devicepart.wrappers.StringStorage;
import org.grabbing.devicepart.hooks.AccountManagerHook;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class AccountManager {
    private final Context context;
    private TokenLive token;
    private QueryData query;
    private AccountManagerHook hook;


    public AccountManager(Context context) {
        this.context = context;
    }

    public void setQuery(QueryData query) {this.query = query;}
    public void setToken(TokenLive token) {this.token = token;}
    public void setToken(String token) {this.token.setToken(token);}

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

        hook = new AccountManagerHook(token);

        httpPost.runRightAway(query, hook);
    }
    public QueryData authorizeQuery(QueryData unauthorizedQuery) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token.getToken());

        unauthorizedQuery.setAuthorizationHeaders(headers);

        return unauthorizedQuery;
    }

}
