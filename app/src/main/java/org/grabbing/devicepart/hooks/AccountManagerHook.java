package org.grabbing.devicepart.hooks;

import android.util.Log;

import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.livedata.TokenLive;
import org.grabbing.devicepart.wrappers.StringStorage;

public class AccountManagerHook implements Hook {
    private TokenLive token;

    public AccountManagerHook(TokenLive token) {
        this.token = token;
    }

    @Override
    public void capture(QueryData query) {
        Log.i("Hook.AccountManagerGetTokenHook.capture * start", query.toString());
        token.setToken(query.getResponseBody());
        token.setStatus(true);
    }
}
