package org.grabbing.devicepart.activities.recyclerview;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.grabbing.devicepart.R;

public class QueryViewHolder extends RecyclerView.ViewHolder {
    private TextView url;
    private TextView id;
    private View view;

    public QueryViewHolder(@NonNull View itemView) {
        super(itemView);
        url = itemView.findViewById(R.id.my_query_text_url);
        id = itemView.findViewById(R.id.my_query_text_id);
        view = itemView.findViewById(R.id.view);
    }

    public void setData(String url, long id) {
        this.url.setText(url);
        this.id.setText(String.valueOf(id));
    }

    public View getView() {
        return view;
    }
}