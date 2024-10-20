/*
 * ------------------------------------------------------------------------------
 * File: MainActivity.java
 * Author: Wilfredo Mendez
 * Date: September 16, 2024
 * Version: 1.0
 *
 * Purpose:
 * This class serves as the main entry point for the event planner Android app.
 * It loads the main activity layout and sets up both the toolbar and bottom
 * navigation, providing a smooth user experience for navigating between
 * different sections of the app. It also links the toolbar to the fragment
 * navigation system, ensuring consistency across user interactions.
 *
 * Design Notes:
 * The MainActivity uses Android’s AppCompatActivity framework to integrate the
 * toolbar as the main action bar, providing a uniform look throughout the app.
 * Navigation between fragments is managed using a `NavController` and a
 * `BottomNavigationView` to simplify user interactions. Each icon in the bottom
 * navigation corresponds to a key section, such as events, data, and settings, ensuring
 * intuitive access to essential functionality.
 *
 * ------------------------------------------------------------------------------
 */


package com.example.wilfredo_mendez_event_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Loads the main screen layout for the app when it first opens
        setContentView(R.layout.activity_main);

        // Set up the top toolbar that gives the app a clean look
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);  // This makes the toolbar the app's main action bar

        // Bottom navigation setup - this is where users can switch between major sections
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Finding the navigation controller that manages switching between the screens
        NavHostFragment navHostFrag = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        if (navHostFrag != null) {
            // NavController handles the navigation between different fragments (or pages)
            NavController navController = navHostFrag.getNavController();

            // This configures which fragments (pages) can be accessed from the app's action bar
            AppBarConfiguration appBarConfig = new AppBarConfiguration.Builder(
                    R.id.navigation_event, R.id.navigation_data,
                    R.id.navigation_settings).build();

            // Link the action bar (top of the screen) with the navigation controller
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig);

            // Link the bottom navigation (the icons at the bottom) with the navigation controller
            NavigationUI.setupWithNavController(navView, navController);

            // Add a listener to handle switching between pages when the user taps a bottom icon
            navView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    // This handles what happens when users tap on the different icons

                    if (item.getItemId() == R.id.nav_home) {
                        // If the user taps the edit icon, navigate to the edit page
                        navController.navigate(R.id.editEventFragment);
                        return true;
                    }
                    else if (item.getItemId() == R.id.nav_data) {
                        // If they tap the data icon, navigate to the data screen
                        navController.navigate(R.id.navigation_data);
                        return true;
                    }
                    else if (item.getItemId() == R.id.nav_setting) {
                        // If they tap the settings icon, take them to the settings page
                        navController.navigate(R.id.navigation_settings);
                        return true;
                    }
                    return false;  // If none of the icons are tapped, do nothing
                }
            });
        }

    }

}
