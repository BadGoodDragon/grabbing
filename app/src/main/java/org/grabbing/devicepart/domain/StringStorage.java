package org.grabbing.devicepart.domain;

import androidx.annotation.Nullable;

public class StringStorage {
    private String data;

    public StringStorage() {
        data = "";
    }
    public StringStorage(String data) {
        this.data = data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
