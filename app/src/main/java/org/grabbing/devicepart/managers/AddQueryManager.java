package org.grabbing.devicepart.managers;

import android.content.Context;

import org.grabbing.devicepart.data.http.HttpGet;
import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.dto.MyQuery;
import org.grabbing.devicepart.hooks.ListOfMyQueryHook;
import org.grabbing.devicepart.hooks.TypeHook;
import org.grabbing.devicepart.livedata.TypeLive;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddQueryManager {
    private final Context context;
    private final String token;

    private static final String queryUrl = "http://192.168.0.75:8090/addingqueries";

    public AddQueryManager(Context context, String token) {
        this.context = context;
        this.token = token;
    }

    public void getDone(TypeLive<List<MyQuery>> typeLive) {
        QueryData queryData = new QueryData(queryUrl, -1);

        queryData.setAddedUrl("/getdone");

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        queryData.setAuthorizationHeaders(headers);

        HttpGet httpGet = new HttpGet(context);

        httpGet.runRightAway(queryData, new ListOfMyQueryHook(typeLive));
    }

    public void getInProcess(TypeLive<List<MyQuery>> typeLive) {
        QueryData queryData = new QueryData(queryUrl, -1);

        queryData.setAddedUrl("/getinprocess");

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        queryData.setAuthorizationHeaders(headers);

        HttpGet httpGet = new HttpGet(context);

        httpGet.runRightAway(queryData, new ListOfMyQueryHook(typeLive));
    }
}
