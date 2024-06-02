package org.grabbing.devicepart.activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.textfield.TextInputLayout;

import org.grabbing.devicepart.R;

import org.grabbing.devicepart.data.storage.LongTermStorage;
import org.grabbing.devicepart.data.storage.StaticStorage;

public class AccountLogInFragment extends Fragment {
    private TextInputLayout username;
    private TextInputLayout password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.account_log_in, container, false);


        Button back = view.findViewById(R.id.account_log_in_button_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                getActivity().getSupportFragmentManager().beginTransaction().remove(AccountLogInFragment.this).commit();
            }
        });

        Button logIn = view.findViewById(R.id.account_log_in_button_log_in);
        username = view.findViewById(R.id.account_log_in_text_input_layout_username);
        password = view.findViewById(R.id.account_log_in_text_input_layout_password);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticStorage.getExecutor().authorize(username.getEditText().getText().toString(),
                        password.getEditText().getText().toString());
            }
        });


        return view;
    }

    public void successCallThread() {
        LongTermStorage.saveUsername(username.getEditText().getText().toString(), getActivity().getApplicationContext());
        getActivity().getSupportFragmentManager().beginTransaction().remove(AccountLogInFragment.this).commit();
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




/*{

    private TextInputLayout username;
    private TextInputLayout password;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_log_in, container, false);

        StaticStorage.setAccountLogInFragment(this);
        StaticStorage.getUpdater().setActivitiesActions(this);

        Button back = view.findViewById(R.id.account_log_in_button_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });

        Button logIn = view.findViewById(R.id.account_log_in_button_log_in);
        username = view.findViewById(R.id.account_log_in_text_input_layout_username);
        password = view.findViewById(R.id.account_log_in_text_input_layout_password);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticStorage.getExecutor().authorize(username.getEditText().getText().toString(),
                        password.getEditText().getText().toString());
                LongTermStorage.saveUsername(username.getEditText().getText().toString(), requireActivity().getApplicationContext());
            }
        });

        return view;
    }

    public void successCallThread() {
        requireActivity().finish();
    }

    public void errorCallThread() {
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                username.setError("error");
            }
        });
    }

    @Override
    public void updateCallThread() {}
}*/