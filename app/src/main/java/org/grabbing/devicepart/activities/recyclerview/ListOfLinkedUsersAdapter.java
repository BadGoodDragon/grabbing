package org.grabbing.devicepart.activities.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.grabbing.devicepart.R;

import java.util.List;

public class ListOfLinkedUsersAdapter extends RecyclerView.Adapter<ListOfLinkedUsersViewHolder>{
    private List<String> dataList;

    public ListOfLinkedUsersAdapter(List<String> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ListOfLinkedUsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_of_linked_users, parent, false);
        return new ListOfLinkedUsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListOfLinkedUsersViewHolder holder, int position) {
        String data = dataList.get(position);
        holder.getTextView().setText(data);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
