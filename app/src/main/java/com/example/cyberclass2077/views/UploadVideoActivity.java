package com.example.cyberclass2077.views;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cyberclass2077.R;
import com.example.cyberclass2077.actions.ActionsCreator;
import com.example.cyberclass2077.controllers.ToNextActivity;
import com.example.cyberclass2077.dispatcher.Dispatcher;
import com.example.cyberclass2077.model.FileInfo;
import com.example.cyberclass2077.model.UserInfo;
import com.example.cyberclass2077.pictureselector.Constant;
import com.example.cyberclass2077.pictureselector.ImageUtils;
import com.example.cyberclass2077.pictureselector.PictureSelector;
import com.example.cyberclass2077.stores.FileInfoStore;
import com.example.cyberclass2077.stores.UserInfoStore;
import com.squareup.otto.Subscribe;

import java.io.File;

public class UploadVideoActivity extends AppCompatActivity {

    private ImageButton img_toBcak;
    private ImageView img_chooseVideo;
    private Button btn_change_cover;
    private TextView txt_confirm_uploadVideo;
    private EditText et_set_title;
    private Spinner sp_set_courseTag;


    private Dispatcher dispatcher;
    private ActionsCreator actionsCreator;
    private UserInfoStore userInfoStore;
    private UserInfo userInfo;

    private UserInfoStore userStore;
    private UserInfo user;
    private FileInfoStore fileInfoStore;
    private FileInfo fileInfo;
    private File sfile; //获取选择视频的路基

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_layout);
        initDependencies();
        initWidget();
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

    void initWidget() {
        img_toBcak = (ImageButton) findViewById(R.id.imageButton2);
        img_toBcak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToNextActivity.to_NextActivityFinish(UploadVideoActivity.this, Fragment3UploadActivity.class);
            }
        });
        img_chooseVideo = (ImageView) findViewById(R.id.img_choose_video);
        img_chooseVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectVideo();
            }
        });
        btn_change_cover = (Button) findViewById(R.id.btn_change_cover);
        btn_change_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector
                        .create(UploadVideoActivity.this, PictureSelector.SELECT_REQUEST_CODE)
                        .selectPicture(true, 500, 500, 1, 1);
            }
        });
        et_set_title = (EditText) findViewById(R.id.et_set_title);
        sp_set_courseTag = (Spinner) findViewById(R.id.sp_set_courseTag);
        txt_confirm_uploadVideo = (TextView) findViewById(R.id.txt_confirm_uploadVideo);
        txt_confirm_uploadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s_video_title = et_set_title.getText().toString();//获取视频标题
                String s_courseClass = sp_set_courseTag.getSelectedItem().toString(); //获取视频标签
                //ImageUtils.saveBitmap(BitmapFactory.decodeFile(Constant.TEMP_PICTUREPATH), Constant.USERPHOTO_PATH + "/" + user.getUserName());//缓冲用户选择视频封面至本地
                actionsCreator.uploadVideo(s_video_title, sfile, s_courseClass, BitmapFactory.decodeFile(Constant.TEMP_PICTUREPATH));
            }
        });
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
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && null != data && requestCode == 2) {
            {
                Uri uri = data.getData();

                String path = getRealPathFromURI(uri);
                Log.d("path", "path==" + path);
                File file = new File(path);
                Log.d("VVVVVVVV", file.getAbsolutePath());
                sfile = file; //获取要获得的视频路径
                MediaMetadataRetriever mmr = new MediaMetadataRetriever();//实例化MediaMetadataRetriever对象
                mmr.setDataSource(file.getAbsolutePath());
                Bitmap bitmap = mmr.getFrameAtTime();//获得视频第一帧的Bitmap对象
                ImageUtils.saveBitmap(bitmap, Constant.TEMP_PICTUREPATH);//将封面缓冲至本地
                img_chooseVideo.setImageBitmap(bitmap);
                img_chooseVideo.setScaleType(ImageView.ScaleType.FIT_XY);
                /*
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
        /*结果回调*/
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                String picturePath = data.getStringExtra(PictureSelector.PICTURE_PATH);
                img_chooseVideo.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                img_chooseVideo.setScaleType(ImageView.ScaleType.FIT_XY);

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
