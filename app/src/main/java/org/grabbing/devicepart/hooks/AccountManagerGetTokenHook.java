package org.grabbing.devicepart.hooks;

import android.util.Log;

import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.domain.StringStorage;

public class AccountManagerGetTokenHook implements Hook {
    private StringStorage token;
    private boolean hasResponse;

    public AccountManagerGetTokenHook(StringStorage token) {
        this.token = token;
        hasResponse = false;
    }

    @Override
    public void capture(QueryData query) {
        Log.i("Hook.AccountManagerGetTokenHook.capture * start", query.toString());
        token.setData(query.getResponseBody());
        hasResponse = true;
    }

    public boolean isHasResponse() {
        return hasResponse;
    }
}
