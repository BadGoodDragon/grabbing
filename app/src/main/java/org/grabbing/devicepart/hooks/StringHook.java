package org.grabbing.devicepart.hooks;

import android.util.Log;

import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.livedata.StringLive;

public class StringHook implements Hook {
    private StringLive data;

    public StringHook(StringLive token) {
        this.data = token;
    }

    @Override
    public void capture(QueryData query) {
        data.setData(query.getResponseBody());
        data.setStatus(true);
    }
}
