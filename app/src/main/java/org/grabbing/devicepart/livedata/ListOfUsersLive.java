package org.grabbing.devicepart.livedata;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.grabbing.devicepart.domain.QueryData;

import java.util.ArrayList;
import java.util.List;

public class ListOfUsersLive extends ViewModel {
    private final MutableLiveData<List<String>> listOfUsers = new MutableLiveData<>();
    private final MutableLiveData<Boolean> status = new MutableLiveData<>();

    public void clearAll() {
        listOfUsers.postValue(new ArrayList<>());
        this.status.postValue(false);
    }

    public void replace(List<String> list) {listOfUsers.postValue(list);}
    public void setStatus(boolean status) {this.status.postValue(status);}

    public List<String> getListOfUsers() {return listOfUsers.getValue();}
    public boolean getStatus() {return Boolean.TRUE.equals(status.getValue());}

    public MutableLiveData<List<String>> getListOfUsersLive() {return listOfUsers;}
    public MutableLiveData<Boolean> getStatusLive() {return status;}
}
