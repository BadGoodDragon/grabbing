package org.grabbing.devicepart.hooks;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.livedata.TypeLive;


public class TypeHook<Type> implements Hook {
    private final TypeLive<Type> typeLive;

    public TypeHook(TypeLive<Type> typeLive) {
        this.typeLive = typeLive;
    }

    @Override
    public void capture(QueryData query) {
        Gson gson = new Gson();

        Type type = gson.fromJson(query.getResponseBody(), new TypeToken<Type>(){}.getType());

        if (type == null) {
            typeLive.setDefaultData();
        } else {
            typeLive.setData(type);
        }

        typeLive.setStatus(true);
    }
}
