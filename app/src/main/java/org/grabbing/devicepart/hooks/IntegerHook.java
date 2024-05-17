package org.grabbing.devicepart.hooks;

import android.util.Log;

import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.livedata.IntegerLive;

public class IntegerHook implements Hook {
    private IntegerLive integerLive;

    public IntegerHook(IntegerLive integerLive) {
        this.integerLive = integerLive;
    }

    @Override
    public void capture(QueryData query) {
        Log.i("DATA", query.getUrl());
        Log.i("DATA", query.getResponseBody());
        Log.i("DATA", String.valueOf(query.getStatusCode()));
        if (query.isError()) {
            integerLive.setData(0);
            integerLive.setStatus(true);
            return;
        }
        try {
            integerLive.setData(Integer.valueOf(query.getResponseBody()));
        } catch (Exception e) {
            e.printStackTrace();
            integerLive.setData(0);
        }
        integerLive.setStatus(true);
    }
}
