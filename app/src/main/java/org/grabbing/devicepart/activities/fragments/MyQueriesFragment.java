package org.grabbing.devicepart.activities.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.grabbing.devicepart.R;
import org.grabbing.devicepart.activities.Updater;
import org.grabbing.devicepart.activities.recyclerview.QueryAdapter;
import org.grabbing.devicepart.data.storage.LongTermStorage;
import org.grabbing.devicepart.dto.MyQuery;
import org.grabbing.devicepart.livedata.TypeLive;
import org.grabbing.devicepart.managers.AddQueryManager;

import java.util.ArrayList;
import java.util.List;

public class MyQueriesFragment extends Fragment {
    Thread thread;
    private TypeLive<List<MyQuery>> listLive;

    RecyclerView done;
    RecyclerView inProcess;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_queries, container, false);


        Button back = view.findViewById(R.id.my_queries_button_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Updater.setMyQueriesFragment(null);
                getActivity().getSupportFragmentManager().beginTransaction().remove(MyQueriesFragment.this).commit();
            }
        });

        Button add = view.findViewById(R.id.my_queries_button_add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.main, new AddQueryFragment());
                transaction.commit();
            }
        });

        done = view.findViewById(R.id.recycler_view_done);
        inProcess = view.findViewById(R.id.recycler_view_in_process);

        thread = new Thread("MQ thread");
        listLive = new TypeLive<>(new ArrayList<>());
        listLive.getStatusLive().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean status) {
                if(status) {
                    synchronized (thread) {
                        thread.notify();
                    }
                }
            }
        });


        thread = new Thread(() -> getAllCallThread(listLive));
        thread.start();


        return view;
    }

    public void getAllCallThread(TypeLive<List<MyQuery>> typeLive) {
        typeLive.clearAll();
        AddQueryManager addQueryManager = new AddQueryManager(getContext(), LongTermStorage.getToken(getContext()));
        addQueryManager.getDone(typeLive);
        synchronized (thread) {
            try {
                thread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        List<MyQuery> listDone = typeLive.getData();
        Log.i("list", listDone.toString());
        if (getActivity() == null || !isAdded()) {
            return;
        }
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                QueryAdapter customAdapter = new QueryAdapter(listDone, requireActivity().getSupportFragmentManager(), getContext());
                done.setLayoutManager(new LinearLayoutManager(MyQueriesFragment.this.requireActivity().getApplicationContext()));
                done.setAdapter(customAdapter);
            }
        });

        typeLive.clearAll();
        addQueryManager.getInProcess(typeLive);
        synchronized (thread) {
            try {
                thread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        List<MyQuery> listInProcess = typeLive.getData();
        Log.i("list", listInProcess.toString());
        if (getActivity() == null || !isAdded()) {
            return;
        }
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                QueryAdapter customAdapter = new QueryAdapter(listInProcess, requireActivity().getSupportFragmentManager(), getContext());
                inProcess.setLayoutManager(new LinearLayoutManager(MyQueriesFragment.this.requireActivity().getApplicationContext()));
                inProcess.setAdapter(customAdapter);
            }
        });
    }

    public void updateCallThread() {
        Activity activity = getActivity();
        if (activity == null || !isAdded()) {
            return;
        }
        thread = new Thread(() -> getAllCallThread(listLive));
        thread.start();
    }

}
