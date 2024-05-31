package org.grabbing.devicepart.activities.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import org.grabbing.devicepart.R;

import org.grabbing.devicepart.activities.recyclerview.ListOfLinkedUsersAdapter;
import org.grabbing.devicepart.data.storage.LongTermStorage;
import org.grabbing.devicepart.data.storage.StaticStorage;

import java.util.List;

public class FaceManagementFragment extends Fragment {
    private RecyclerView recyclerView;
    private TextInputLayout name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.face_management, container, false);

        initUI(view);

        StaticStorage.getExecutor().getListOfLinkedUsers();

        return view;
    }

    public void initUI(View view) {
        Button back = view.findViewById(R.id.face_management_button_back);
        recyclerView = view.findViewById(R.id.face_management_recycler_view);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(FaceManagementFragment.this).commit();
            }
        });

        Button attach = view.findViewById(R.id.face_management_button_attach);
        Button detach = view.findViewById(R.id.face_management_button_detach);

        name = view.findViewById(R.id.face_management_text_input_layout_name);

        attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticStorage.getExecutor().attachFace(name.getEditText().getText().toString());
                name.setErrorEnabled(false);
            }
        });

        detach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticStorage.getExecutor().detachFace(name.getEditText().getText().toString());
                if (name.getEditText().getText().toString().equals(LongTermStorage.getUsername(FaceManagementFragment.this.requireActivity()))) {
                    getActivity().getSupportFragmentManager().beginTransaction().remove(FaceManagementFragment.this).commit();
                    name.setErrorEnabled(false);
                }
            }
        });
    }

    public void updateCallThread() {
        StaticStorage.getExecutor().getListOfLinkedUsers();
    }

    public void setListOfLinkedUsersCallThread(List<String> list) {
        if (!isAdded()) {
            return;
        }
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ListOfLinkedUsersAdapter customAdapter = new ListOfLinkedUsersAdapter(list);
                recyclerView.setLayoutManager(new LinearLayoutManager(FaceManagementFragment.this.requireActivity().getApplicationContext()));
                recyclerView.setAdapter(customAdapter);
            }
        });
    }
    public void setErrorCallThread() {
        if (!isAdded()) {
            Log.i("setErrorCallThread", "!isAdded");
            return;
        }
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                name.setError("error");
            }
        });
    }
}
