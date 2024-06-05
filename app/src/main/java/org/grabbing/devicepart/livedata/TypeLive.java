package org.grabbing.devicepart.livedata;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TypeLive<Type> extends ViewModel {
    private final MutableLiveData<Type> data = new MutableLiveData<>();
    private final MutableLiveData<Boolean> status = new MutableLiveData<>();
    private final Type defaultValue;

    public TypeLive(Type defaultValue) {
        this.defaultValue = defaultValue;

        data.postValue(defaultValue);
        status.postValue(false);
    }


    public Type getData() {return data.getValue();}
    public boolean getStatus() {return Boolean.TRUE.equals(status.getValue());}

    public void setData(Type data){this.data.postValue(data);}
    public void setStatus(boolean status){this.status.postValue(status);}

    public void setDefaultData() {this.data.postValue(defaultValue);}

    public void clearAll() {
        data.postValue(defaultValue);
        status.postValue(false);
    }

    public MutableLiveData<Type> getDataLive() {return data;}
    public MutableLiveData<Boolean> getStatusLive() {return status;}
}
