/*
 * ------------------------------------------------------------------------------
 * File: Login.java
 * Author: Wilfredo Mendez
 * Date: September 17, 2024
 * Version: 1.0
 *
 * Purpose:
 * This fragment provides a login UI and supports login and registration functionalities
 * by interacting with a local SQLite database. The fragment handles validation checks to ensure proper
 * authentication and delivers user feedback through Toast messages. The bottom
 * navigation is hidden while the login screen is active to prevent users from
 * bypassing authentication. On successful login, the user is redirected to the
 * home screen (editEventFragment). New accounts can be registered through the
 * same interface, with duplicate accounts being prevented.
 *
 * Design Notes:
 * Parameters are passed using a bundle, allowing flexibility for future expansion
 * (e.g., pre-filling usernames from previous sessions).
 * The use of Toast notifications ensures user feedback is immediate and visible.
 * The app’s flow relies on Android’s Navigation Component for screen transitions.
 *
 * ------------------------------------------------------------------------------
 */

package com.example.wilfredo_mendez_event_app;

import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/*
 * Login Fragment: Manages the user login and registration screen.
 * Contains logic for handling user input, validating credentials,
 * and transitioning to the next screen upon successful authentication.
 */
public class Login extends Fragment {

    // Keys for passing optional parameters via a Bundle (unused but available for future use)
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Variables to store incoming parameter values
    private String mParam1;
    private String mParam2;

    // Database helper instance to manage user accounts in SQLite
    private LoginDatabase dbHelper;

    // Default constructor required by the Fragment class
    public Login() {}

    /*
     * Factory method to create a new instance of this fragment.
     * Allows the passing of parameters for flexibility (currently unused).
     */
    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);  // Save param1 in the bundle
        args.putString(ARG_PARAM2, param2);  // Save param2 in the bundle
        fragment.setArguments(args);  // Attach the bundle to the fragment
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new LoginDatabase(getContext());  // Initialize the database helper

        // Retrieve passed parameters, if any, to customize the login session
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);  // Assign param1 to mParam1
            mParam2 = getArguments().getString(ARG_PARAM2);  // Assign param2 to mParam2
        }
    }

    /*
     * Inflates the login screen layout and initializes UI elements
     * such as buttons and text fields. Also defines behavior for
     * login and registration actions.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the login layout XML into a View object
        View view = inflater.inflate(R.layout.login, container, false);

        // Reference the login and registration buttons
        Button loginButton = view.findViewById(R.id.loginButton);
        Button registerButton = view.findViewById(R.id.createAccountButton);

        // Reference the input fields for username and password
        EditText usernameEditText = view.findViewById(R.id.usernameEdit);
        EditText passwordEditText = view.findViewById(R.id.passwordEdit);

        // Hide the bottom navigation to restrict navigation before login
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.GONE);  // Navigation hidden on login screen

        /*
         * Login button click listener: Checks if the entered credentials
         * match any existing user in the database. If successful,
         * navigates to the home screen; otherwise, shows a failure message.
         */
        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();  // Get username from input
            String password = passwordEditText.getText().toString();  // Get password from input

            // Validate credentials through the database helper
            if (validationCheck(username, password)) {
                // Navigate to the event editing screen upon successful login
                Navigation.findNavController(view).navigate(R.id.editEventFragment);
            } else {
                // Display error message if login fails
                Toast.makeText(getContext(), "Invalid Username and Password", Toast.LENGTH_SHORT).show();
            }
        });

        /*
         * Register button click listener: Allows users to create a new account.
         * If the username is unique, the new account is saved in the database.
         * If the username already exists, an error message is displayed.
         */
        registerButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();  // Get new username
            String password = passwordEditText.getText().toString();  // Get new password

            // Attempt to add the new user to the database
            if (dbHelper.addUser(username, password)) {
                // Display success message if registration is successful
                Toast.makeText(getContext(), "User registered successfully", Toast.LENGTH_SHORT).show();
            } else {
                // Display error message if the username is already taken
                Toast.makeText(getContext(), "User already exists", Toast.LENGTH_SHORT).show();
            }
        });

        return view;  // Return the fully-inflated View to display the UI
    }

    /*
     * Helper method to validate user credentials.
     * Queries the database to check if the username and password match an existing user.
     */
    private boolean validationCheck(String username, String password) {
        return dbHelper.checkUser(username, password);  // Return true if credentials are valid
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Restore the bottom navigation visibility when leaving the login screen
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.VISIBLE);  // Show navigation bar again
    }
}
