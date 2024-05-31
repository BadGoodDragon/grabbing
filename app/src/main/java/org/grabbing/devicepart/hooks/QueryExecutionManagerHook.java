package org.grabbing.devicepart.hooks;

import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.livedata.QueryDataListLive;

import java.util.List;

public class QueryExecutionManagerHook implements Hook {
    private QueryDataListLive data;
    private int currentQuantity;
    private int expectedQuantity;

    public QueryExecutionManagerHook(QueryDataListLive data) {
        this.data = data;
        this.expectedQuantity = data.get().size();
    }

    @Override
    public void capture(QueryData query) {
        data.add(query);
        currentQuantity++;
        if (currentQuantity >= expectedQuantity) {
            data.setStatus(true);
        }

    }

}
