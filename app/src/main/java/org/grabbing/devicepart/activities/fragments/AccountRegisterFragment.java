package org.grabbing.devicepart.activities.fragments;

import android.os.Bundle;
import android.text.style.BulletSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.google.android.material.textfield.TextInputLayout;

import org.grabbing.devicepart.R;
import org.grabbing.devicepart.livedata.TypeLive;
import org.grabbing.devicepart.managers.AccountManager;

public class AccountRegisterFragment extends Fragment {
    private TextInputLayout username;
    private TextInputLayout password;
    Thread thread;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_register, container, false);


        Button back = view.findViewById(R.id.account_register_button_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(AccountRegisterFragment.this).commit();
            }
        });

        Button register = view.findViewById(R.id.account_register_button_register);
        username = view.findViewById(R.id.account_register_text_input_layout_username);
        password = view.findViewById(R.id.account_register_text_input_layout_password);

        thread = new Thread("Register Thread");
        TypeLive<Boolean> typeLive = new TypeLive<>(false);

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

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeLive.clearAll();
                thread = new Thread(() -> register(username.getEditText().getText().toString(),
                        password.getEditText().getText().toString(), typeLive));
                thread.start();
            }
        });


        return view;
    }

    private void register(String username, String password, TypeLive<Boolean> typeLive) {
        AccountManager accountManager = new AccountManager(getContext());
        accountManager.register(username, password, typeLive);
        synchronized (thread) {
            try {
                thread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Boolean currentData = typeLive.getData();

        if (currentData) {
            successCallThread();
        } else {
            errorCallThread();
        }
    }

    public void successCallThread() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(AccountRegisterFragment.this).commit();
    }

    public void errorCallThread() {
        requireActivity().runOnUiThread(new Thread() {
            @Override
            public void run() {
                username.setError("error");
            }
        });
    }

}
