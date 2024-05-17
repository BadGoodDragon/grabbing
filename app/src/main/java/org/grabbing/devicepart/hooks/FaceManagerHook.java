package org.grabbing.devicepart.hooks;

import android.util.Log;

import com.google.gson.Gson;

import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.livedata.ListOfUsersLive;
import org.grabbing.devicepart.wrappers.BooleanWrapper;
import org.grabbing.devicepart.wrappers.QuickCompletion;

import java.util.ArrayList;
import java.util.List;

public class FaceManagerHook implements Hook{
    private ListOfUsersLive linkedUsers;

    public FaceManagerHook(ListOfUsersLive linkedUsers) {
        this.linkedUsers = linkedUsers;
    }

    @Override
    public void capture(QueryData query) {
        Gson gson = new Gson();

        try {
            linkedUsers.replace(gson.fromJson(query.getResponseBody(), ArrayList.class));
            linkedUsers.setStatus(true);
        } catch (Exception e) {
            linkedUsers.replace(new ArrayList<>());
            linkedUsers.setStatus(true);
        }
    }

}
