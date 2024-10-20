/*
 * ------------------------------------------------------------------------------
 * File: RecyclerFragment.java
 * Author: Wilfredo Mendez
 * Date: September 17, 2024
 * Version: 1.0
 *
 * Purpose:
 * This fragment is responsible for displaying a list of events in a RecyclerView
 * within the event planner Android app. It interacts with the EventDatabase to
 * retrieve, display, and manage event data. It allows users to edit or delete events
 * using buttons within each event item. Events can be dynamically updated when the
 * user returns to the fragment or performs CRUD operations.
 *
 * Design Notes:
 * This fragment follows Android’s fragment lifecycle and uses the onCreateView() method
 * to set up the UI components, including the RecyclerView and its adapter. It ensures
 * that events are retrieved from the database before attaching the adapter to avoid
 * any null pointer exceptions. Inline comments mark enhanced sections, such as event
 * retrieval logic and adapter updates. Logging is used to help trace the flow and state of operations,
 * providing insights for debugging. Additionally, the fragment contains a helper
 * function, `loadDummyEvents()`, which can be used to populate sample data for testing.
 *
 * Note: Ensure the event database is correctly initialized before using this fragment.
 * ------------------------------------------------------------------------------
 */

package com.example.wilfredo_mendez_event_app;

import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerFragment extends Fragment {
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private List<Event> eventList;
    private EventDatabase eventDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the eventgrid layout
        View view = inflater.inflate(R.layout.eventgrid, container, false);

        // Initialize RecyclerView and set its layout manager
        recyclerView = view.findViewById(R.id.databaseRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.d("RecyclerFragment", "RecyclerView initialized");

        // Initialize the event list and database connection
        eventList = new ArrayList<>();
        eventDatabase = new EventDatabase(getActivity());

        // Load events from the database and attach the adapter
        loadEvents();
        eventAdapter = new EventAdapter(getActivity(), eventList, new EventAdapter.OnEventClickListener() {
            @Override
            public void onEditEvent(Event event) {
                // Launch EventEditActivity to edit the selected event
                Intent intent = new Intent(getActivity(), EventEditActivity.class);
                intent.putExtra("eventId", event.getId());
                intent.putExtra("eventName", event.getName());
                intent.putExtra("eventDescription", event.getDescription());
                intent.putExtra("eventDate", event.getDate());
                intent.putExtra("eventTime", event.getTime());
                startActivity(intent);
            }

            @Override
            public void onDeleteEvent(Event event) {
                // Delete the event and refresh the list
                eventDatabase.deleteEvent(event.getId());
                loadEvents();  // Reload the event list after deletion
            }
        });

        Log.d("RecyclerFragment", "Adapter initialized with eventList size: " + eventList.size());

        // Attach the adapter to the RecyclerView
        recyclerView.setAdapter(eventAdapter);
        Log.d("RecyclerFragment", "Adapter attached to RecyclerView");

        return view;
    }

    /**
     * Retrieves events from the database and updates the event list.
     * If the cursor is null or empty, it logs an error and avoids crashes.
     */
    private void loadEvents() {
        Cursor cursor = eventDatabase.getAllEvents();
        Log.d("EventDatabase", "Number of events: " + (cursor != null ? cursor.getCount() : "Cursor is null"));

        loadDummyEvents();  // Load dummy data for testing

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Extract event data from the cursor
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_ID"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(EventDatabase.EventTable.eventName));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(EventDatabase.EventTable.eventDescription));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(EventDatabase.EventTable.eventDate));
                String time = cursor.getString(cursor.getColumnIndexOrThrow(EventDatabase.EventTable.eventTime));

                // Create an Event object and add it to the list
                Event event = new Event(id, name, description, date, time);
                eventList.add(event);
            } while (cursor.moveToNext());
        } else {
            Log.e("RecyclerFragment", "No events found in the database");
        }

        // Close the cursor to release resources
        if (cursor != null) {
            cursor.close();
        }

        Log.d("RecyclerFragment", "Event list updated with size: " + eventList.size());

        // Notify the adapter about data changes
        if (eventAdapter != null) {
            eventAdapter.notifyDataSetChanged();
        } else {
            Log.e("RecyclerFragment", "Adapter is null!");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Reload events when the fragment resumes
        loadEvents();
        Log.d("RecyclerFragment", "onResume called, events reloaded");
    }

    /**
     * Adds dummy events to the list for testing purposes.
     * Can be customized or removed in production.
     */
    private void loadDummyEvents() {
        eventList.clear();  // Clear the list before adding new dummy events

        // Uncomment to add sample events for testing
        // eventList.add(new Event(1, "Sample Event", "Sample Description", "2024-09-21", "10:00 AM"));
        // eventList.add(new Event(2, "Another Event", "Another Description", "2024-09-22", "11:00 AM"));

        Log.d("RecyclerFragment", "Dummy events added with size: " + eventList.size());

        // Notify the adapter about data changes
        if (eventAdapter != null) {
            eventAdapter.notifyDataSetChanged();
        }
    }
}
