package org.grabbing.devicepart.managers;

import android.content.Context;

import com.google.gson.Gson;

import org.grabbing.devicepart.data.http.HttpGet;
import org.grabbing.devicepart.data.http.HttpPost;
import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.dto.MyQuery;
import org.grabbing.devicepart.dto.NewQuery;
import org.grabbing.devicepart.dto.QueryVisual;
import org.grabbing.devicepart.hooks.EmptyHook;
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

    public void add(NewQuery newQuery) {
        QueryData queryData = new QueryData(queryUrl, -1);

        queryData.setAddedUrl("/add");

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        queryData.setAuthorizationHeaders(headers);

        Gson gson = new Gson();

        queryData.setQueryBody(gson.toJson(newQuery));

        HttpPost httpPost = new HttpPost(context);

        httpPost.runRightAway(queryData, new EmptyHook());
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

    public void getQuery(TypeLive<QueryVisual> typeLive, long id) {
        QueryData queryData = new QueryData(queryUrl, -1);

        queryData.setAddedUrl("/getquery");

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);

        headers.put("X-ID", String.valueOf(id));
        queryData.setAuthorizationHeaders(headers);

        HttpGet httpGet = new HttpGet(context);

        httpGet.runRightAway(queryData, new TypeHook<QueryVisual>(typeLive, QueryVisual.class));
    }
}
