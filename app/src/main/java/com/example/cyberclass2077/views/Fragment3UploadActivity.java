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
import android.widget.TextView;
import android.widget.Toast;

import com.example.cyberclass2077.R;
import com.example.cyberclass2077.actions.ActionsCreator;
import com.example.cyberclass2077.dispatcher.Dispatcher;
import com.example.cyberclass2077.model.FileInfo;
import com.example.cyberclass2077.model.User;
import com.example.cyberclass2077.model.UserInfo;
import com.example.cyberclass2077.stores.FileInfoStore;
import com.example.cyberclass2077.stores.UserInfoStore;
import com.example.cyberclass2077.stores.UserStore;
import com.squareup.otto.Subscribe;

import java.io.File;

public class Fragment3UploadActivity extends AppCompatActivity {

    private ImageButton backButton;
    private TextView txt_upload_btn;

    private Dispatcher dispatcher;
    private ActionsCreator actionsCreator;
    private UserInfoStore userInfoStore;
    private UserInfo userInfo;

    private UserInfoStore userStore;
    private UserInfo user;
    private FileInfoStore fileInfoStore;
    private FileInfo fileInfo;
    private File sfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment3_uploadlist_layout);

        //跳转到用户个人主页
        backButton = findViewById(R.id.upload_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Fragment3UploadActivity.this, MainActivity.class);
                intent.putExtra("fragment",2);
                startActivity(intent);
                finish();
                //需要在finish和startActivity之后进行
                //第一个参数是需要打开的Activity进入时的动画，第二个是需要关闭的Activity离开时的动画
                overridePendingTransition(R.anim.anim_slide_from_right, R.anim.anim_slide_from_right);
            }
        });
        initDependencies();
        txt_upload_btn = findViewById(R.id.txt_upload_btn);
        txt_upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //selectVideo();
                String s = "/storage/emulated/0/DCIM/ScreenRecorder/Screenrecorder-2019-03-30-11-10-22-652.mp4";
                File sfile = new File(s);
                if(sfile.exists()) {
                    Log.d("进来了", "hao");
                    actionsCreator.uploadVideo("黄片", sfile);
                }
                else {

                }
            }
        });

    }

    @Subscribe
    void UploadVideo(FileInfoStore.UploadVideoEvent event) {
        if(event.isUploadVideoSuccessful) {
            Toast.makeText(this,
                    String.format("上传成功"),
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void initDependencies() {
        fileInfoStore = FileInfoStore.getInstance();
        //获取调度者单例
        dispatcher = Dispatcher.get();
        //获取动作创建者单例
        actionsCreator = ActionsCreator.get(dispatcher);
        //获取 用户 数据仓库单例
        dispatcher.register(fileInfoStore);
    }

    //选择视频
    private void selectVideo() {

        if (android.os.Build.BRAND.equals("Huawei")) {
            Intent intentPic = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentPic, 2);
        }
        if (android.os.Build.BRAND.equals("Xiaomi")) {//是否是小米设备,是的话用到弹窗选取入口的方法去选取视频
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "video/*");
            startActivityForResult(Intent.createChooser(intent, "选择要导入的视频"), 2);
        } else {//直接跳到系统相册去选取视频
            Intent intent = new Intent();
            if (Build.VERSION.SDK_INT < 19) {
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("video/*");
            } else {
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("video/*");
            }
            startActivityForResult(Intent.createChooser(intent, "选择要导入的视频"), 2);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(2, resultCode, data);
        if (resultCode == RESULT_OK && null != data && requestCode == 2) {
            {
                Uri uri = data.getData();

                String path = getRealPathFromURI(uri);
                Log.d("path", "path==" + path);
                File file = new File(path);
                Log.d("VVVVVVVV", file.getAbsolutePath());
                sfile = file;
                /*
                MediaMetadataRetriever mmr = new MediaMetadataRetriever();//实例化MediaMetadataRetriever对象
                mmr.setDataSource(file.getAbsolutePath());
                Bitmap bitmap = mmr.getFrameAtTime();//获得视频第一帧的Bitmap对象
                String duration = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION);//时长(毫秒)
                Log.d("ddd", "duration==" + duration);
                int int_duration = Integer.parseInt(duration);
                if (int_duration > 11000) {
                    Toast.makeText(getApplicationContext(), "视频时长已超过10秒，请重新选择", Toast.LENGTH_SHORT).show();
                    return;
                }
                */

            }
        }

    }

    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

}
