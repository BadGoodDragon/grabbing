package org.grabbing.devicepart.activities.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import org.grabbing.devicepart.R;

import org.grabbing.devicepart.activities.recyclerview.ListOfLinkedUsersAdapter;
import org.grabbing.devicepart.data.storage.LongTermStorage;
import org.grabbing.devicepart.data.storage.StaticStorage;
import org.grabbing.devicepart.livedata.TypeLive;
import org.grabbing.devicepart.managers.FaceManager;

import java.util.ArrayList;
import java.util.List;

public class FaceManagementFragment extends Fragment {
    private RecyclerView recyclerView;
    private TextInputLayout name;
    Thread thread;
    private TypeLive<List<String>> listLive;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.face_management, container, false);

        initUI(view);

        listLive = new TypeLive<>(new ArrayList<>());

        listLive.getStatusLive().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean status) {
                if (status) {
                    synchronized (thread) {
                        thread.notify();
                    }
                }
            }
        });

        listLive.clearAll();
        thread = new Thread(() -> getListOfUsers(listLive));
        thread.start();

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


        thread = new Thread("Face Thread");
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

        attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeLive.clearAll();
                thread = new Thread(() -> attach(name.getEditText().getText().toString(), typeLive));
                thread.start();
            }
        });

        detach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeLive.clearAll();
                thread = new Thread(() -> detach(name.getEditText().getText().toString(), typeLive));
                thread.start();
            }
        });

    }

    public void attach(String name, TypeLive<Boolean> typeLive) {
        FaceManager faceManager = new FaceManager(getContext(), LongTermStorage.getToken(getContext()));
        faceManager.attach(name, typeLive);
        synchronized (thread) {
            try {
                thread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Boolean currentData = typeLive.getData();

        if (!currentData) {
            setErrorCallThread();
        }
    }
    public void detach(String name, TypeLive<Boolean> typeLive) {
        FaceManager faceManager = new FaceManager(getContext(), LongTermStorage.getToken(getContext()));
        faceManager.detach(name, typeLive);
        synchronized (thread) {
            try {
                thread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Boolean currentData = typeLive.getData();

        if (!currentData) {
            setErrorCallThread();
        }
    }

    public void updateCallThread() {
        listLive.clearAll();
        thread = new Thread(() -> getListOfUsers(listLive));
        thread.start();
    }

    public void getListOfUsers(TypeLive<List<String>> listLive) {
        FaceManager faceManager = new FaceManager(getContext(), LongTermStorage.getToken(getContext()));
        faceManager.getListOfLinkedUsers(listLive);
        synchronized (thread) {
            try {
                thread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        List<String> list = listLive.getData();
        setListOfLinkedUsersCallThread(list);
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
