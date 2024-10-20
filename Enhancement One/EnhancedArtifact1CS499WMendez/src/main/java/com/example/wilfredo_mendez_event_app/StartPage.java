/*
 * ------------------------------------------------------------------------------
 * File: StartPage.java
 * Author: Wilfredo Mendez
 * Date: September 20, 2024
 * Version: 1.0
 *
 * Purpose:
 * This fragment serves as the starting page for the event planner Android app,
 * specifically handling the login process and user authentication. It collects
 * username and password input, preparing the user session for further interactions
 * within the app.
 *
 * Design Notes:
 * The StartPage fragment follows Android’s fragment lifecycle model. A default
 * constructor is included for fragment management by Android, and a static
 * `newInstance` method is used to create new instances with arguments, enabling
 * modular behavior. The fragment inflates the 'eventgrid' layout, which can later
 * be customized to show either an event list or a calendar view.
 *
 * Note: Ensure this class handles any changes in state or future UI interactions
 * gracefully to support seamless user login. Enhanced code areas are documented
 * with inline comments to simplify future edits.
 * ------------------------------------------------------------------------------
 */


package com.example.wilfredo_mendez_event_app;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * The StartPage fragment represents the entry point of the application where
 * users log in using their credentials. It manages user input for the username
 * and password, preparing data for the next interaction.
 */
public class StartPage extends Fragment {

    // Constants used to pass and store data in this fragment during runtime
    private static final String ARG_PARAM1 = "param1"; // Example: Username
    private static final String ARG_PARAM2 = "param2"; // Example: User role or session type

    // Variables to hold values assigned to this fragment when it loads
    private String mParam1;
    private String mParam2;

    /**
     * Default constructor (Required for Android's fragment system to manage this class)
     * Initializes a basic instance of the StartPage fragment.
     */
    public StartPage() {
        // Fragment requires an empty constructor to work with Android lifecycle methods.
    }

    /**
     * Static factory method to create a new instance of StartPage with provided arguments.
     * This allows easy passing of data when the fragment is instantiated.
     *
     * @param param1 String value for the first parameter (e.g., username)
     * @param param2 String value for the second parameter (e.g., session type)
     * @return A new instance of the StartPage fragment with assigned arguments.
     */
    public static StartPage newInstance(String param1, String param2) {
        StartPage fragment = new StartPage();
        Bundle args = new Bundle();

        // Storing passed parameters in a bundle to access them in the fragment lifecycle
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args); // Passing arguments into the fragment
        return fragment;
    }

    /**
     * Called when the fragment is created, initializing values passed via the bundle.
     * Handles the retrieval of any parameters assigned to this fragment.
     *
     * @param savedInstanceState Bundle containing the saved state of the fragment.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Retrieve the parameters stored in the bundle and assign them to local variables
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /**
     * Called to create and return the view hierarchy associated with the fragment.
     * This method inflates the layout that defines what the user sees.
     *
     * @param inflater LayoutInflater to inflate the XML layout
     * @param container Parent view to contain the fragment UI
     * @param savedInstanceState State information for restoring the fragment
     * @return The inflated view to display the fragment’s UI.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate and return the eventgrid layout, which holds the event data display
        return inflater.inflate(R.layout.eventgrid, container, false);
    }
}
