package org.grabbing.devicepart.activities.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import org.grabbing.devicepart.R;
import org.grabbing.devicepart.activities.fragments.QueryDialogFragment;
import org.grabbing.devicepart.data.storage.LongTermStorage;
import org.grabbing.devicepart.dto.MyQuery;
import org.grabbing.devicepart.dto.QueryVisual;
import org.grabbing.devicepart.livedata.TypeLive;
import org.grabbing.devicepart.managers.AddQueryManager;

import java.util.List;

public class QueryAdapter extends RecyclerView.Adapter<QueryViewHolder> {
    private final List<MyQuery> data;
    private final FragmentManager fragmentManager;

    private Thread thread;
    private TypeLive<QueryVisual> typeLive;
    private TypeLive<String> stringLive;

    private final Context context;

    public QueryAdapter(List<MyQuery> data, FragmentManager fragmentManager, Context context) {
        this.data = data;
        this.fragmentManager = fragmentManager;
        this.context = context;

        typeLive = new TypeLive<>(null);
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

        stringLive = new TypeLive<>("");
        stringLive.getStatusLive().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean status) {
                if (status) {
                    QueryDialogFragment queryDialogFragment = new QueryDialogFragment(stringLive.getData());
                    queryDialogFragment.show(fragmentManager, null);
                }
            }
        });
    }

    @NonNull
    @Override
    public QueryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_query_item, parent, false);
        return new QueryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QueryViewHolder holder, int position) {
        long id = data.get(position).getId();
        holder.setData(data.get(position).getUrl(), data.get(position).getId());
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*QueryDialogFragment queryDialogFragment = new QueryDialogFragment(String.valueOf(id));
                queryDialogFragment.show(fragmentManager, null);*/
                typeLive.clearAll();
                stringLive.clearAll();

                thread = new Thread(() -> getQueryDataCallThread(typeLive, stringLive, id));
                thread.start();
            }
        });


    }


    void getQueryDataCallThread(TypeLive<QueryVisual> queryVisualLive, TypeLive<String> textLive, long id) {
        AddQueryManager addQueryManager = new AddQueryManager(context, LongTermStorage.getToken(context));
        addQueryManager.getQuery(queryVisualLive, id);
        synchronized (thread) {
            try {
                thread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        QueryVisual queryVisual = queryVisualLive.getData();

        if (queryVisual == null) {
            textLive.setData("Error: No Internet Connection");
            textLive.setStatus(true);
            return;
        }

        textLive.setData(queryVisual.toString());
        textLive.setStatus(true);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }
}
