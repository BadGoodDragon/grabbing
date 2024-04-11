package org.grabbing.devicepart.livedata;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class IntegerLive extends ViewModel {
    private final MutableLiveData<Integer> data = new MutableLiveData<>();
    private final MutableLiveData<Boolean> status = new MutableLiveData<>();


    public int getData() {return data.getValue();}
    public boolean getStatus() {return Boolean.TRUE.equals(status.getValue());}

    public void setData(int data){this.data.postValue(data);}
    public void setStatus(boolean status){this.status.postValue(status);}

    public void clearAll() {
        data.postValue(0);
        status.postValue(false);
    }

    public MutableLiveData<Integer> getDataLive() {return data;}
    public MutableLiveData<Boolean> getStatusLive() {return status;}
}
