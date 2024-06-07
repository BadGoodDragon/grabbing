package org.grabbing.devicepart.activities.fragments;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import org.grabbing.devicepart.R;
import org.grabbing.devicepart.activities.Updater;
import org.grabbing.devicepart.activities.recyclerview.ItemAdapter;
import org.grabbing.devicepart.dto.NewQuery;

import java.util.HashMap;
import java.util.Map;

public class AddQueryFragment extends Fragment {
    private ItemAdapter parametersAdapter;
    private ItemAdapter headersAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_query, container, false);

        Button back = view.findViewById(R.id.add_query_button_back);
        Button add = view.findViewById(R.id.add_query_button_add);

        TextInputLayout url = view.findViewById(R.id.add_query_text_input_layout_url);
        RecyclerView parameters = view.findViewById(R.id.recycler_view_parameters);
        Button addParameter = view.findViewById(R.id.button_add_parameter);
        RecyclerView headers = view.findViewById(R.id.recycler_view_headers);
        Button addHeader = view.findViewById(R.id.button_add_header);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Updater.setFaceManagementFragment(null);
                getActivity().getSupportFragmentManager().beginTransaction().remove(AddQueryFragment.this).commit();
            }
        });

        /*ListOfLinkedUsersAdapter customAdapter = new ListOfLinkedUsersAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(FaceManagementFragment.this.requireActivity().getApplicationContext()));
        recyclerView.setAdapter(customAdapter);*/

        Map<String, String> test = new HashMap<>();
        test.put("", "");

        parametersAdapter = new ItemAdapter(test);
        headersAdapter = new ItemAdapter(test);

        parameters.setLayoutManager(new LinearLayoutManager(AddQueryFragment.this.requireActivity().getApplicationContext()));
        headers.setLayoutManager(new LinearLayoutManager(AddQueryFragment.this.requireActivity().getApplicationContext()));

        parameters.setAdapter(parametersAdapter);
        headers.setAdapter(headersAdapter);


        addParameter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> transferMap = parametersAdapter.getMap();
                parameters.setLayoutManager(new LinearLayoutManager(AddQueryFragment.this.requireActivity().getApplicationContext()));
                transferMap.put("", "");
                parametersAdapter = new ItemAdapter(transferMap);
                parameters.setAdapter(parametersAdapter);
            }
        });

        addHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> transferMap = headersAdapter.getMap();
                headers.setLayoutManager(new LinearLayoutManager(AddQueryFragment.this.requireActivity().getApplicationContext()));
                transferMap.put("", "");
                headersAdapter = new ItemAdapter(transferMap);
                headers.setAdapter(headersAdapter);
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewQuery newQuery = new NewQuery(url.getEditText().getText().toString(),
                        parametersAdapter.getMap(),
                        headersAdapter.getMap(),
                        "",
                        0); //TODO: write get body and host id;
                Log.i("new query", newQuery.toString());
            }
        });

        return view;
    }
}
