package com.example.cyberclass2077.views;

import android.Manifest;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.cyberclass2077.R;
import com.example.cyberclass2077.actions.ActionsCreator;
import com.example.cyberclass2077.dispatcher.Dispatcher;
import com.example.cyberclass2077.model.User;
import com.example.cyberclass2077.pictureselector.PermissionUtils;
import com.example.cyberclass2077.stores.Store;
import com.example.cyberclass2077.stores.UserStore;
import com.squareup.otto.Subscribe;

import java.io.File;

public class LoginActivity extends AppCompatActivity {
    //在这里声明控件的引用变量
    private EditText vUserNameEditor;
    private EditText vPassWordEditor;
    private Button vLoginButton;
    private Button vSignupButton; //注册按钮
    private ImageButton vCancelButton; //add By Lee



    //在这里声明其他引用变量
    private Dispatcher dispatcher;
    private ActionsCreator actionsCreator;
    private UserStore userStore;
    private final int PERMISSION_CODE_FIRST = 0x14;//权限请求码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        initDependencies();
        setupView();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
       // dispatcher.unregister(userStore);
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


    private void setupView(){
        //根据控件ID设置控件引用变量
        vUserNameEditor=(EditText)findViewById(R.id.et_username_login);
        vPassWordEditor=(EditText)findViewById(R.id.et_password_login);
        vLoginButton=(Button)findViewById(R.id.btn_login_login);
        vSignupButton = (Button) findViewById(R.id.btn_signup_login);
        vCancelButton = (ImageButton) findViewById(R.id.btn_cancel_login);

        vUserNameEditor.setImeOptions(EditorInfo.IME_ACTION_DONE);
        vUserNameEditor.setSingleLine();

        //设置控件相应事件，这里采用匿名函数
        vLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击事件方法
                String userName=vUserNameEditor.getText().toString();
                String passWord=vPassWordEditor.getText().toString();
                boolean checkPermissionFirst = PermissionUtils.checkPermissionFirst(LoginActivity.this, PERMISSION_CODE_FIRST,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA});
                if (checkPermissionFirst) {
                    actionsCreator.login(userName,passWord);

            }
                else {
                    Toast.makeText(LoginActivity.this,
                            String.format("请给予权限再登录"),
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });

        vSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转至注册页面
                //点击事件方法
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
                //需要在finish和startActivity之后进行
                //第一个参数是需要打开的Activity进入时的动画，第二个是需要关闭的Activity离开时的动画
                overridePendingTransition(R.anim.anim_slide_from_right, R.anim.anim_slide_from_right);
            }
        });

        vCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpActivity();
            }
        } );

    }
    private void initDependencies() {
        //获取调度者单例
        dispatcher = Dispatcher.get();
        //获取动作创建者单例
        actionsCreator = ActionsCreator.get(dispatcher);
        //获取 用户 数据仓库
        userStore = UserStore.getInstance();
        //在调度者里注册 用户 数据仓库
        dispatcher.register(userStore);
    }

    //对事件总线EventBus发出的StoreChangeEvent（及其子类）做出响应                  ,
    @Subscribe
    //函数取名不重要，甚至可以重载，重要的是参数的类型一定要匹配上对应的事件
    public void onLoginEvent(UserStore.LoginStateChangeEvent event){
        if(event.isLoginSuccessful==true&&event.isAlreadyLogin==false){
            Toast.makeText(this,
                    String.format("登录成功! %n欢迎您，%s!",userStore.getUser().getUserName()),
                    Toast.LENGTH_SHORT
            ).show();
            jumpActivity();
        }
        else if(event.isLoginSuccessful==true&&event.isAlreadyLogin==true){
            Toast.makeText(this,
                    String.format("用户%s已登录%n现已重新登录！",userStore.getUser().getUserName()),
                    Toast.LENGTH_SHORT
            ).show();
            jumpActivity();
        }
        else if(event.isLoginSuccessful==false&&event.isAlreadyLogin==false){
            Toast.makeText(this,
                    String.format("用户名或密码错误%n登录失败！",userStore.getUser().getUserName()),
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    public void jumpActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("fragment",2);
        startActivity(intent);
        finish();
        //需要在finish和startActivity之后进行
        //第一个参数是需要打开的Activity进入时的动画，第二个是需要关闭的Activity离开时的动画
        overridePendingTransition(R.anim.anim_slide_from_right, R.anim.anim_slide_from_right);
    }

}
