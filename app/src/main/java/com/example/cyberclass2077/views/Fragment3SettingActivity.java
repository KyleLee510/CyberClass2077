package com.example.cyberclass2077.views;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.cyberclass2077.R;
import com.example.cyberclass2077.actions.ActionsCreator;
import com.example.cyberclass2077.dispatcher.Dispatcher;
import com.example.cyberclass2077.model.User;
import com.example.cyberclass2077.pictureselector.FileUtils;
import com.example.cyberclass2077.pictureselector.PermissionUtils;
import com.example.cyberclass2077.pictureselector.PictureSelector;
import com.example.cyberclass2077.stores.UserStore;
import com.squareup.otto.Subscribe;

import java.io.File;

public class Fragment3SettingActivity extends AppCompatActivity {

    private ImageButton backButton;
    private ConstraintLayout to_changePassword;
    private ConstraintLayout vLogout_ConsrtraintLayout;
    private ConstraintLayout vClear_buffer;

    //在这里声明其他引用变量
    private Dispatcher dispatcher;
    private ActionsCreator actionsCreator;
    private UserStore userStore;
    private User user;

    private final int PERMISSION_CODE_FIRST = 0x14;//权限请求码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment3_setting);
        initDependencies();
        initWidget();
    }

    @Override
    protected void onResume() {
        super.onResume();
        userStore.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        userStore.unregister(this);
    }

    private void initDependencies() {
        //获取调度者单例
        dispatcher = Dispatcher.get();
        //获取动作创建者单例
        actionsCreator = ActionsCreator.get(dispatcher);
        //获取 用户 数据仓库单例
        userStore = UserStore.getInstance();
        //在调度者里注册 用户 数据仓库，若已注册，不会重复注册
        dispatcher.register(userStore);
        user = userStore.getUser();
    }

    @Subscribe
    public void onLogoutEvent(UserStore.LogoutEvent event) {
        if(event.isLogoutSuccessful) {
            Toast.makeText(this,
                    String.format("退出成功!",userStore.getUser().getUserName()),
                    Toast.LENGTH_SHORT
            ).show();
        }
        else {
            Toast.makeText(this,
                    String.format("退出失败! %n，%s!",userStore.getUser().getUserName()),
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
    //
    void initWidget() {
        vClear_buffer = (ConstraintLayout) findViewById(R.id.constraintLayout_clear_buffer);

        vClear_buffer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String delFile = "/storage/emulated/0/PictureSelector";
                //String delFile = "/storage/emulated/0/PictureSelector.temp.jpg";
                File file = new File(delFile);
                boolean checkPermissionFirst = PermissionUtils.checkPermissionFirst(Fragment3SettingActivity.this, PERMISSION_CODE_FIRST,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE});
                if (checkPermissionFirst) {
                    deleteFile(file);
                }
                else {
                    Toast.makeText(Fragment3SettingActivity.this,
                            String.format("请给予权限再操作"),
                            Toast.LENGTH_SHORT
                    ).show();
                }

            }
        });
        //返回个人主页
        backButton = findViewById(R.id.setting_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Fragment3SettingActivity.this, MainActivity.class);
                intent.putExtra("fragment",2);
                startActivity(intent);
                finish();
                //需要在finish和startActivity之后进行
                //第一个参数是需要打开的Activity进入时的动画，第二个是需要关闭的Activity离开时的动画
                overridePendingTransition(R.anim.anim_slide_from_right, R.anim.anim_slide_from_right);
            }
        });
        //登录状态修改密码和退出登录才是可用的
        to_changePassword = (ConstraintLayout) findViewById(R.id.constraintLayout_change_password);
        vLogout_ConsrtraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout_logout);
        if(user.isLoginState()) {
            //to_changePassword.setVisibility(View.VISIBLE);
            //vLogout_ConsrtraintLayout.setVisibility(View.VISIBLE);
            //跳转到更改密码界面
            to_changePassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Fragment3SettingActivity.this, ChangePasswordActivity.class);
                    startActivity(intent);
                    finish();
                    //需要在finish和startActivity之后进行
                    //第一个参数是需要打开的Activity进入时的动画，第二个是需要关闭的Activity离开时的动画
                    overridePendingTransition(R.anim.anim_slide_from_right, R.anim.anim_slide_from_right);
                }
            });
            //退出控件
            vLogout_ConsrtraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actionsCreator.logout(user.getUserName());
                }
            });
        }
        else {
            //to_changePassword.setVisibility(View.INVISIBLE);
            //vLogout_ConsrtraintLayout.setVisibility(View.INVISIBLE);
        }
    }

    private void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                deleteFile(f);
            }
            //file.delete();//如要保留文件夹，只删除文件，请注释这行,感觉删文件夹有点慢，好像顺便已删除文件夹了
        } else if (file.exists()) {
            String filePath = file.getAbsolutePath();
            file.delete();
            //https://www.jianshu.com/p/1ffd18367cc4原因所在
            getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    MediaStore.Images.Media.DATA + "=?",
                    new String[]{filePath});
        }
    }

}
