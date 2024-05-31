package org.grabbing.devicepart.hooks;

import android.util.Log;

import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.livedata.StringLive;

public class AccountManagerHook implements Hook {
    private StringLive token;

    public AccountManagerHook(StringLive token) {
        this.token = token;
    }

    @Override
    public void capture(QueryData query) {
        Log.i("Hook.AccountManagerGetTokenHook.capture * start", query.toString());
        token.setData(query.getResponseBody());
        token.setStatus(true);
    }
}
