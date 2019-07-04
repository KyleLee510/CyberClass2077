package com.example.cyberclass2077.views;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cyberclass2077.R;
import com.example.cyberclass2077.actions.ActionsCreator;
import com.example.cyberclass2077.dispatcher.Dispatcher;
import com.example.cyberclass2077.model.User;
import com.example.cyberclass2077.model.UserInfo;
import com.example.cyberclass2077.stores.UserInfoStore;
import com.example.cyberclass2077.stores.UserStore;
import com.squareup.otto.Subscribe;

import java.util.Calendar;

public class UserDataSettingActivity extends AppCompatActivity {

    private ImageButton to_back;//返回上一个页面
    private TextView txt_to_DatePickerDialog;//跳转到时间选择对话框
    private TextView txt_to_save_UserInfo;
    private TextView txt_username;//显示当前用户的用户名
    private Calendar cal;//显示当前日期
    private EditText et_set_nick_name;
    private Spinner sp_set_gender;
    private LinearLayout linearlayout_set_image;

    private int mYear;
    private int mMonth;
    private int mDay;

    //在这里声明其他引用变量
    private Dispatcher dispatcher;
    private ActionsCreator actionsCreator;
    private UserInfoStore userInfoStore;
    private UserInfo userInfo;
    private UserStore userStore;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_data_setting_layout);
        initDependencies();
        initWidget();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //dispatcher.unregister(userStore);
    }
    @Override
    protected void onResume() {
        super.onResume();
        userInfoStore.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        userInfoStore.unregister(this);
    }

    private void initDependencies() {
        userStore = UserStore.getInstance(); //使用Store来进行传值判定
        user = userStore.getUser();
        //获取调度者单例
        dispatcher = Dispatcher.get();
        //获取动作创建者单例
        actionsCreator = ActionsCreator.get(dispatcher);
        //获取 用户 数据仓库单例
        userInfoStore = UserInfoStore.getInstance();
        userInfo = userInfoStore.getUserInfo();
        userInfo.setUserName(user.getUserName());
        //在调度者里注册 用户 数据仓库，若已注册，不会重复注册
        dispatcher.register(userInfoStore);
    }

    @Subscribe
    public void onUpdateUserInfo(UserInfoStore.UpdateUserInfoEvent event) {
        if(event.isUpdateUserInfoSuccessful) {
            Toast.makeText(this,
                    String.format("您已更新"),
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    void initWidget() {
        to_back = (ImageButton) findViewById(R.id.btn_dataSetting_back);
        txt_to_DatePickerDialog = (TextView)  findViewById(R.id.txt_set_borndate);
        cal = Calendar.getInstance();
        txt_to_save_UserInfo = (TextView) findViewById(R.id.txt_userInfo_save);
        txt_username = (TextView) findViewById(R.id.txt_userInfo_username);
        et_set_nick_name = (EditText) findViewById(R.id.et_set_nick_name);
        sp_set_gender = (Spinner) findViewById(R.id.sp_set_gender);
        linearlayout_set_image = (LinearLayout) findViewById(R.id.linearlayout_set_image);

        txt_username.setText(user.getUserName());//设置显示当前用户的用户名
        //返回个人主页
        to_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击事件方法
                Intent intent = new Intent(UserDataSettingActivity.this, MainActivity.class);
                intent.putExtra("fragment",2);
                startActivity(intent);
                finish();
                //需要在finish和startActivity之后进行
                //第一个参数是需要打开的Activity进入时的动画，第二个是需要关闭的Activity离开时的动画
                overridePendingTransition(R.anim.anim_slide_from_right, R.anim.anim_slide_from_right);
            }
        });

        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH) + 1;
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        //日期选择界面
        final DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mYear = year;
                        mMonth = month + 1;
                        mDay = dayOfMonth;
                        final String data =  mYear + "-" + mMonth + "-" + mDay;
                        txt_to_DatePickerDialog.setText(data);//修改text显示
                    }
                },
                mYear, mMonth, mDay);

        //点击弹出日期选择框
        txt_to_DatePickerDialog.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                //点击事件方法
                datePickerDialog.show();
            }
        });

        txt_to_save_UserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUserInfo();
                actionsCreator.updateUserInfo(userInfo);
            }
        });
    }

    void setUserInfo() {
        String userName = user.getUserName();
        String nickName = et_set_nick_name.getText().toString();
        String gender = sp_set_gender.getSelectedItem().toString();
        String dateBorn = txt_to_DatePickerDialog.getText().toString();
        userInfo.setUserName(userName);
        Log.d("test",userInfo.getUserName());
        userInfo.setNickName(nickName);
        userInfo.setGender(gender);
        userInfo.setBirthDate(dateBorn);
        Log.d("在哪里",dateBorn);
    }
}
