package org.grabbing.devicepart.activities.recyclerview;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.grabbing.devicepart.R;

public class ListOfQueriesViewHolder extends RecyclerView.ViewHolder {
    private TextView textView;

    public ListOfQueriesViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.my_query_item_text);
    }

    public TextView getTextView() {
        return textView;
    }
}
