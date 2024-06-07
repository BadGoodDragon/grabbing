package org.grabbing.devicepart.hooks;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.dto.MyQuery;
import org.grabbing.devicepart.livedata.TypeLive;

import java.util.ArrayList;
import java.util.List;

public class ListOfMyQueryHook implements Hook {
    private final TypeLive<List<MyQuery>> typeLive;

    public ListOfMyQueryHook(TypeLive<List<MyQuery>> typeLive) {
        this.typeLive = typeLive;
    }

    @Override
    public void capture(QueryData query) {
        Gson gson = new Gson();

        List<MyQuery> data;

        data = gson.fromJson(query.getResponseBody(), new TypeToken<ArrayList<MyQuery>>(){}.getType());


        if (data == null) {
            typeLive.setDefaultData();
        } else {
            typeLive.setData(data);
        }

        typeLive.setStatus(true);
    }
}
