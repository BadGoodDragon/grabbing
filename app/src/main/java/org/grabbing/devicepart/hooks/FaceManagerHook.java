package org.grabbing.devicepart.hooks;

import com.google.gson.Gson;

import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.wrappers.BooleanWrapper;
import org.grabbing.devicepart.wrappers.QuickCompletion;

import java.util.ArrayList;
import java.util.List;

public class FaceManagerHook implements Hook{
    private List<String> linkedUsers;
    boolean isReady;

    public FaceManagerHook(List<String> linkedUsers) {
        this.linkedUsers = linkedUsers;
        this.isReady = false;
    }

    @Override
    public void capture(QueryData query) {
        Gson gson = new Gson();

        linkedUsers = gson.fromJson(query.getResponseBody(), ArrayList.class);

        isReady = true;
    }

    public List<String> getLinkedUsers() {
        return linkedUsers;
    }

    public boolean isHasResponse() {
        return isReady;
    }
}
