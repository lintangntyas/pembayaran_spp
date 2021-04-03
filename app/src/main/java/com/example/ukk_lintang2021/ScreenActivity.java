package com.example.ukk_lintang2021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.ukk_lintang2021.Activities.Login.LoginActivity;

public class ScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);
        getSupportActionBar().hide();

        int SCREEN_TIME = 5000; // 5 DETIK

        new Handler().postDelayed(new Runnable()    {
            @Override
            public void run() {
                Intent intent = new Intent(ScreenActivity.this, LoginActivity.class);
                ScreenActivity.this.startActivity(intent);
                ScreenActivity.this.finish();
            }
        }, SCREEN_TIME);
    }
}