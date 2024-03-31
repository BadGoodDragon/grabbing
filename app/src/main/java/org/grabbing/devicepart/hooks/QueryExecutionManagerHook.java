package org.grabbing.devicepart.hooks;

import org.grabbing.devicepart.domain.QueryData;

import java.util.List;

public class QueryExecutionManagerHook implements Hook {
    private List<QueryData> data;
    private int currentQuantity;

    public QueryExecutionManagerHook() {
        currentQuantity = 0;
    }
    public QueryExecutionManagerHook(List<QueryData> data) {
        this.data = data;
    }

    public void setData(List<QueryData> data) {this.data = data;}

    @Override
    public void capture(QueryData query) {
        data.add(query);
        currentQuantity++;
    }

    public int getCurrentQuantity() {return currentQuantity;}
    public List<QueryData> getData() {return data;}
}
