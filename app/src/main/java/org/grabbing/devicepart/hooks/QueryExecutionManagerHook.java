package org.grabbing.devicepart.hooks;

import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.livedata.TypeLive;

import java.util.List;

public class QueryExecutionManagerHook implements Hook {
    private final TypeLive<List<QueryData>> data;
    private int currentQuantity;
    private final int expectedQuantity;

    public QueryExecutionManagerHook(TypeLive<List<QueryData>> data, int expectedQuantity) {
        this.data = data;
        this.expectedQuantity = expectedQuantity;
    }

    @Override
    public void capture(QueryData query) {
        List<QueryData> dataList = data.getData();
        dataList.add(query);
        data.setData(dataList);
        currentQuantity++;
        if (currentQuantity >= expectedQuantity) {
            data.setStatus(true);
        }

    }

}
