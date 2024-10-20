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

    // Logcat tag
    private static final String TAG = "SmsNotificationsActivity";

    // Name of the app preference to store whether the user wants to receive notifications or not
    public static String PREFERENCE_RECEIVE_NOTIFICATIONS = "pref_receive_notifications";

    // Permissions code
    private final int REQUEST_SEND_SMS_CODE = 0;

    Button grantPermissionButton;

    SharedPreferences sharedPrefs;

    boolean receiveNotifications = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.smsnotification);

        grantPermissionButton = findViewById(R.id.buttonGrantPermission);

        // Listen for button click
        grantPermissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check for permissions
                if (hasPermissions()) {
                    Log.d(TAG, "Wants to receive notifications");
                    receiveNotifications = true;
                    grantPermissionButton.setEnabled(false); // Disable the button after permission is granted
                } else {
                    Log.d(TAG, "Does not want to receive notifications");
                    receiveNotifications = false;
                }

                // Update the user's preferences for this app
                savePreferences();
            }
        });

        // Access the default shared prefs
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        receiveNotifications = sharedPrefs.getBoolean(PREFERENCE_RECEIVE_NOTIFICATIONS, false);

        // Set the initial state of the button based on the permissions and preferences selected
        if (receiveNotifications
                && ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED
        ) {
            grantPermissionButton.setEnabled(false); // Disable the button if permission is already granted
        }
    }

    private boolean hasPermissions() {
        String smsPermission = Manifest.permission.SEND_SMS;

        // If the app doesn't already have permissions
        if (ContextCompat.checkSelfPermission(this, smsPermission)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we check again (ie: user didn't say "don't ask me again")
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, smsPermission)) {
                // Show a dialog box that explains why we need permissions
                new AlertDialog.Builder(this)
                        .setTitle(R.string.sms_notification_dialog_title)
                        .setMessage("Receive SMS notifications when events are coming up!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Actually request permission from the user
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
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
            } else {
                // Request permissions from the user
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{smsPermission},
                        REQUEST_SEND_SMS_CODE
                );
            }
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_SEND_SMS_CODE: {
                // Set the preference  based on changes to the granted permissions
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted!
                    Log.d(TAG, "Permission granted");
                    receiveNotifications = true;
                    grantPermissionButton.setEnabled(false); // Disable the button after permission is granted
                } else {
                    // Permission denied!
                    Log.d(TAG, "Permission denied");
                    receiveNotifications = false;
                }

                // Save the app preferences
                savePreferences();
            }
        }
    }

    private void savePreferences() {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean(PREFERENCE_RECEIVE_NOTIFICATIONS, receiveNotifications);
        editor.commit();
    }
}


