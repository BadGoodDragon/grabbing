package org.grabbing.devicepart.activities.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.grabbing.devicepart.R;
import org.grabbing.devicepart.dto.MyQuery;

import java.util.List;

public class QueryAdapter extends RecyclerView.Adapter<QueryViewHolder> {
    private List<MyQuery> data;

    public QueryAdapter(List<MyQuery> data) {
        this.data = data;
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
        holder.setData(data.get(position).getUrl(), data.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
