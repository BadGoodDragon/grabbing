package org.grabbing.devicepart.data.http;

import org.grabbing.devicepart.domain.QueryData;

import java.util.List;

public interface HttpQuery {
    public void setData(List<QueryData> data);
    public void run();
    public List<QueryData> getData();
}
