package com.example.aegisapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SpashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash);

        Thread splashScreen = new Thread(){
            public void run(){
                try {
                    sleep(2 * 1000);
                    startActivity(new Intent(SpashActivity.this, Signup.class));
                    finish();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };
        splashScreen.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

