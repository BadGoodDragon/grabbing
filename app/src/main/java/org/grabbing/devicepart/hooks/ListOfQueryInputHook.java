package org.grabbing.devicepart.hooks;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.dto.QueryInput;
import org.grabbing.devicepart.livedata.TypeLive;

import java.util.ArrayList;
import java.util.List;

public class ListOfQueryInputHook implements Hook {
    private final TypeLive<List<QueryInput>> typeLive;

    public ListOfQueryInputHook(TypeLive<List<QueryInput>> typeLive) {
        this.typeLive = typeLive;
    }

    @Override
    public void capture(QueryData query) {
        Gson gson = new Gson();

        List<QueryInput> data;

        data = gson.fromJson(query.getResponseBody(), new TypeToken<ArrayList<QueryInput>>(){}.getType());


        if (data == null) {
            typeLive.setDefaultData();
        } else {
            typeLive.setData(data);
        }

        typeLive.setStatus(true);
    }
}
