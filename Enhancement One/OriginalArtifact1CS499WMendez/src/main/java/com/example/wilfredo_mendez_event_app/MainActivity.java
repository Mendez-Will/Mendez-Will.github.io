package com.example.wilfredo_mendez_event_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavHostFragment navHostFrag = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        if(navHostFrag != null) {
            NavController navController = navHostFrag.getNavController();

            AppBarConfiguration appBarConfig = new AppBarConfiguration.Builder(
                    R.id.navigation_event, R.id.navigation_data,
                    R.id.navigation_settings).build();

            NavigationUI.setupActionBarWithNavController(this, navController,
                    appBarConfig);

            NavigationUI.setupWithNavController(navView, navController);

            navView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if(item.getItemId() == R.id.nav_home) {
                        navController.navigate(R.id.navigation_home);
                        return true;
                    }
                    else if(item.getItemId() == R.id.nav_data) {
                        navController.navigate((R.id.navigation_data));
                        return true;
                    }
                    else if(item.getItemId() == R.id.nav_setting) {
                        navController.navigate((R.id.navigation_settings));
                        return true;
                    }
                    return false;
                }
            });
        }

    }


}