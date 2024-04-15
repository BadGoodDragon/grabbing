package org.grabbing.devicepart.livedata;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StringLive extends ViewModel {
    private final MutableLiveData<String> data = new MutableLiveData<>();
    private final MutableLiveData<Boolean> status = new MutableLiveData<>();

    public void clearAll() {
        data.postValue("");
        this.status.postValue(false);
    }

    public void setData(String token) {this.data.postValue(token);}
    public String getData() {return data.getValue();}

    public void setStatus(boolean status) {this.status.postValue(status);}
    public boolean getStatus() {return Boolean.TRUE.equals(this.status.getValue());}

    public MutableLiveData<String> getDataLive() {return data;}
    public MutableLiveData<Boolean> getStatusLive() {return status;}
}
