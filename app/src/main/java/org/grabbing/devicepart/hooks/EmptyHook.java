package org.grabbing.devicepart.hooks;

import android.util.Log;

import org.grabbing.devicepart.domain.QueryData;

public class EmptyHook implements Hook{
    @Override
    public void capture(QueryData query) {
        Log.i("Hook.EmptyHook.capture * start", String.valueOf(query.getStatusCode()));
    }
}
