package org.grabbing.devicepart.livedata;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class BooleanLive extends ViewModel {
    private final MutableLiveData<Boolean> data = new MutableLiveData<>();
    private final MutableLiveData<Boolean> status = new MutableLiveData<>();

    public boolean getData() {return Boolean.TRUE.equals(data.getValue());}
    public boolean getStatus() {return Boolean.TRUE.equals(status.getValue());}

    public void setData(boolean data){this.data.postValue(data);}
    public void setStatus(boolean status){this.status.postValue(status);}

    public void clearAll() {
        data.postValue(false);
        status.postValue(false);
    }

    public MutableLiveData<Boolean> getDataLive() {return data;}
    public MutableLiveData<Boolean> getStatusLive() {return status;}
}
