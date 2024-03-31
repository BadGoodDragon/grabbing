package org.grabbing.devicepart.hooks;

import org.grabbing.devicepart.converters.JsonToListOfQueryData;
import org.grabbing.devicepart.domain.QueryData;

import java.util.List;

public class QueryReceiptManagerHook implements Hook {
    private List<QueryData> data;
    private boolean hasResponse;

    public QueryReceiptManagerHook(List<QueryData> data) {
        this.data = data;
        hasResponse = false;
    }

    @Override
    public void capture(QueryData query) {
        JsonToListOfQueryData jsonToListOfQueryData = new JsonToListOfQueryData();

        jsonToListOfQueryData.setJson(query.getResponseBody());
        jsonToListOfQueryData.convert(data);

        hasResponse = true;
    }

    public boolean isHasResponse() {
        return hasResponse;
    }
}
