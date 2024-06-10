package org.grabbing.devicepart.managers;

import android.content.Context;

import org.grabbing.devicepart.data.http.HttpGet;
import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.hooks.TypeHook;
import org.grabbing.devicepart.livedata.TypeLive;

import java.util.HashMap;
import java.util.Map;

public class CheckManager {
    private final Context context;
    private final String token;

    private static final String queryUrl = "http://quickqueries.org/checkmanagement";


    public CheckManager(Context context, String token) {
        this.context = context;
        this.token = token;
    }

    public void check(TypeLive<Integer> typeLive) { // 0 - нет никакой авторизации  1 - успешная авторизация  2 - успешная авторизация лица
        QueryData queryData = new QueryData(queryUrl, -1);

        queryData.setAddedUrl("/check");

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        queryData.setAuthorizationHeaders(headers);

        HttpGet httpGet = new HttpGet(context);

        httpGet.runRightAway(queryData, new TypeHook<Integer>(typeLive, Integer.class));
    }
}
