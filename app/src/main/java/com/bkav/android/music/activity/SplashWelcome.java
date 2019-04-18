package com.bkav.android.music.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bkav.android.music.activity.MainActivity;

public class SplashWelcome extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    launchHomeScreen();
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }

            }
        }).start();
    }
    private void launchHomeScreen()
    {
        Intent intent=new Intent(SplashWelcome.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
