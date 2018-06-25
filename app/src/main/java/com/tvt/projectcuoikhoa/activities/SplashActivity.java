package com.tvt.projectcuoikhoa.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tvt.projectcuoikhoa.R;

public class SplashActivity extends AppCompatActivity {
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

         handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_splash,R.anim.splash2);
            }
        },5000);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
