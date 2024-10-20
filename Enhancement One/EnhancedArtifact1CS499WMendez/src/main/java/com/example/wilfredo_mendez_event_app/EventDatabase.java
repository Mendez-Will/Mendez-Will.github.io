/*
 * ---------------------------------------------------------------------------
 * File: EventDatabase.java
 * Author: Wilfredo Mendez
 * Date: September 15, 2024
 * Version: 1.0
 *
 * Purpose:
 * This class defines the SQLite database used for storing event information
 * in the event planner app. It provides essential methods for CRUD operations
 * (Create, Read, Update, Delete) and manages the lifecycle of the database,
 * including creation and upgrades. Events are stored with attributes like name,
 * description, date, and time. Each event is assigned a unique ID for easy access.
 *
 * Design Notes:
 * - Using SQLiteOpenHelper ensures smooth management of database connections
 *   across the app lifecycle.
 * - Log messages are commented throughout the methods to assist with debugging.
 * - The event name and date are used as constraints to prevent duplicate events.
 *
 * ---------------------------------------------------------------------------
 */

package com.example.wilfredo_mendez_event_app;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/*
 * EventDatabase class extends SQLiteOpenHelper to manage database creation,
 * versioning, and CRUD operations on the events table.
 */
public class EventDatabase extends SQLiteOpenHelper {

    // Database properties: name and version
    private static final String DATABASE_NAME = "eventDatabase";  // Database file name
    private static final int DATABASE_VERSION = 1;  // Version for schema updates

    /*
     * Inner class defining the schema for the events table.
     * This structure ensures all column names are grouped logically,
     * preventing hard-coding throughout the app.
     */
    public static final class EventTable {
        private static final String eventTable = "events";  // Table name
        public static final String eventName = "name";       // Event name column
        public static final String eventDescription = "description";  // Description column
        public static final String eventDate = "date";       // Event date column
        public static final String eventTime = "time";       // Event time column
    }

    /*
     * Constructor initializes the database helper, linking it to the app context.
     */
    public EventDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //Log.d(TAG, "EventDatabase initialized");  // Debug log for tracking
    }

    /*
     * Called when the database is created for the first time.
     * It defines the structure of the events table, which includes
     * columns for event ID, name, description, date, and time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Log.d(TAG, "Creating events table");
        db.execSQL("CREATE TABLE " + EventTable.eventTable + " (" +
                "_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +  // Auto-incremented primary key
                EventTable.eventName + " TEXT, " +            // Event name column
                EventTable.eventDescription + " TEXT, " +     // Description column
                EventTable.eventDate + " TEXT, " +            // Date column
                EventTable.eventTime + " TEXT)");             // Time column
    }

    /*
     * Called when the database version is updated.
     * This method handles schema changes by dropping the existing table
     * and recreating it with the updated structure.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Log.d(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + EventTable.eventTable);  // Remove old table
        onCreate(db);  // Recreate the table with new schema
    }

    /*
     * Adds a new event to the database.
     * Takes the event details as parameters and inserts them into the events table.
     * Returns true if the insertion is successful.
     */
    public boolean addEvent(String name, String description, String date, String time) {
        //Log.d(TAG, "Adding event: " + name + ", " + date);
        SQLiteDatabase db = this.getWritableDatabase();  // Open database for writing
        ContentValues values = new ContentValues();  // Store event details in ContentValues
        values.put(EventTable.eventName, name);
        values.put(EventTable.eventDescription, description);
        values.put(EventTable.eventDate, date);
        values.put(EventTable.eventTime, time);
        long result = db.insert(EventTable.eventTable, null, values);  // Insert into the table
        return result != -1;  // Return true if insertion was successful
    }

    /*
     * Retrieves all events from the database.
     * Uses a raw SQL query to select all records from the events table.
     * The result is returned as a Cursor object, which can be iterated to access the events.
     */
    public Cursor getAllEvents() {
        //Log.d(TAG, "Retrieving all events");
        SQLiteDatabase db = this.getReadableDatabase();  // Open database for reading
        return db.rawQuery("SELECT * FROM " + EventTable.eventTable, null);  // Fetch all events
    }

    /*
     * Updates an existing event by its ID.
     * Takes the event ID and updated details as parameters and applies
     * the changes to the corresponding record in the events table.
     * Returns true if the update was successful.
     */
    public boolean updateEvent(int id, String name, String description, String date, String time) {
        //Log.d(TAG, "Updating event ID: " + id);
        SQLiteDatabase db = this.getWritableDatabase();  // Open database for writing
        ContentValues values = new ContentValues();  // Store updated details
        values.put(EventTable.eventName, name);
        values.put(EventTable.eventDescription, description);
        values.put(EventTable.eventDate, date);
        values.put(EventTable.eventTime, time);
        int rowsAffected = db.update(EventTable.eventTable, values, "_ID=?", new String[]{String.valueOf(id)});
        return rowsAffected > 0;  // Return true if at least one row was affected
    }

    /*
     * Deletes an event from the database by its ID.
     * Returns true if the deletion was successful.
     */
    public boolean deleteEvent(int id) {
        //Log.d(TAG, "Deleting event ID: " + id);
        SQLiteDatabase db = this.getWritableDatabase();  // Open database for writing
        int rowsDeleted = db.delete(EventTable.eventTable, "_ID=?", new String[]{String.valueOf(id)});
        return rowsDeleted > 0;  // Return true if at least one row was deleted
    }

    /*
     * Checks if an event with the given name and date already exists in the database.
     * This prevents duplicate events from being created.
     * Returns true if an event with the same name and date is found.
     */
    public boolean checkEvent(String name, String date) {
        SQLiteDatabase db = this.getReadableDatabase();  // Open database for reading
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + EventTable.eventTable +
                        " WHERE " + EventTable.eventName + "=? AND " + EventTable.eventDate + "=?",
                new String[]{name, date});  // Query matching events
        boolean exists = cursor.getCount() > 0;  // Check if any record exists
        cursor.close();  // Close the cursor to free resources
        return exists;  // Return true if the event exists
    }
}
