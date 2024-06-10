package org.grabbing.devicepart.activities.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.google.android.material.textfield.TextInputLayout;

import org.grabbing.devicepart.R;

import org.grabbing.devicepart.data.storage.LongTermStorage;
import org.grabbing.devicepart.livedata.TypeLive;
import org.grabbing.devicepart.managers.AccountManager;

public class AccountLogInFragment extends Fragment {
    private TextInputLayout username;
    private TextInputLayout password;
    Thread thread;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_log_in, container, false);


        Button back = view.findViewById(R.id.account_log_in_button_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(AccountLogInFragment.this).commit();
            }
        });

        Button logIn = view.findViewById(R.id.account_log_in_button_log_in);
        username = view.findViewById(R.id.account_log_in_text_input_layout_username);
        password = view.findViewById(R.id.account_log_in_text_input_layout_password);

        thread = new Thread("Log In Thread");
        TypeLive<String> typeLive = new TypeLive<>("");

        typeLive.getStatusLive().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean status) {
                if (status) {
                    synchronized (thread) {
                        thread.notify();
                    }
                }
            }
        });

        TypeLive<Integer> statusCode = new TypeLive<>(-1);


        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeLive.clearAll();
                thread = new Thread(() -> authorize(username.getEditText().getText().toString(),
                        password.getEditText().getText().toString(), typeLive, statusCode));
                thread.start();
            }
        });


        return view;
    }

    private void authorize(String username, String password, TypeLive<String> typeLive, TypeLive<Integer> statusCode) {
        AccountManager accountManager = new AccountManager(getContext());
        accountManager.generateToken(username, password, typeLive, statusCode);
        synchronized (thread) {
            try {
                thread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String currentToken = typeLive.getData();

        if (statusCode.getData().equals(-1)) {
            errorCallThread("There is no internet connection");
        } else if (!currentToken.isEmpty()) {
            LongTermStorage.saveToken(currentToken, getContext());
            successCallThread();
            return;
        } else {
            errorCallThread("Invalid username or password");
        }
    }

    public void successCallThread() {
        LongTermStorage.saveUsername(username.getEditText().getText().toString(), getActivity().getApplicationContext());
        getActivity().getSupportFragmentManager().beginTransaction().remove(AccountLogInFragment.this).commit();
    }

    public void errorCallThread(String error) {
        requireActivity().runOnUiThread(new Thread() {
            @Override
            public void run() {
                username.setError(error);
            }
        });
    }
}