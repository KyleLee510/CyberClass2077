package com.example.cyberclass2077.views.Dynamic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cyberclass2077.R;
import com.example.cyberclass2077.bean.DynamicPublishBean;
import com.example.cyberclass2077.pictureselector.Constant;
import com.example.cyberclass2077.pictureselector.ImageUtils;
import com.example.cyberclass2077.pictureselector.PictureSelector;
import com.example.cyberclass2077.stores.UserStore;
import com.example.cyberclass2077.views.UserDataSettingActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

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
                ImageUtils.saveBitmap(BitmapFactory.decodeFile(Constant.TEMP_PICTUREPATH), Constant.USERPHOTO_PATH + "/" + "dynamic");//缓冲用户头像至本地
                //当你想读取的时候使用    ImageView.setImageBitmap(Constant.USERPHOTO_PATH + "/" + "你定义的名字" + "jpg")

                //填充DynamicPublishBean
                DynamicPublishBean dynamicPublishBean=new DynamicPublishBean();
                TextView textView=findViewById(R.id.txt_dynamic_publish_content);
                dynamicPublishBean.setContent(textView.getText().toString());
                String strUserName=UserStore.getInstance().getUser().getUserName();
                dynamicPublishBean.setUserName(strUserName);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date=new Date(System.currentTimeMillis());
                String time=simpleDateFormat.format(date);
                dynamicPublishBean.setDate(time);

                //获取bitmap图片
                ImageView imageView=findViewById(R.id.imageView_choose);
                Drawable drawable=imageView.getDrawable();
                Bitmap bitmap=getBitmap(drawable);


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

    private Bitmap getBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        //canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }


    @Override
    public void onClick(View v) {

    }
}
