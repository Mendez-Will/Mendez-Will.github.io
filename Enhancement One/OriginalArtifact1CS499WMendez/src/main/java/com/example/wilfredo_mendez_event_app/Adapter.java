package com.example.wilfredo_mendez_event_app;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter<Adapter.EventViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    public Adapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {

        public TextView eventNameTextView;
        public TextView eventDateTimeTextView;
        public TextView eventDescriptionTextView;
        public Button editButton;
        public Button deleteButton;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventNameTextView = itemView.findViewById(R.id.textViewEventName);
            eventDateTimeTextView = itemView.findViewById(R.id.textViewEventDateTime);
            eventDescriptionTextView = itemView.findViewById(R.id.textViewEventDescription);
            editButton = itemView.findViewById(R.id.buttonEdit);
            deleteButton = itemView.findViewById(R.id.buttonDelete);
        }
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.eventdisplay, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        String eventName = mCursor.getString(mCursor.getColumnIndex(EventDatabase.EventTable.title_of_event));
        String eventDateTime = mCursor.getString(mCursor.getColumnIndex(EventDatabase.EventTable.date_of_event));
        String eventDescription = mCursor.getString(mCursor.getColumnIndex(EventDatabase.EventTable.description));

        holder.eventNameTextView.setText(eventName);
        holder.eventDateTimeTextView.setText(eventDateTime);
        holder.eventDescriptionTextView.setText(eventDescription);

        holder.editButton.setOnClickListener(v -> {
            // Handle edit button click
            Toast.makeText(mContext, "Edit button clicked", Toast.LENGTH_SHORT).show();
        });

        holder.deleteButton.setOnClickListener(v -> {
            // Handle delete button click
            Toast.makeText(mContext, "Delete button clicked", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }

        mCursor = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}
