package org.grabbing.devicepart.hooks;

import org.grabbing.devicepart.converters.JsonToListOfQueryData;
import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.livedata.QueryDataListLive;

import java.util.ArrayList;
import java.util.List;

public class QueryReceiptManagerHook implements Hook {
    private QueryDataListLive data;

    public QueryReceiptManagerHook(QueryDataListLive data) {
        this.data = data;
    }

    @Override
    public void capture(QueryData query) {
        JsonToListOfQueryData jsonToListOfQueryData = new JsonToListOfQueryData();

        List<QueryData> localData = new ArrayList<>();

        jsonToListOfQueryData.setJson(query.getResponseBody());
        jsonToListOfQueryData.convert(localData);

        data.replace(localData);
        data.setStatus(true);
    }

}
