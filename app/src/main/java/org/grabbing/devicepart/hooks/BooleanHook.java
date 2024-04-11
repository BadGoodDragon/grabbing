package org.grabbing.devicepart.hooks;

import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.livedata.BooleanLive;

import java.util.Objects;

public class BooleanHook implements Hook {
    private BooleanLive booleanLive;

    public BooleanHook(BooleanLive booleanLive) {
        this.booleanLive = booleanLive;
    }

    @Override
    public void capture(QueryData query) {
        booleanLive.setData(Objects.equals(query.getResponseBody(), "true"));
        booleanLive.setStatus(true);
    }
}
