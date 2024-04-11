package org.grabbing.devicepart.managers;

import android.content.Context;

import org.grabbing.devicepart.data.http.HttpGet;
import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.hooks.BooleanHook;
import org.grabbing.devicepart.hooks.IntegerHook;
import org.grabbing.devicepart.livedata.IntegerLive;

public class CheckManager {
    private final Context context;

    private QueryData query;
    IntegerLive integerLive;


    public CheckManager(Context context) {
        this.context = context;
    }

    public void setQuery(QueryData query) {this.query = query;}
    public QueryData getQuery() {return query;}

    public void setIntegerLive(IntegerLive integerLive) {this.integerLive = integerLive;}

    public void check() { // 0 - нет никакой авторизации  1 - успешная авторизация  2 - успешная авторизация лица
        integerLive.clearAll();

        if (query.getAuthorizationHeaders().isEmpty()) {
            integerLive.setData(0);
            integerLive.setStatus(true);
            return;
        }

        HttpGet httpGet = new HttpGet(context);

        httpGet.runRightAway(query, new IntegerHook(integerLive));
    }
}
