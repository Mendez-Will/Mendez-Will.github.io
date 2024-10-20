/*
 * ------------------------------------------------------------------------------
 * File: LoginDatabase.java
 * Author: Wilfredo Mendez
 * Date: September 19, 2024
 * Version: 1.0
 *
 * Purpose:
 * This class handles the local SQLite database for managing user authentication
 * data, including usernames, passwords, and login state tracking. It provides
 * essential CRUD operations to add new users, verify credentials, and update
 * passwords.
 *
 * Design Notes:
 * The LoginDatabase extends the SQLiteOpenHelper class to manage the database
 * lifecycle, including table creation and upgrades. The `onCreate` method initializes
 * the database with a login table that stores user credentials, while `onUpgrade` ensures
 * smooth transitions during version changes by recreating the table if necessary.
 * The CRUD operations are designed to ensure secure access to user data, with the
 * `checkUser` method verifying credentials through parameterized queries to prevent
 * SQL injection attacks.
 *
 * ------------------------------------------------------------------------------
 */
package com.example.wilfredo_mendez_event_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This class manages the login database using SQLite. It defines the database
 * schema, creates the login table, and provides CRUD operations for managing user data.
 */
public class LoginDatabase extends SQLiteOpenHelper {

    // Database name and version constants
    private static final String DATABASE_NAME = "loginDatabase";
    private static final int DATABASE_VERSION = 1;

    /**
     * Internal class to define the login table schema.
     * Holds column names and the table name as static constants.
     */
    public static final class LoginTable {
        // Name of the login table
        private static final String EventTable = "login";

        // Column names for user data
        public static final String username = "username";  // Stores the username
        public static final String password = "password";  // Stores the password
        public static final String isLoggedIn = "loggedIn";  // Tracks login state (0 = logged out, 1 = logged in)
    }

    /**
     * Constructor for creating a new instance of the database.
     * @param context The context in which the database is used.
     */
    public LoginDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is first created.
     * Creates the login table with columns for ID, username, password, and login state.
     * @param db The SQLite database object.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create the login table
        db.execSQL("CREATE TABLE " + LoginTable.EventTable + "(" +
                "_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +  // Auto-incrementing primary key
                LoginTable.username + " TEXT, " +  // Username column
                LoginTable.password + " TEXT, " +  // Password column
                LoginTable.isLoggedIn + " INTEGER DEFAULT 0)");  // Default login state is 'logged out'
    }

    /**
     * Called when the database version changes.
     * Drops the existing table and creates a new one.
     * @param db The SQLite database object.
     * @param oldVersion The old version number.
     * @param newVersion The new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the table if it exists to ensure a fresh start
        db.execSQL("DROP TABLE IF EXISTS " + LoginTable.EventTable);
        // Recreate the table with the updated schema
        onCreate(db);
    }

    /**
     * Adds a new user to the login table.
     * @param username The username to be added.
     * @param password The password associated with the username.
     * @return True if the user was added successfully, otherwise false.
     */
    public boolean addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();  // Open the database for writing
        ContentValues values = new ContentValues();  // Prepare the key-value pairs

        // Insert username and password into the ContentValues object
        values.put(LoginTable.username, username);
        values.put(LoginTable.password, password);

        // Insert the new user into the table and store the result
        long result = db.insert(LoginTable.EventTable, null, values);

        // Check if the insertion was successful (-1 indicates failure)
        return result != -1;
    }

    /**
     * Verifies if the provided username and password match any record in the table.
     * @param username The username to be checked.
     * @param password The password to be checked.
     * @return True if the user exists, otherwise false.
     */
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();  // Open the database for reading

        // Execute a parameterized query to prevent SQL injection attacks
        Cursor cursor = db.rawQuery("SELECT * FROM " + LoginTable.EventTable +
                        " WHERE " + LoginTable.username + "=? AND " + LoginTable.password + "=?",
                new String[]{username, password});

        // Check if the query returned any results (i.e., user exists)
        boolean exists = cursor.getCount() > 0;

        cursor.close();  // Close the cursor to release resources
        return exists;
    }

    /**
     * Updates the password for the specified username.
     * @param username The username for which the password will be updated.
     * @param newPassword The new password to be set.
     * @return True if the password was updated successfully, otherwise false.
     */
    public boolean updatePassword(String username, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();  // Open the database for writing
        ContentValues values = new ContentValues();  // Prepare the key-value pairs

        // Insert the new password into the ContentValues object
        values.put(LoginTable.password, newPassword);

        // Update the password for the specified username
        int rowsAffected = db.update(LoginTable.EventTable, values,
                LoginTable.username + "=?", new String[]{username});

        // Check if the update was successful (at least one row affected)
        return rowsAffected > 0;
    }
}