package com.example.cyberclass2077.views;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cyberclass2077.R;
import com.example.cyberclass2077.actions.ActionsCreator;
import com.example.cyberclass2077.adapter.CourseAdapter;
import com.example.cyberclass2077.bean.CourseBean;
import com.example.cyberclass2077.controllers.ToNextActivity;
import com.example.cyberclass2077.dispatcher.Dispatcher;
import com.example.cyberclass2077.model.FileInfo;
import com.example.cyberclass2077.model.User;
import com.example.cyberclass2077.model.UserInfo;
import com.example.cyberclass2077.stores.FileInfoStore;
import com.example.cyberclass2077.stores.UserInfoStore;
import com.example.cyberclass2077.stores.UserStore;
import com.squareup.otto.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Fragment3UploadActivity extends AppCompatActivity {

    private ImageButton backButton;
    private TextView txt_upload_btn;
    private ListView listView_upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment3_uploadlist_layout);
        initWidget();

    }
    void initWidget() {

        listView_upload=(ListView)findViewById(R.id.id_contribution_list);
        List<CourseBean> courseBeanList=new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            CourseBean courseBean=new CourseBean();
            courseBeanList.add(courseBean);
        }
        listView_upload.setAdapter(new CourseAdapter(Fragment3UploadActivity.this,courseBeanList));


        //跳转到用户个人主页
        backButton = findViewById(R.id.upload_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToNextActivity.to_NextActivityFinish(Fragment3UploadActivity.this,
                        MainActivity.class,
                        ToNextActivity.FRAGMENT3);
            }
        });
        txt_upload_btn = findViewById(R.id.txt_upload_btn);
        txt_upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToNextActivity.to_NextActivityFinish(Fragment3UploadActivity.this, UploadVideoActivity.class);

            }
        });
    }
}
