package org.grabbing.devicepart.managers;

import android.content.Context;

import com.google.gson.Gson;

import org.grabbing.devicepart.data.http.HttpPost;
import org.grabbing.devicepart.data.http.HttpQuery;
import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.hooks.EmptyHook;
import org.grabbing.devicepart.dto.ResponseOutput;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendingResultManager {
    private final Context context;
    private final String token;


    private static final String queryUrl = "http://quickqueries.org/returningresults";

    public SendingResultManager(Context context, String token) {
        this.context = context;
        this.token = token;
    }

    public void run(List<ResponseOutput> data) {
        QueryData query = new QueryData(queryUrl, -1);

        Gson gson = new Gson();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);
        query.setAuthorizationHeaders(headers);

        query.setQueryBody(gson.toJson(data));
        query.setAddedUrl("/send");

        HttpQuery httpPost = new HttpPost(context);

        httpPost.runRightAway(query, new EmptyHook());
    }
}
