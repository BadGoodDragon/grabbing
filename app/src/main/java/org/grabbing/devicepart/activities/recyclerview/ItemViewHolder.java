package org.grabbing.devicepart.activities.recyclerview;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import org.grabbing.devicepart.R;

import java.util.List;
import java.util.Map;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    private List<String> keys;
    private List<String> values;
    private int position;

    private TextInputLayout key;
    private TextInputLayout value;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        key = itemView.findViewById(R.id.key);
        value = itemView.findViewById(R.id.value);

        key.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                keys.set(position, String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        value.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                values.set(position, String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    public void setData(List<String> keys, List<String> values, int position) {
        this.keys = keys;
        this.values = values;
        this.position = position;

        this.key.getEditText().setText(keys.get(position));
        this.value.getEditText().setText(values.get(position));
    }
}
