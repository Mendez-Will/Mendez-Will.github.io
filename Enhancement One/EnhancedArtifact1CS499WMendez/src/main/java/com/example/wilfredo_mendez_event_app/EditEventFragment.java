/*
 * ------------------------------------------------------------------------------
 * File: EditEventFragment.java
 * Author: Wilfredo Mendez
 * Date: September 17, 2024
 * Version: 1.0
 *
 * Purpose:
 * The `EditEventFragment` handles the functionality for editing an event or
 * creating a new one. This fragment allows users to input an event's name,
 * description, date, and time. If an existing event is being edited, the
 * fragment pre-fills the fields with the event's data. A save button is provided
 * to either update the existing event or add a new event to the SQLite database.
 *
 * Design Notes:
 * 1. The fragment uses `DatePicker` and `TimePicker` widgets to simplify date
 *    and time input.
 * 2. `EventDatabase` is used to manage event data, ensuring all modifications
 *    are saved persistently in SQLite.
 * 3. This fragment retrieves and passes event data using `Bundle` to maintain
 *    state when switching between screens.
 * 4. The use of `Toast` notifications ensures users receive immediate feedback
 *    on success or failure when saving events.
 * ------------------------------------------------------------------------------
 */

package com.example.wilfredo_mendez_event_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

/**
 * `EditEventFragment` manages the UI and logic for editing or adding events.
 * Users can input an event's name, description, date, and time. If editing an
 * existing event, the fields are pre-filled with the event’s data.
 */
public class EditEventFragment extends Fragment {

    // UI elements for event name and description input
    private EditText editTextEventName, editTextEventDescription;

    // Widgets for selecting date and time
    private DatePicker datePicker;
    private TimePicker timePicker;

    // Database object to manage event storage and retrieval
    private EventDatabase eventDatabase;

    // Holds the current event being edited, if any
    private Event currentEvent;

    /**
     * Creates and initializes the fragment's view.
     *
     * @param inflater  LayoutInflater to inflate the fragment's layout.
     * @param container ViewGroup containing this fragment.
     * @param savedInstanceState Saved state of the fragment (if any).
     * @return The inflated View for this fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment's layout from the XML resource file
        View view = inflater.inflate(R.layout.editevent, container, false);

        // Initialize the database connection
        eventDatabase = new EventDatabase(getContext());

        // Initialize UI components
        editTextEventName = view.findViewById(R.id.editTextEventName);
        editTextEventDescription = view.findViewById(R.id.editTextEventDescription);
        datePicker = view.findViewById(R.id.datePicker);
        timePicker = view.findViewById(R.id.timePicker);
        Button buttonSaveEvent = view.findViewById(R.id.buttonSaveEvent);

        // Retrieve and pre-fill event data, if provided
        if (getArguments() != null) {
            currentEvent = (Event) getArguments().getSerializable("event");
            if (currentEvent != null) {
                // Pre-fill event name and description
                editTextEventName.setText(currentEvent.getName());
                editTextEventDescription.setText(currentEvent.getDescription());

                // Set the selected date in the DatePicker
                String[] dateParts = currentEvent.getDate().split("-");
                datePicker.updateDate(
                        Integer.parseInt(dateParts[0]),  // Year
                        Integer.parseInt(dateParts[1]) - 1,  // Month (0-indexed)
                        Integer.parseInt(dateParts[2])  // Day
                );

                // Set the selected time in the TimePicker
                String[] timeParts = currentEvent.getTime().split(":");
                timePicker.setCurrentHour(Integer.parseInt(timeParts[0]));
                timePicker.setCurrentMinute(Integer.parseInt(timeParts[1]));
            }
        }

        // Set up the save button's click listener
        buttonSaveEvent.setOnClickListener(v -> {
            // Retrieve input values from the text fields
            String name = editTextEventName.getText().toString();
            String description = editTextEventDescription.getText().toString();

            // Retrieve the selected date and time from the pickers
            String date = datePicker.getYear() + "-" +
                    (datePicker.getMonth() + 1) + "-" +
                    datePicker.getDayOfMonth();
            String time = String.format(
                    "%02d:%02d", timePicker.getCurrentHour(), timePicker.getCurrentMinute()
            );

            // Check if all required fields are filled
            if (name.isEmpty() || description.isEmpty()) {
                // Show a toast if any field is empty
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;  // Exit early to avoid saving incomplete data
            }

            boolean success;

            // Determine whether to add a new event or update an existing one
            if (currentEvent == null) {
                // Add a new event to the database
                success = eventDatabase.addEvent(name, description, date, time);
            } else {
                // Update the existing event in the database
                success = eventDatabase.updateEvent(
                        currentEvent.getId(), name, description, date, time
                );
            }

            // Provide feedback based on the operation result
            if (success) {
                // Show a success message if the event is saved
                Toast.makeText(getContext(), "Event saved", Toast.LENGTH_SHORT).show();
            } else {
                // Show an error message if saving failed
                Toast.makeText(getContext(), "Error saving event", Toast.LENGTH_SHORT).show();
            }
        });

        return view;  // Return the fully initialized view
    }
}

