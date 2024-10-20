/*
 * ------------------------------------------------------------------------------
 * File: smspermission.java
 * Author: Wilfredo Mendez
 * Date: September 19, 2024
 * Version: 1.0
 *
 * Purpose:
 * This activity handles requesting SMS permissions from the user and stores their
 * preference to receive event notifications. The permissions allow notifications
 * to be sent via SMS at the scheduled event times.
 *
 * Design Notes:
 * The class interacts with Android's permissions API and SharedPreferences to track
 * whether the user has granted permission and enabled notifications. It provides
 * a modular setup with a customizable button click listener, giving developers the option
 * to change the type of permissions requested (e.g., location permissions) if needed.
 * ------------------------------------------------------------------------------
 */

package com.example.wilfredo_mendez_event_app;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.Manifest;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
public class smspermission extends AppCompatActivity {

    // Logging tag to track events and permission changes
    private static final String TAG = "SmsNotificationsActivity";

    // Key to store user preference for receiving notifications (can be extended for other settings)
    public static String PREFERENCE_RECEIVE_NOTIFICATIONS = "pref_receive_notifications";

    // Code to identify the SMS permission request; can be changed if adding more permission requests
    private final int REQUEST_SEND_SMS_CODE = 0;

    // UI element for requesting SMS permissions from the user
    Button grantPermissionButton;

    // SharedPreferences object to store and retrieve user settings
    SharedPreferences sharedPrefs;

    // Boolean to track whether the user has opted to receive notifications
    boolean receiveNotifications = false;

    /**
     * Called when the activity is created.
     * Initializes UI components and loads user preferences for notifications.
     *
     * @param savedInstanceState Contains the saved state of the activity (if any).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Apply a slide animation when opening this screen
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        // Set the content view to the SMS notification layout
        setContentView(R.layout.smsnotification);

        // Initialize the permission button
        grantPermissionButton = findViewById(R.id.buttonGrantPermission);

        // Load user preferences to check if notifications were previously enabled
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        receiveNotifications = sharedPrefs.getBoolean(PREFERENCE_RECEIVE_NOTIFICATIONS, false);

        // Disable the permission button if notifications are enabled and permission is granted
        if (receiveNotifications
                && ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED) {
            grantPermissionButton.setEnabled(false);
        }

        // Set up the button click listener to handle permission requests
        grantPermissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the app has the necessary permissions
                if (hasPermissions()) {
                    Log.d(TAG, "Wants to receive notifications");
                    receiveNotifications = true;
                    grantPermissionButton.setEnabled(false); // Disable after granting permission
                } else {
                    Log.d(TAG, "Does not want to receive notifications");
                    receiveNotifications = false;
                }
                // Save the user's preference in SharedPreferences
                savePreferences();
            }
        });
    }

    /**
     * Checks whether the user has granted SMS permissions.
     * If not, it prompts the user with a dialog explaining why the permission is needed.
     *
     * @return true if the permission is granted; false otherwise.
     */
    private boolean hasPermissions() {
        String smsPermission = Manifest.permission.SEND_SMS;

        if (ContextCompat.checkSelfPermission(this, smsPermission)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, smsPermission)) {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.sms_notification_dialog_title)
                        .setMessage("Receive SMS notifications when events are coming up!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(
                                        smspermission.this,
                                        new String[]{smsPermission},
                                        REQUEST_SEND_SMS_CODE
                                );
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss(); // Close dialog if permission is denied
                            }
                        })
                        .create()
                        .show();
            } else {
                // Directly request permission if no rationale is needed
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{smsPermission},
                        REQUEST_SEND_SMS_CODE
                );
            }
            return false; // Permission not yet granted
        }
        return true; // Permission already granted
    }

    /**
     * Handles the result of the permission request.
     * Updates user preferences based on whether the permission was granted or denied.
     *
     * @param requestCode Identifies which permission request this result corresponds to.
     * @param permissions The permissions requested.
     * @param grantResults The results for the corresponding permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_SEND_SMS_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Permission granted");
                receiveNotifications = true;
                grantPermissionButton.setEnabled(false); // Disable button after permission is granted
            } else {
                Log.d(TAG, "Permission denied");
                receiveNotifications = false;
            }
            // Save the updated preference
            savePreferences();
        }
    }

    /**
     * Saves the user's preference for receiving notifications in SharedPreferences.
     */
    private void savePreferences() {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean(PREFERENCE_RECEIVE_NOTIFICATIONS, receiveNotifications);
        editor.commit(); // Apply the changes
    }
}


