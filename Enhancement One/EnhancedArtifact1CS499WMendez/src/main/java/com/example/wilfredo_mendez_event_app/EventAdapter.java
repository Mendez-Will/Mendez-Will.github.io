/*
 * ------------------------------------------------------------------------------
 * File: EventAdapter.java
 * Author: Wilfredo Mendez
 * Date: September 17, 2024
 * Version: 1.0
 *
 * Purpose:
 * This class serves as the adapter for a RecyclerView that displays a list of
 * events within the event planner app. It binds data from the `Event` class to
 * the individual event items displayed in the RecyclerView and it
 * provides support for user interaction through edit and delete buttons for each event.
 *
 * Design Notes:
 * 1. Uses a ViewHolder pattern to improve performance by recycling views.
 * 2. Implements an `OnEventClickListener` interface to handle event interactions.
 *    This allows for decoupling the event handling logic from the adapter itself.
 * 3. All item views (like name, date, and time) are bound in the `onBindViewHolder()`
 *    method to ensure the correct data appears in the right UI components.
 *
 * ------------------------------------------------------------------------------
 */

package com.example.wilfredo_mendez_event_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/*
 * RecyclerView Adapter: Responsible for managing and displaying event data
 * inside a RecyclerView. This adapter binds the event data to the item layout
 * and handles user interactions like editing and deleting events.
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    // Context used to inflate the layout and interact with Android components
    private Context context;
    // List of events to be displayed in the RecyclerView
    private List<Event> eventList;
    // Listener interface to handle event actions (edit/delete)
    private OnEventClickListener eventClickListener;

    /*
     * Constructor: Initializes the adapter with a context, list of events,
     * and a click listener to handle event actions.
     */
    public EventAdapter(Context context, List<Event> eventList, OnEventClickListener eventClickListener) {
        this.context = context;  // Assign the context to the adapter
        this.eventList = eventList;  // Assign the event list to the adapter
        this.eventClickListener = eventClickListener;  // Assign the listener for event actions
    }

    /*
     * Called by the RecyclerView to create new item views. It inflates the event
     * layout (`eventdisplay.xml`) and wraps it in a ViewHolder to manage the views.
     */
    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each event item
        View view = LayoutInflater.from(context).inflate(R.layout.eventdisplay, parent, false);
        // Return a new ViewHolder instance for this item view
        return new EventViewHolder(view);
    }

    /*
     * Called by the RecyclerView to bind data to the item views at the given position.
     * It assigns the event’s details to the corresponding TextView elements and
     * sets up click listeners for the edit and delete buttons.
     */
    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        // Get the event at the current position in the list
        Event event = eventList.get(position);

        // Bind the event data to the corresponding UI elements
        holder.nameTextView.setText(event.getName());
        holder.dateTextView.setText(event.getDate());
        holder.timeTextView.setText(event.getTime());
        holder.descriptionTextView.setText(event.getDescription());

        /*
         * Set up click listeners for the Edit button.
         * When clicked, it triggers the `onEditEvent()` method from the listener interface.
         */
        holder.editButton.setOnClickListener(v -> eventClickListener.onEditEvent(event));

        /*
         * Set up click listeners for the Delete button.
         * When clicked, it triggers the `onDeleteEvent()` method from the listener interface.
         */
        holder.deleteButton.setOnClickListener(v -> eventClickListener.onDeleteEvent(event));
    }

    /*
     * Returns the total number of items in the list.
     * This helps the RecyclerView know how many items to display.
     */
    @Override
    public int getItemCount() {
        return eventList.size();  // Return the size of the event list
    }

    /*
     * ViewHolder class: Manages the views inside each individual event item.
     * Holds references to the TextViews and Buttons for each event, minimizing
     * repeated findViewById() calls to improve performance.
     */
    public static class EventViewHolder extends RecyclerView.ViewHolder {
        // TextViews to display event details (name, date, time, description)
        TextView nameTextView, dateTextView, timeTextView, descriptionTextView;
        // Buttons for editing and deleting events
        Button editButton, deleteButton;

        /*
         * Constructor: Links the UI elements inside the item layout to
         * the ViewHolder, making them accessible for data binding.
         */
        public EventViewHolder(@NonNull View itemView) {
            super(itemView);  // Call superclass constructor

            // Reference the TextViews for displaying event details
            nameTextView = itemView.findViewById(R.id.textViewEventName);
            dateTextView = itemView.findViewById(R.id.textViewEventDate);
            timeTextView = itemView.findViewById(R.id.textViewEventTime);
            descriptionTextView = itemView.findViewById(R.id.textViewEventDescription);

            // Reference the Edit and Delete buttons
            editButton = itemView.findViewById(R.id.buttonEdit);
            deleteButton = itemView.findViewById(R.id.buttonDelete);
        }
    }

    /*
     * Interface: Defines the callback methods for handling event actions
     * such as editing and deleting. This separates the UI logic from the
     * event handling code, improving modularity.
     */
    public interface OnEventClickListener {
        // Called when the Edit button is clicked for a specific event
        void onEditEvent(Event event);

        // Called when the Delete button is clicked for a specific event
        void onDeleteEvent(Event event);
    }
}

