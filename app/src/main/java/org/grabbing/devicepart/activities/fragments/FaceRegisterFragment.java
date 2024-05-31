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

public class FaceRegisterFragment extends Fragment {
    private TextInputLayout name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.face_register, container, false);


        Button back = view.findViewById(R.id.face_register_button_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(FaceRegisterFragment.this).commit();
            }
        });

        Button register = view.findViewById(R.id.face_register_button_register);
        name = view.findViewById(R.id.face_register_text_input_layout_name);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticStorage.getExecutor().registerFace(name.getEditText().getText().toString());
            }
        });


        return view;
    }

    public void successCallThread() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(FaceRegisterFragment.this).commit();
    }

    public void errorCallThread() {
        requireActivity().runOnUiThread(new Thread() {
            @Override
            public void run() {
                name.setError("error");
            }
        });
    }
}
