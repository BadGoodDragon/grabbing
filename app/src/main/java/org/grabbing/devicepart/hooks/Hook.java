package org.grabbing.devicepart.hooks;

import org.grabbing.devicepart.data.http.HttpQuery;
import org.grabbing.devicepart.domain.QueryData;

public interface Hook {
    public void capture(QueryData query);
}
