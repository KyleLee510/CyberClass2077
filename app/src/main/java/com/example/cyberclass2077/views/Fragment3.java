package com.example.cyberclass2077.views;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cyberclass2077.R;
import com.example.cyberclass2077.actions.ActionsCreator;
import com.example.cyberclass2077.dispatcher.Dispatcher;
import com.example.cyberclass2077.model.User;
import com.example.cyberclass2077.model.UserInfo;
import com.example.cyberclass2077.pictureselector.FileUtils;
import com.example.cyberclass2077.pictureselector.PermissionUtils;
import com.example.cyberclass2077.stores.UserInfoStore;
import com.example.cyberclass2077.stores.UserStore;
import com.squareup.otto.Subscribe;

import java.io.File;


public class Fragment3 extends Fragment {

    ConstraintLayout to_setting;
    ConstraintLayout to_contribution;
    ConstraintLayout to_login;
    Boolean isLogin = false; //默认false未登录状态
    Button btnCheckin;
    TextView txtUserName;
    TextView txtAccountnumber;
    ImageView imagePhoto;
    TextView show_lv;




    //在这里声明其他引用变量
    private String[] lv_tag_content;//签到的等级tag
    private int check_in_day=0;//连续签到的天数
    boolean is_check_in=false;//今日签到状态

    private Dispatcher dispatcher;
    private ActionsCreator actionsCreator;
    private UserInfoStore userInfoStore;
    private UserInfo userInfo;

    private UserStore userStore;
    private User user;

    private final int PERMISSION_CODE_FIRST = 0x14;//权限请求码

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_layout, container, false);
        initDependencies();

        initWidget(view);   //初始化控件
        userLogin();   //用户是否登录的界面设置

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //dispatcher.unregister(userStore);
    }
    @Override
    public void onResume() {
        super.onResume();
        userInfoStore.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        userInfoStore.unregister(this);
    }

    @Subscribe
    public void onGetUserInfo(UserInfoStore.GetUserInfoEvent event) {
        if(event.isGetUserInfoSuccessful) {
            userInfo = userInfoStore.getUserInfo();
            txtUserName.setText(userInfo.getNickName());
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
        userInfoStore = UserInfoStore.getInstance();
        //在调度者里注册 用户 数据仓库，若已注册，不会重复注册
        dispatcher.register(userInfoStore);
    }

    //控件的可见与不可见：GONE不可见不占位置，VISIBLE表示可见，INVISIBLE表示不可见但是占用空间
    void initWidget(View view) {
        to_setting = (ConstraintLayout) view.findViewById(R.id.to_setting); //跳转设置的控件
        to_contribution = (ConstraintLayout) view.findViewById(R.id.to_contribution);//跳转到贡献的控件
        to_login = (ConstraintLayout) view.findViewById(R.id.to_login); //跳转至用户登录的控件
        btnCheckin = (Button) view.findViewById(R.id.btn_user_layout_Check_in); //用来完成用户签到
        txtUserName = (TextView) view.findViewById(R.id.user_layout_username); //显示用户名即昵称
        txtAccountnumber = (TextView) view.findViewById(R.id.user_layout_account_number); //显示账号
        imagePhoto = (ImageView) view.findViewById(R.id.user_layout_user_image); //用户头像
        show_lv = (TextView)view.findViewById(R.id.user_layout_show_lv);//展示等级称号
        lv_tag_content = getResources().getStringArray(R.array.check_in_tag);//等级称号的字符串数组

        //跳转到设置界面的监听器
        to_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Fragment3SettingActivity.class);
                startActivity(intent);
                getActivity().finish();
                //需要在finish和startActivity之后进行
                //第一个参数是需要打开的Activity进入时的动画，第二个是需要关闭的Activity离开时的动画
                getActivity().overridePendingTransition(R.anim.anim_slide_from_right, R.anim.anim_slide_from_right);
            }
        });

        //跳转到贡献界面的监听器
        to_contribution.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent = new Intent(getActivity(), Fragment3UploadActivity.class);
                startActivity(intent);
                //getActivity().finish();
                //需要在finish和startActivity之后进行
                //第一个参数是需要打开的Activity进入时的动画，第二个是需要关闭的Activity离开时的动画
                getActivity().overridePendingTransition(R.anim.anim_slide_from_right, R.anim.anim_slide_from_right);

            }
        });

    }

    void userLogin() {
        if (!user.isLoginState()) {
            //未登录状态的控件显示
            btnCheckin.setVisibility(View.INVISIBLE);
            show_lv.setVisibility(View.INVISIBLE);

            txtAccountnumber.setVisibility(View.GONE);
            txtUserName.setText("登录/注册");
            to_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    //getActivity().finish();
                    //需要在finish和startActivity之后进行
                    //第一个参数是需要打开的Activity进入时的动画，第二个是需要关闭的Activity离开时的动画
                    getActivity().overridePendingTransition(R.anim.anim_slide_from_right, R.anim.anim_slide_from_right);

                }
            });
        }
        else {
            actionsCreator.getUserInfo(user.getUserName());
            btnCheckin.setVisibility(View.VISIBLE);
            get_check_in_day();//获取用户签到天数
            get_check_in_stats();//获取是否签到信息
            update_user_lv();//更新用户等级
            update_check_in_stats();//更新签到按钮显示


            show_lv.setVisibility(View.VISIBLE);
            txtAccountnumber.setVisibility(View.VISIBLE);
            txtUserName.setText("昵称");
            txtAccountnumber.setText(user.getUserName());

            String picturePath = "/storage/emulated/0/PictureSelector.temp.jpg";
            File photoFile = new File(picturePath);
            if (photoFile.exists()) {
                imagePhoto.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            }

            //点击头像
            imagePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), UserDataSettingActivity.class);
                    startActivity(intent);
                    //getActivity().finish();
                    //需要在finish和startActivity之后进行
                    //第一个参数是需要打开的Activity进入时的动画，第二个是需要关闭的Activity离开时的动画
                    getActivity().overridePendingTransition(R.anim.anim_slide_from_right, R.anim.anim_slide_from_right);
                }
            });


            //点击签到按钮
            btnCheckin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!is_check_in) {//如果当前为没签到
                        //向服务器发送更新的签到状态
                        is_check_in=true;
                        check_in_day++;
                        Toast toast=Toast.makeText(getActivity(),"签到成功",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        update_user_lv();//更新用户等级
                        update_check_in_stats();
                        send_check_in_day();
                        send_check_in_stats();
                    }
                    else{
                        Toast toast=Toast.makeText(getActivity(),"已签到",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
            });



        }
    }

    void update_user_lv(){

        if((check_in_day/3)>=0&&(check_in_day/3)<=(lv_tag_content.length-1))
        {
            show_lv.setText(lv_tag_content[(check_in_day/3)]);
        }
        else if((check_in_day/3)>(lv_tag_content.length-1))
        {
            show_lv.setText(lv_tag_content[(lv_tag_content.length-1)]);
        }
        else {
            show_lv.setText(lv_tag_content[0]);
        }
    }

    void update_check_in_stats(){
        if(is_check_in)
        {
            btnCheckin.setText("已签到");
        }
        else {
            btnCheckin.setText("签到");
        }
    }
    void get_check_in_stats(){
        //获取今日签到状态-----------易雄宇提供

    }
    void get_check_in_day(){
        //获取连续签到天数函数-----------易雄宇提供

    }
    void send_check_in_stats(){
        //发送今日签到状态-----------客户端提供

    }
    void send_check_in_day(){
        //发送连续签到天数函数-----------客户端提供

    }
}
