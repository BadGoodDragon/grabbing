package org.grabbing.devicepart.managers;

import android.content.Context;

import com.google.gson.Gson;

import org.grabbing.devicepart.data.http.HttpPost;
import org.grabbing.devicepart.data.http.HttpQuery;
import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.hooks.EmptyHook;
import org.grabbing.devicepart.dto.ResponseOutput;

import java.util.List;

public class SendingResultManager {
    private final Context context;
    private final String token;


    private static final String queryUrl = "http://192.168.0.75:8090/returningresults";

    public SendingResultManager(Context context, String token) {
        this.context = context;
        this.token = token;
    }

    public void run(List<ResponseOutput> data) {
        QueryData query = new QueryData(queryUrl, -1);

        Gson gson = new Gson();

        query.setQueryBody(gson.toJson(data));
        query.setAddedUrl("/send");

        HttpQuery httpPost = new HttpPost(context);

        httpPost.runRightAway(query, new EmptyHook());
    }
}
