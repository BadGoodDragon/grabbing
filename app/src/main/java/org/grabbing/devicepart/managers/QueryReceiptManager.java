package org.grabbing.devicepart.managers;

import android.content.Context;

import org.grabbing.devicepart.data.http.HttpGet;
import org.grabbing.devicepart.data.http.HttpQuery;
import org.grabbing.devicepart.domain.QueryData;

import org.grabbing.devicepart.dto.QueryInput;
import org.grabbing.devicepart.hooks.ListOfQueryInputHook;
import org.grabbing.devicepart.livedata.TypeLive;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryReceiptManager {
    private final Context context;
    private final String token;

    private static final String queryUrl = "http://192.168.0.75:8090/receivingqueries";

    public QueryReceiptManager(Context context, String token) {
        this.context = context;
        this.token = token;
    }

    public void run(TypeLive<List<QueryInput>> typeLive) {
        QueryData query = new QueryData(queryUrl, -1);

        query.setAddedUrl("/receive");

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        query.setAuthorizationHeaders(headers);

        HttpQuery httpGet = new HttpGet(context);
        httpGet.runRightAway(query, new ListOfQueryInputHook(typeLive));
    }

}
