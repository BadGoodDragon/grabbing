package org.grabbing.devicepart.activities.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.grabbing.devicepart.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {
    private final List<String> keys;
    private final List<String> values;

    public ItemAdapter(Map<String, String> data) {
        keys = new ArrayList<>();
        values = new ArrayList<>();

        for (Map.Entry<String, String> entry : data.entrySet()) {
            keys.add(entry.getKey());
            values.add(entry.getValue());
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.add_query_rv_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.setData(keys, values, position);
    }

    @Override
    public int getItemCount() {
        if (keys.size() != values.size()) {
            throw new RuntimeException();
        }
        return keys.size();
    }

    public void addEmpty() {
        keys.add("");
        values.add("");
    }

    public Map<String, String> getMap() {
        Map<String, String> result = new HashMap<>();
        for (int i = 0; i < keys.size(); i++) {
            result.put(keys.get(i), values.get(i));
        }
        return result;
    }
}
