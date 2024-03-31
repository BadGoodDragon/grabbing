package org.grabbing.devicepart.data.http;

import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.hooks.Hook;

import java.util.List;

public interface HttpQuery {
    public void setData(List<QueryData> data);
    public void setHook(Hook hook);
    public void run();
    public List<QueryData> getData();
    public void runRightAway(QueryData query, Hook hook);
}
