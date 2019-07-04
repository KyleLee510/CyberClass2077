package com.example.cyberclass2077.views;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.cyberclass2077.R;
import com.example.cyberclass2077.actions.ActionsCreator;
import com.example.cyberclass2077.dispatcher.Dispatcher;
import com.example.cyberclass2077.model.User;
import com.example.cyberclass2077.stores.UserStore;
import com.squareup.otto.Subscribe;

public class Fragment3SettingActivity extends AppCompatActivity {

    private ImageButton backButton;
    private ConstraintLayout to_changePassword;
    private ConstraintLayout vLogout_ConsrtraintLayout;

    //在这里声明其他引用变量
    private Dispatcher dispatcher;
    private ActionsCreator actionsCreator;
    private UserStore userStore;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment3_setting);

        initDependencies();
        user = userStore.getUser();

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

//        跳转到更改密码界面
        to_changePassword = (ConstraintLayout) findViewById(R.id.constraintLayout_change_password);
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
        vLogout_ConsrtraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout_logout);
        vLogout_ConsrtraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionsCreator.logout(user.getUserName());
            }
        });
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
    }

    @Subscribe
    void onLogoutEvent(UserStore.LogoutEvent event) {
        if(event.isLogoutSuccessful) {
            Toast.makeText(this,
                    String.format("退出成功! %n欢迎您，%s!",userStore.getUser().getUserName()),
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}
