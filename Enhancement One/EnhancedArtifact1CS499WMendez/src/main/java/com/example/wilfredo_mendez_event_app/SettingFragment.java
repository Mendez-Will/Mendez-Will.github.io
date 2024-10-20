/*
 * File: SettingFragment.java
 * Author: Wilfredo Mendez
 * Date: September 17, 2024
 * Version: 1.0
 *
 * Purpose:
 * This fragment handles notification permissions for the event planner app.
 * It displays a button allowing users to enable notifications if permissions
 * have not already been granted. The design accounts for Android version differences,
 * specifically API level 33 (TIRAMISU) and above, where notification permissions need to be
 * requested explicitly. On older versions, notifications are enabled by default without the
 * need for user intervention.
 *
 * Design Notes:
 * - This fragment follows modern Android design patterns, using a `Fragment` to encapsulate
 *   UI and logic for permission handling.
 * - Toast messages are implemented for user feedback regarding permission status.
 * - Code structure allows easy future modifications, such as changing the layout or
 *   handling different permission statuses.
 */

package com.example.wilfredo_mendez_event_app;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

public class SettingFragment extends Fragment {

    // Request code for identifying the notification permission request
    private static final int REQUEST_NOTIFICATION_PERMISSION = 1;

    /**
     * Public constructor required for fragment instantiation.
     * This ensures that the system can recreate the fragment during configuration changes.
     */
    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Inflates the fragment's view and sets up the button to handle notification permissions.
     *
     * @param inflater The LayoutInflater used to inflate the layout.
     * @param container The parent view this fragment belongs to.
     * @param savedInstanceState Saved state bundle for restoring the fragment's previous state.
     * @return The view representing the fragment's UI.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for the fragment, binding it to the specified XML layout file.
        View view = inflater.inflate(R.layout.smsnotification, container, false);

        // Find the button and set a click listener to handle permission requests.
        Button requestPermissionButton = view.findViewById(R.id.buttonGrantPermission);
        requestPermissionButton.setOnClickListener(v -> checkAndRequestNotificationPermission());

        return view;
    }

    /**
     * Checks the device's API version and whether notification permissions are enabled.
     * Requests notification permissions if the API level is 33+ and notifications are disabled.
     * Provides feedback to the user through Toast messages.
     */
    private void checkAndRequestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // For API 33 (TIRAMISU) and above, check if notifications are enabled.
            if (!NotificationManagerCompat.from(getContext()).areNotificationsEnabled()) {
                // Request notification permission if not already granted.
                requestPermissions(
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                        REQUEST_NOTIFICATION_PERMISSION
                );
            } else {
                // Inform the user if permission is already granted.
                Toast.makeText(getContext(),
                        "Notification permission already granted",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            // For older devices, no explicit permission is required.
            Toast.makeText(getContext(),
                    "No permission required for notifications on this device",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Callback function to handle the result of the permission request.
     *
     * @param requestCode The request code identifying the permission request.
     * @param permissions The requested permissions.
     * @param grantResults The results for the requested permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // Handle the result only if it matches the notification permission request code.
        if (requestCode == REQUEST_NOTIFICATION_PERMISSION) {
            // Check if permission was granted.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(),
                        "Notification permission granted",
                        Toast.LENGTH_SHORT).show();
            } else {
                // Inform the user if the permission was denied.
                Toast.makeText(getContext(),
                        "Notification permission denied",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
