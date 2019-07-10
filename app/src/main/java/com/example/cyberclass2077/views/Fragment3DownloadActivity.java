package com.example.cyberclass2077.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.cyberclass2077.R;
import com.example.cyberclass2077.adapter.CourseAdapter;
import com.example.cyberclass2077.bean.CourseBean;
import com.example.cyberclass2077.controllers.ToNextActivity;

import java.util.ArrayList;
import java.util.List;

public class Fragment3DownloadActivity extends AppCompatActivity {

    private ImageButton img_to_backUserlayout;
    private ListView listView_download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment3_downloadlist_layout);
        initWidget();
    }

    void initWidget() {

        //跳转到用户个人主页
        img_to_backUserlayout = findViewById(R.id.img_download_back_button);
        img_to_backUserlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToNextActivity.to_NextActivityFinish(Fragment3DownloadActivity.this,
                        MainActivity.class,
                        ToNextActivity.FRAGMENT3);
            }
        });
    }
}
