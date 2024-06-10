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
import org.grabbing.devicepart.managers.FaceManager;

public class FaceRegisterFragment extends Fragment {
    private TextInputLayout name;
    Thread thread;


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

        thread = new Thread("FaceReg Thread");
        TypeLive<Boolean> typeLive = new TypeLive<>(null);

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
                thread = new Thread(() -> register(name.getEditText().getText().toString(), typeLive));
                thread.start();
            }
        });


        return view;
    }


    private void register(String name, TypeLive<Boolean> typeLive) {
        FaceManager faceManager = new FaceManager(getContext(), LongTermStorage.getToken(getContext()));
        faceManager.register(name, typeLive);
        synchronized (thread) {
            try {
                thread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Boolean currentData = typeLive.getData();

        if (currentData == null) {
            errorCallThread("There is no internet connection");
        } else if (currentData) {
            successCallThread();
        } else {
            errorCallThread("Such a face already exists");
        }
    }


    public void successCallThread() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(FaceRegisterFragment.this).commit();
    }

    public void errorCallThread(String error) {
        requireActivity().runOnUiThread(new Thread() {
            @Override
            public void run() {
                name.setError(error);
            }
        });
    }
}
