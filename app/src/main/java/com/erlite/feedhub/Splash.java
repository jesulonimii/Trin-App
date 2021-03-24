package com.erlite.feedhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class Splash extends AppCompatActivity {

    SharedPrefs sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Handler handler = new Handler();


        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorThemeBlue));


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2500);

        sharedPrefs = new SharedPrefs(Splash.this);

        int getTheme = sharedPrefs.themeSettings;

        AppCompatDelegate.setDefaultNightMode(getTheme);



    }
}
