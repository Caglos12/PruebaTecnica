package com.example.pruebatecnica.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.splashscreen.SplashScreen;
import com.example.pruebatecnica.ui.home.HomeActivity;

public class MainActivity extends AppCompatActivity {

    private static final long SPLASH_DURATION_MS = 1200L;
    private boolean readyToContinue = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);

        splashScreen.setKeepOnScreenCondition(() -> !readyToContinue);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            readyToContinue = true;
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }, SPLASH_DURATION_MS);
    }
}
