package com.example.cyberclass2077.views.Dynamic;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.cyberclass2077.R;
import com.example.cyberclass2077.pictureselector.Constant;
import com.example.cyberclass2077.pictureselector.ImageUtils;
import com.example.cyberclass2077.pictureselector.PictureSelector;
import com.example.cyberclass2077.views.UserDataSettingActivity;

public class DynamicPublish extends AppCompatActivity implements View.OnClickListener{

    private ImageView ibtn;
    private ImageView imageView_choose;
    private Button btn_publish;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dynamic_pulish);

        //返回
        ibtn = findViewById(R.id.dyname_publish_back_button);
        ibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //图片选择
        imageView_choose = findViewById(R.id.imageView_choose);
        imageView_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector
                        .create(DynamicPublish.this, PictureSelector.SELECT_REQUEST_CODE)
                        .selectPicture(true, 500, 500, 1, 1);
            }
        });

        //发表
        btn_publish = findViewById(R.id.btn_publish);
        btn_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //该函数负责存储用户要使用的图像，第一个参数从临时保存的地方提取图片信息，第二个参数是设置的保存路径
                ImageUtils.saveBitmap(BitmapFactory.decodeFile(Constant.TEMP_PICTUREPATH), Constant.USERPHOTO_PATH + "/" + "你想保存的名字");//缓冲用户头像至本地
                //当你想读取的时候使用    ImageView.setImageBitmap(Constant.USERPHOTO_PATH + "/" + "你定义的名字" + "jpg")
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*结果回调*/
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                String picturePath = data.getStringExtra(PictureSelector.PICTURE_PATH);
                imageView_choose.setImageBitmap(BitmapFactory.decodeFile(picturePath)); //图片加载
                imageView_choose.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        }
    }


    @Override
    public void onClick(View v) {

    }
}
