/*
 * ------------------------------------------------------------------------------
 * File: Event.java
 * Author: Wilfredo Mendez
 * Date: October 15, 2024
 * Version: 1.0
 *
 * Purpose:
 * The `Event` class defines the structure of an event object used in the event
 * planner app. Each event includes fields for an ID, name, description, date,
 * and time. This class provides getter and setter methods to access and modify
 * the event data. It serves as a model for organizing events and will be used
 * across different parts of the application, such as storing event data in
 * SQLite and displaying it through the RecyclerView.
 *
 * Design Notes:
 * The ID uniquely identifies each event, allowing it to be referenced easily
 * within the SQLite database or in UI components (like RecyclerView). The
 * constructor initializes an event with all necessary details. By using
 * individual getters and setters for each field, the code ensures that event
 * properties can be modified separately if needed.
 * ------------------------------------------------------------------------------
 */

package com.example.wilfredo_mendez_event_app;

/**
 * The `Event` class defines the structure of an event, which includes essential
 * information such as ID, name, description, date, and time. This class acts as
 * a data model, ensuring consistent structure and data access across the app.
 */
public class Event {

    // Unique identifier for the event (used in database operations)
    private int id;

    // Name or title of the event (e.g., "Birthday Party" or "Work Meeting")
    private String name;

    // Description provides additional details about the event
    private String description;

    // Date of the event in string format (e.g., "2024-10-20")
    private String date;

    // Time of the event in string format (e.g., "14:30")
    private String time;

    /**
     * Constructor: Initializes an event object with all the required fields.
     *
     * @param id          Unique ID for the event (used for reference and updates).
     * @param name        Name or title of the event.
     * @param description Optional description providing more details.
     * @param date        Date of the event in string format.
     * @param time        Time of the event in string format.
     */
    public Event(int id, String name, String description, String date, String time) {
        this.id = id;  // Assigns the provided ID to the event's ID field
        this.name = name;  // Assigns the provided name to the event's name field
        this.description = description;  // Assigns the description to the event
        this.date = date;  // Assigns the date string to the event's date field
        this.time = time;  // Assigns the time string to the event's time field
    }

    // ---------------------------- Getter Methods ----------------------------
    // Each getter method retrieves the value of a specific field.

    /**
     * Gets the ID of the event.
     *
     * @return The event's unique ID.
     */
    public int getId() {
        return id;  // Returns the ID associated with this event
    }

    /**
     * Gets the name or title of the event.
     *
     * @return The event's name.
     */
    public String getName() {
        return name;  // Returns the name of the event
    }

    /**
     * Gets the description of the event.
     *
     * @return A detailed description of the event.
     */
    public String getDescription() {
        return description;  // Returns the event's description
    }

    /**
     * Gets the date of the event.
     *
     * @return The event's date in string format (e.g., "2024-10-20").
     */
    public String getDate() {
        return date;  // Returns the date of the event
    }

    /**
     * Gets the time of the event.
     *
     * @return The event's time in string format (e.g., "14:30").
     */
    public String getTime() {
        return time;  // Returns the time of the event
    }

    // ---------------------------- Setter Methods ----------------------------
    // Each setter method allows modification of a specific field.

    /**
     * Sets the ID for the event.
     *
     * @param id The new ID to assign to the event.
     */
    public void setId(int id) {
        this.id = id;  // Updates the event's ID field with the new value
    }

    /**
     * Sets the name or title of the event.
     *
     * @param name The new name to assign to the event.
     */
    public void setName(String name) {
        this.name = name;  // Updates the event's name with the new value
    }

    /**
     * Sets the description of the event.
     *
     * @param description The new description to assign to the event.
     */
    public void setDescription(String description) {
        this.description = description;  // Updates the event's description
    }

    /**
     * Sets the date of the event.
     *
     * @param date The new date to assign to the event (in string format).
     */
    public void setDate(String date) {
        this.date = date;  // Updates the event's date with the new value
    }

    /**
     * Sets the time of the event.
     *
     * @param time The new time to assign to the event (in string format).
     */
    public void setTime(String time) {
        this.time = time;  // Updates the event's time with the new value
    }
}
