package com.tvt.projectcuoikhoa.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.tvt.projectcuoikhoa.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.splash2, R.anim.anim_splash);
            }
        }, 5000);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
