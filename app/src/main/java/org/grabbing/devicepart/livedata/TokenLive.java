package org.grabbing.devicepart.livedata;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.grabbing.devicepart.domain.QueryData;

import java.util.List;

public class TokenLive extends ViewModel {
    private final MutableLiveData<String> token = new MutableLiveData<>();
    private final MutableLiveData<Boolean> status = new MutableLiveData<>();

    public void setToken(String token) {this.token.postValue(token);}
    public String getToken() {return token.getValue();}

    public void setStatus(boolean status) {this.status.postValue(status);}
    public boolean getStatus() {return Boolean.TRUE.equals(this.status.getValue());}

    public MutableLiveData<String> getTokenLive() {return token;}
    public MutableLiveData<Boolean> getStatusLive() {return status;}
}
