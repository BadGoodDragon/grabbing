package org.grabbing.devicepart.livedata;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.grabbing.devicepart.domain.QueryData;

import java.util.ArrayList;
import java.util.List;

public class QueryDataListLive extends ViewModel {
    private final MutableLiveData<List<QueryData>> queryDataList = new MutableLiveData<>();
    private final MutableLiveData<Boolean> status = new MutableLiveData<>();

    public void clearAll() {
        queryDataList.postValue(new ArrayList<>());
        this.status.postValue(false);
    }

    public void clear() {
        queryDataList.postValue(new ArrayList<>());
    }
    public void add(QueryData queryData) {
        List<QueryData> list = queryDataList.getValue();
        list.add(queryData);
        queryDataList.postValue(list);
    }
    public void replace(List<QueryData> list) {
        queryDataList.postValue(list);
    }
    public List<QueryData> get() {
        return queryDataList.getValue();
    }

    public void setStatus(boolean status) {
        this.status.postValue(status);
    }
    public boolean getStatus() {
        return Boolean.TRUE.equals(this.status.getValue());
    }

    public MutableLiveData<List<QueryData>> getListLive() {return queryDataList;}
    public MutableLiveData<Boolean> getStatusLive() {return status;}
}
