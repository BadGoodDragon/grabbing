package org.grabbing.devicepart.activities.recyclerview;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.grabbing.devicepart.R;

public class ListOfLinkedUsersViewHolder extends RecyclerView.ViewHolder {
    private TextView textView;

    public ListOfLinkedUsersViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.list_of_linked_users_item_text);
    }

    public TextView getTextView() {
        return textView;
    }
}
