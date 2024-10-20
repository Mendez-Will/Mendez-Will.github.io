/*
 * ------------------------------------------------------------------------------
 * File: EventEditActivity.java
 * Author: Wilfredo Mendez
 * Date: September 15, 2024
 * Version: 1.0
 *
 * Purpose:
 * This activity provides an interface for adding new events or editing existing ones.
 * It interacts with the SQLite database to save or update event details. When making a
 * new event, the user inputs event information such as the name, description, date, and time.
 * This class also pre-fills the text windows if an existing event is passed through an intent, making
 * it easy to modify events.
 *
 * Design Decisions:
 * 1. Date and time are captured using `DatePicker` and `TimePicker` in spinner mode
 *    to offer an easy user experience.
 * 2. Input validation ensures all fields are filled before an event is saved.
 * 3. Toast notifications provide immediate feedback on the success or failure
 *    of operations.
 *
 * ------------------------------------------------------------------------------
 */

package com.example.wilfredo_mendez_event_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/*
 * EventEditActivity:
 * This class allows the user to add new events or edit existing ones. It handles
 * input validation, database interactions, and provides feedback using Toast messages.
 */
public class EventEditActivity extends AppCompatActivity {

    // UI components for capturing event details
    private EditText editTextEventName, editTextEventDescription;
    private DatePicker datePicker;  // Date selector for the event
    private TimePicker timePicker;  // Time selector for the event

    // Database helper for managing event records
    private EventDatabase eventDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editevent);  // Inflate the event edit layout

        eventDatabase = new EventDatabase(this);  // Initialize the database helper

        // Link UI components to their XML counterparts
        editTextEventName = findViewById(R.id.editTextEventName);
        editTextEventDescription = findViewById(R.id.editTextEventDescription);
        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        Button buttonSaveEvent = findViewById(R.id.buttonSaveEvent);  // Save button

        /*
         * Intent handling: Check if this activity was opened to edit an existing event.
         * If an event ID is found, pre-fill the input fields with the existing data.
         */
        Intent intent = getIntent();  // Get the Intent that started this activity
        int eventId = intent.getIntExtra("eventId", -1);  // Retrieve event ID (if available)

        if (eventId != -1) {
            // Pre-fill event details when editing an existing event
            editTextEventName.setText(intent.getStringExtra("eventName"));
            editTextEventDescription.setText(intent.getStringExtra("eventDescription"));
        }

        /*
         * Save Button Click Listener:
         * Handles both adding new events and updating existing events in the database.
         * On button click, it validates inputs and either adds or updates the event.
         */
        buttonSaveEvent.setOnClickListener(v -> {
            String name = editTextEventName.getText().toString().trim();  // Get event name
            String description = editTextEventDescription.getText().toString().trim();  // Get event description

            // Retrieve selected date from DatePicker
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth();
            int year = datePicker.getYear();
            String date = day + "/" + (month + 1) + "/" + year;  // Format the date

            // Retrieve selected time from TimePicker
            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();
            String time = String.format("%02d:%02d", hour, minute);  // Format the time

            // Validate input fields to ensure no field is left empty
            if (name.isEmpty() || description.isEmpty()) {
                // Show a Toast message if validation fails
                Toast.makeText(EventEditActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                boolean success;  // Store the result of the database operation

                if (eventId == -1) {
                    // Add a new event to the database if no event ID was passed
                    success = eventDatabase.addEvent(name, description, date, time);
                } else {
                    // Update the existing event in the database
                    success = eventDatabase.updateEvent(eventId, name, description, date, time);
                }

                // Provide feedback based on the success of the database operation
                if (success) {
                    Toast.makeText(EventEditActivity.this, "Event saved", Toast.LENGTH_SHORT).show();
                    finish();  // Close the activity and return to the previous screen
                } else {
                    Toast.makeText(EventEditActivity.this, "Error saving event", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
