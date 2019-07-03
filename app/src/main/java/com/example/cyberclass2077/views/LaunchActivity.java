package com.example.cyberclass2077.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cyberclass2077.R;

public class LaunchActivity extends AppCompatActivity {

    //设置启动图显示时间，现在是2秒
    private final int LAUNCH_DISPLAY_LENGHT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_layout);

        new android.os.Handler().postDelayed(new Runnable() {
            public void run() {
                Intent mainIntent = new Intent(LaunchActivity.this,MainActivity.class);
                LaunchActivity.this.startActivity(mainIntent);
                LaunchActivity.this.finish();
            }
        }, LAUNCH_DISPLAY_LENGHT);
    }
}
