package org.grabbing.devicepart.activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import org.grabbing.devicepart.R;
import org.grabbing.devicepart.data.storage.StaticStorage;

public class AccountRegisterFragment extends Fragment {
    private TextInputLayout username;
    private TextInputLayout password;

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

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticStorage.getExecutor().registerAccount(username.getEditText().getText().toString(),
                        password.getEditText().getText().toString());
                //LongTermStorage.saveUsername(username.getEditText().getText().toString(), AccountLogInActivity.this.getApplicationContext());

            }
        });


        return view;
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
