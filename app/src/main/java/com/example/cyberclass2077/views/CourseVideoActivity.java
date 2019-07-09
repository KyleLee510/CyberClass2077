package com.example.cyberclass2077.views;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.cyberclass2077.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CourseVideoActivity extends Activity {

    @BindView(R.id.vv_videoView)
    VideoView mVvVideoView;
    Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_videoview);
        mUnbinder = ButterKnife.bind(this);
        requestSDpermission();
        initVideoPath();
        initBind();
    }

    /**
     * videoView和MediaController绑定
     */
    private void initBind() {
        MediaController mediaController = new MediaController(this);
        mVvVideoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(mVvVideoView);
    }
    //注意：必须互相设置进行绑定

    /**
     * 初始化本地或网络播放路径
     */
    private void initVideoPath() {
        if(getIntent().getBooleanExtra("islocal",true))//islocal为true播放本地视频，为false从服务器获取
        {
            mVvVideoView.setVideoPath(getLocalPath());
        }
        else
        {
            mVvVideoView.setVideoURI(Uri.parse(getIntent().getStringExtra("VideoPath")));
        }
    }
    //提示：网络测试的话Tomcat下webapps下面放vivo.mp4

    /**
     * 获取本地路径
     *
     * @return
     */
//    VideoPath为本地视频时，输入Environment.getExternalStorageDirectory()的后续地址
//    （getExternalStorageDirectory()在小米8中为/storage/emulated/0，即输入/tencent/TIMfile_recv/20190310_174858.mp4），
//

    @NonNull
    private String getLocalPath() {

        return new File(Environment.getExternalStorageDirectory(), getIntent().getStringExtra("VideoPath")).getPath();
    }

    private void requestSDpermission() {
        if (ContextCompat.checkSelfPermission(CourseVideoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CourseVideoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            initVideoPath();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initVideoPath();
                } else {
                    Toast.makeText(this, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        if (mVvVideoView != null) {
            mVvVideoView.suspend();
        }
    }

}
