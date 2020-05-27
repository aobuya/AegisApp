package com.example.aegisapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ReporterAcitivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporter_acitivity);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                new HomeFragment()).commit();
        //bottomNavigationBar

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;
                switch (menuItem.getItemId()){
                    case R.id.mine:
                        selectedFragment = new MyReports();
                        break;
                    case R.id.help_line:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.all_reports:
                        selectedFragment = new Reports();

                       selectedFragment = new AllReports();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                        selectedFragment).commit();
                return  true;
            }
        });
    }
}
