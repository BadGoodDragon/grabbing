package org.grabbing.devicepart.converters;

import org.grabbing.devicepart.domain.QueryData;

import java.util.ArrayList;
import java.util.List;

public class JsonToListOfQueryData {
    private List<QueryData> data;
    private String json;

    public void setJson(String json) {
        this.json = json;
    }

    public void convert() {
        data = new ArrayList<>();





    }

    public List<QueryData> getData() {
        return data;
    }
}
