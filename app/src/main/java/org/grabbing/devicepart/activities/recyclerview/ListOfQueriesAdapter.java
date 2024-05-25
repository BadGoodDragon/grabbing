package org.grabbing.devicepart.activities.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.grabbing.devicepart.R;

import java.util.List;

public class ListOfQueriesAdapter extends RecyclerView.Adapter<ListOfQueriesViewHolder> {
    private List<String> dataList;

    public ListOfQueriesAdapter(List<String> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ListOfQueriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_query, parent, false);
        return new ListOfQueriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListOfQueriesViewHolder holder, int position) {
        String data = dataList.get(position);
        holder.getTextView().setText(data);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
