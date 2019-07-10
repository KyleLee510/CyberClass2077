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
import android.widget.Toast;

import com.example.cyberclass2077.R;
import com.example.cyberclass2077.actions.ActionsCreator;
import com.example.cyberclass2077.bean.DynamicPublishBean;
import com.example.cyberclass2077.controllers.ToNextActivity;
import com.example.cyberclass2077.dispatcher.Dispatcher;
import com.example.cyberclass2077.model.User;
import com.example.cyberclass2077.model.UserInfo;
import com.example.cyberclass2077.pictureselector.Constant;
import com.example.cyberclass2077.pictureselector.ImageUtils;
import com.example.cyberclass2077.pictureselector.PictureSelector;
import com.example.cyberclass2077.stores.DynamicStore;
import com.example.cyberclass2077.stores.UserInfoStore;
import com.example.cyberclass2077.stores.UserStore;
import com.example.cyberclass2077.views.MainActivity;
import com.example.cyberclass2077.views.UserDataSettingActivity;
import com.squareup.otto.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DynamicPublish extends AppCompatActivity{

    private ImageView ibtn;
    private ImageView imageView_choose;
    private TextView btn_publish;

    private Dispatcher dispatcher;
    private ActionsCreator actionsCreator;
    private UserInfoStore userInfoStore;
    private UserInfo userInfo;

    private UserStore userStore;
    private User user;
    private DynamicStore dynamicStore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dynamic_pulish);
        initDependencies();
        initWidget();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        dynamicStore.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        dynamicStore.unregister(this);
    }

    @Subscribe
    public void onSendDynamic(DynamicStore.SendDynamicEvent event) {
        if(event.isSendDynamicSuccessful) {
            Toast.makeText(DynamicPublish.this,
                    String.format("发表动态成功"),
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void initDependencies() {
        userStore = UserStore.getInstance(); //使用Store来进行传值判定
        user = userStore.getUser();
        //获取调度者单例
        dispatcher = Dispatcher.get();
        //获取动作创建者单例
        actionsCreator = ActionsCreator.get(dispatcher);
        //获取 用户 数据仓库单例
        dynamicStore = DynamicStore.getInstance();
        //在调度者里注册 用户 数据仓库，若已注册，不会重复注册
        dispatcher.register(dynamicStore);
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

    void initWidget() {
        //返回
        ibtn = findViewById(R.id.dyname_publish_back_button);
        ibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToNextActivity.to_NextActivityFinish(DynamicPublish.this, MainActivity.class, ToNextActivity.FRAGMENT2);
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
                //ImageUtils.saveBitmap(BitmapFactory.decodeFile(Constant.TEMP_PICTUREPATH), Constant.USERPHOTO_PATH + "/" + "dynamic");//缓冲用户头像至本地
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

                actionsCreator.sendDynamic(dynamicPublishBean, BitmapFactory.decodeFile(Constant.TEMP_PICTUREPATH)); //发布动态
                ToNextActivity.to_NextActivityFinish(DynamicPublish.this, MainActivity.class, ToNextActivity.FRAGMENT2);

            }
        });
    }
}
