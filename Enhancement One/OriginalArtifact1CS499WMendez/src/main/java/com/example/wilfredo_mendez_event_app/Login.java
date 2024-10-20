package com.example.wilfredo_mendez_event_app;

import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class Login extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private EventDatabase dbHelper;

    public Login() {
        // Required empty public constructor
    }


    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new EventDatabase(getContext());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.login, container, false);

        Button loginButton = view.findViewById(R.id.loginButton);
        Button registerButton = view.findViewById(R.id.createAccountButton);

        EditText usernameEditText = view.findViewById(R.id.usernameEdit);
        EditText passwordEditText = view.findViewById(R.id.passwordEdit);

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.nav_view);

        // Hide the bottom navigation view
        bottomNavigationView.setVisibility(View.GONE);

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            boolean validated = validationCheck(username, password);
            if (validated) {
                Navigation.findNavController(view).navigate(R.id.navigation_home);
            } else {
                Toast.makeText(getContext(), "Invalid Username and Password", Toast.LENGTH_SHORT).show();
            }
        });

        registerButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            boolean added = dbHelper.addUser(username, password);
            if (added) {
                Toast.makeText(getContext(), "User registered successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "User already exists", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private boolean validationCheck(String username, String password) {
        return dbHelper.checkUser(username, password);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Get reference to the bottom navigation view
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.nav_view);

        // Show the bottom navigation view
        bottomNavigationView.setVisibility(View.VISIBLE);
    }
}
