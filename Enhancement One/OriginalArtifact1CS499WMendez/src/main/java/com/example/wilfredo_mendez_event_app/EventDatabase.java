package com.example.wilfredo_mendez_event_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class EventDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "database";
    private static final int DATABASE_VERSION = 1;

    public static final class EventTable implements BaseColumns {
        private static final String eventTable = "event";
        private static final String username = "username";
        private static final String password = "password" ;
        private static final String isLoggedIn = "loggedIn";
        public static final String title_of_event = "title";
        public static final String date_of_event = "date";
        public static final String description = "description";
    }
    public EventDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + EventTable.eventTable + "(" +
                EventTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EventTable.username + " TEXT, " +
                EventTable.password + " TEXT, " +
                EventTable.isLoggedIn + " INTEGER DEFAULT 0, " +
                EventTable.title_of_event + " TEXT, " +
                EventTable.date_of_event + " TEXT, " +
                EventTable.description + " TEXT)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + EventTable.eventTable);
        onCreate(db);
    }

    public boolean checkUser(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + EventTable.eventTable +" WHERE " + EventTable.username + "=? AND " + EventTable.password + "=?", new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }


    public boolean addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EventTable.username, username);
        values.put(EventTable.password, password);
        long result = db.insert(EventTable.eventTable, null, values);
        return result != -1;
    }


    public boolean updatePassword(String username, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EventTable.password, newPassword);
        int rowsAffected = db.update(EventTable.eventTable, values, EventTable.username + "=?", new String[]{username});
        return rowsAffected > 0;
    }

    public void addEvent (String title, String date, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EventTable.title_of_event, title);
        values.put(EventTable.date_of_event, date);
        values.put(EventTable.description, description);
        db.insert(EventTable.eventTable, null, values);
        db.close();
    }

    public void deleteEvent(long eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EventTable.eventTable, EventTable._ID + "=?", new String[]{String.valueOf(eventId)});
        db.close();
    }

    public void updateEvent(long eventId, String title, String date, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EventTable.title_of_event, title);
        values.put(EventTable.date_of_event, date);
        values.put(EventTable.description, description);
        db.update(EventTable.eventTable, values, EventTable._ID + "=?", new String[]{String.valueOf(eventId)});
        db.close();
    }

    public Cursor getAllEvents() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(EventTable.eventTable, new String[]{EventTable._ID, EventTable.username, EventTable.password, EventTable.title_of_event, EventTable.date_of_event, EventTable.description}, null, null, null, null, null);
    }

}



