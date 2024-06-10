package org.grabbing.devicepart.hooks;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.livedata.TypeLive;


public class TypeHook<Type> implements Hook {
    private final TypeLive<Type> typeLive;
    private final Class<Type> typeClass;

    public TypeHook(TypeLive<Type> typeLive, Class<Type> typeClass) {
        this.typeLive = typeLive;
        this.typeClass = typeClass;
    }

    public TypeHook(TypeLive<Type> typeLive) {
        this.typeLive = typeLive;
        this.typeClass = null;
    }

    @Override
    public void capture(QueryData query) {
        Gson gson = new Gson();
        Type type;
        if (typeClass != null) {
            type = gson.fromJson(query.getResponseBody(), typeClass);
        } else {
            type = gson.fromJson(query.getResponseBody(), new TypeToken<Type>(){}.getType());
        }

        if (type == null) {
            typeLive.setDefaultData();
        } else {
            typeLive.setData(type);
        }

        typeLive.setStatus(true);
    }
}
