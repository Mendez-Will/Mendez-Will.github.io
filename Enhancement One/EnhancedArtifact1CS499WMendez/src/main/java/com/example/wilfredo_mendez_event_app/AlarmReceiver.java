/*
 * ------------------------------------------------------------------------------
 * File: AlarmReceiver.java
 * Author: Wilfredo Mendez
 * Date: September 18, 2024
 * Version: 1.0
 *
 * Purpose:
 * The `AlarmReceiver` class is responsible for receiving broadcast intents when
 * an alarm triggers, and it creates a notification to remind the user about an
 * upcoming event. This class is a crucial part of the event reminder system in
 * the app, ensuring users receive timely alerts for their scheduled events.
 *
 * Design Notes:
 * 1. The class extends `BroadcastReceiver` to handle alarm events set by the app.
 * 2. It creates a notification channel for devices running Android 8.0 (API 26) or later,
 *    following Android's new notification management requirements.
 * 3. A `PendingIntent` is used to open the main activity when the user taps the notification.
 * 4. This implementation ensures the notification auto-dismisses upon tapping, preventing
 *    clutter in the notification bar.
 * 5. Future improvements could include setting unique notification IDs for multiple events
 *    and handling more complex actions directly from the notification.
 * ------------------------------------------------------------------------------
 */

package com.example.wilfredo_mendez_event_app;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;

/**
 * `AlarmReceiver` handles alarm events by generating notifications.
 * This class triggers when an alarm is broadcast, ensuring users receive
 * reminders about upcoming events.
 */
public class AlarmReceiver extends BroadcastReceiver {

    /**
     * Called when the receiver receives a broadcast intent (i.e., when an alarm goes off).
     * This method builds and displays a notification to remind the user about an event.
     *
     * @param context The application context used to access system services.
     * @param intent  The intent carrying the event message to be displayed in the notification.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        // Extract the message for the event from the intent extras
        String title = "Event Reminder";  // Title of the notification
        String message = intent.getStringExtra("eventMessage");  // Event message passed via intent

        // Get the system notification manager to issue notifications
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Channel ID to group notifications (required for Android 8.0+)
        String channelId = "event_reminders";

        // Check Android version and create a notification channel if required
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Define the notification channel with a name and importance level
            NotificationChannel channel = new NotificationChannel(
                    channelId, "Event Notifications", NotificationManager.IMPORTANCE_HIGH);

            // Register the notification channel with the system
            notificationManager.createNotificationChannel(channel);
        }

        // Create an intent that opens the main activity when the notification is clicked
        Intent notificationIntent = new Intent(context, MainActivity.class);

        // Wrap the intent in a PendingIntent to grant permission for future use
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Build the notification with all necessary attributes
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.event)  // Notification icon (replace with your icon resource)
                .setContentTitle(title)  // Set the title of the notification
                .setContentText(message)  // Set the notification message (event details)
                .setPriority(NotificationCompat.PRIORITY_HIGH)  // High priority for immediate delivery
                .setContentIntent(pendingIntent)  // Set the intent to open MainActivity on tap
                .setAutoCancel(true);  // Auto-dismiss the notification on click

        // Display the notification using a unique ID (ID 1 used here)
        notificationManager.notify(1, builder.build());
    }
}

