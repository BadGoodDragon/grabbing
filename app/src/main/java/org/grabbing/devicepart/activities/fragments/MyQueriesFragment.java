package org.grabbing.devicepart.activities.fragments;



import androidx.fragment.app.Fragment;

public class MyQueriesFragment extends Fragment {

    /*private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_queries, container, false);

        recyclerView = view.findViewById(R.id.my_queries_list_of_queries);

        Button update = view.findViewById(R.id.my_queries_button_update);
        Button add = view.findViewById(R.id.my_queries_button_add);
        Button back = view.findViewById(R.id.my_queries_button_back);

        TextInputLayout url = view.findViewById(R.id.my_queries_text_input_layout_url);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(MyQueriesFragment.this).commit();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticStorage.getExecutor().addQuery(url.getEditText().getText().toString());
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StaticStorage.isHasFace()) {
                    getActivity().getSupportFragmentManager().beginTransaction().remove(MyQueriesFragment.this).commit();
                }
                StaticStorage.getExecutor().getQuery();
            }
        });


        return view;
    }

    public void setListOfQueriesCallThread(List<String> list) {
        if (!isAdded()) {
            return;
        }
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ListOfQueriesAdapter customAdapter = new ListOfQueriesAdapter(list);
                recyclerView.setLayoutManager(new LinearLayoutManager(MyQueriesFragment.this.requireActivity().getApplicationContext()));
                recyclerView.setAdapter(customAdapter);
            }
        });
    }*/
}
