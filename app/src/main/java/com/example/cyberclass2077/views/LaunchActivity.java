package com.example.cyberclass2077.views;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cyberclass2077.R;
import com.example.cyberclass2077.controllers.ToNextActivity;
import com.example.cyberclass2077.pictureselector.PermissionUtils;

public class LaunchActivity extends AppCompatActivity {

    //设置启动图显示时间，现在是2秒
    private final int LAUNCH_DISPLAY_LENGHT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_layout);
        new android.os.Handler().postDelayed(new Runnable() {
            public void run() {
                ToNextActivity.to_NextActivityFinish(LaunchActivity.this, MainActivity.class, ToNextActivity.FRAGMENT1);
            }
        }, LAUNCH_DISPLAY_LENGHT);
    }
}
