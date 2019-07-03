package com.example.cyberclass2077.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.cyberclass2077.R;
import com.example.cyberclass2077.actions.ActionsCreator;
import com.example.cyberclass2077.dispatcher.Dispatcher;
import com.example.cyberclass2077.model.User;
import com.example.cyberclass2077.stores.Store;
import com.example.cyberclass2077.stores.UserStore;
import com.squareup.otto.Subscribe;

public class LoginActivity extends AppCompatActivity {
    //在这里声明控件的引用变量
    private EditText vUserNameEditor;
    private EditText vPassWordEditor;
    private Button vLoginButton;
    private ImageButton vCancelButton; //add By Lee


    //在这里声明其他引用变量
    private Dispatcher dispatcher;
    private ActionsCreator actionsCreator;
    private UserStore userStore;


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
        dispatcher.unregister(userStore);
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
        vCancelButton = (ImageButton) findViewById(R.id.btn_cancel_login);

        //设置控件相应事件，这里采用匿名函数
        vLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击事件方法
                String userName=vUserNameEditor.getText().toString();
                String passWord=vPassWordEditor.getText().toString();
                actionsCreator.login(userName,passWord);


            }
        });

        vCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击事件方法
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("fragment",2);
                startActivity(intent);
                finish();
                //需要在finish和startActivity之后进行
                //第一个参数是需要打开的Activity进入时的动画，第二个是需要关闭的Activity离开时的动画
                overridePendingTransition(R.anim.anim_slide_from_right, R.anim.anim_slide_from_right);
            }
        } );

    }
    private void initDependencies() {
        //获取调度者单例
        dispatcher = Dispatcher.get();
        //获取动作创建者单例
        actionsCreator = ActionsCreator.get(dispatcher);
        //创建 用户 数据仓库
        userStore = UserStore.getInstance();
        //在调度者里注册 用户 数据仓库
        dispatcher.register(userStore);
    }

    //对事件总线EventBus发出的StoreChangeEvent（及其子类）做出响应                  ,
    @Subscribe
    public void onStoreChange(UserStore.LoginStateChangeEvent event){
        if(event.isLoginSuccessful==true&&event.isAlreadyLogin==false){
            Toast.makeText(this,
                    String.format("登录成功! %n欢迎您，%s!",userStore.getUser().getUserName()),
                    Toast.LENGTH_SHORT
            ).show();
        }
        else if(event.isLoginSuccessful==true&&event.isAlreadyLogin==true){
            Toast.makeText(this,
                    String.format("用户%s已登录%n现已重新登录！",userStore.getUser().getUserName()),
                    Toast.LENGTH_SHORT
            ).show();
        }
        else if(event.isLoginSuccessful==false&&event.isAlreadyLogin==false){
            Toast.makeText(this,
                    String.format("用户名或密码错误%n登录失败！",userStore.getUser().getUserName()),
                    Toast.LENGTH_SHORT
            ).show();
        }

    }

}
