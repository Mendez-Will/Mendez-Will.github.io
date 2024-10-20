package com.example.wilfredo_mendez_event_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.database.Cursor;


public class DataFragment extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private EventDatabase dbHelper;
    private RecyclerView recyclerView;
    private Adapter adapter;

    private Cursor cursor;

    public DataFragment() {
        // Required empty public constructor
    }


    public static DataFragment newInstance(String param1, String param2) {
        DataFragment fragment = new DataFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new EventDatabase(getActivity());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.editevent, container, false);
        View view2 = inflater.inflate(R.layout.eventgrid, container, false);

        // Find the RecyclerView in your layout
        recyclerView = view2.findViewById(R.id.databaseRecyclerView);

        // Create an instance of your adapter
        adapter = new Adapter(getContext(), cursor);

        // Set layout manager to RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set the adapter to the RecyclerView
        recyclerView.setAdapter(adapter);

        Button addButton = view.findViewById(R.id.saveEventBtn);
        EditText titleInput = view.findViewById(R.id.editEventName);
        EditText dateInput = view.findViewById(R.id.selectDateBtn);
        EditText decripInput = view.findViewById(R.id.editEventDescription);

        addButton.setOnClickListener(v -> {
            String title = titleInput.getText().toString().trim();
            String date = dateInput.getText().toString().trim();
            String description = decripInput.getText().toString().trim();

            if(title.isEmpty() || date.isEmpty()) {
                Toast.makeText(getContext(), "Title and Date of event required.", Toast.LENGTH_SHORT).show();
            }
            else {
                addItem(title, date, description);
            }
        });

        return view;
    }

    public void addItem(String title, String date, String description) {
        dbHelper.addEvent(title, date, description);
    }

    public void removeItem(long eventId) {
        dbHelper.deleteEvent(eventId);
    }

    public void updateItem(long eventId, String title, String date, String description) {
        dbHelper.updateEvent(eventId, title, date, description);
    }
}