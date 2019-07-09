package com.example.cyberclass2077.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cyberclass2077.R;
import com.example.cyberclass2077.actions.ActionsCreator;
import com.example.cyberclass2077.controllers.ToNextActivity;
import com.example.cyberclass2077.dispatcher.Dispatcher;
import com.example.cyberclass2077.model.User;
import com.example.cyberclass2077.model.UserInfo;
import com.example.cyberclass2077.pictureselector.Constant;
import com.example.cyberclass2077.pictureselector.ImageUtils;
import com.example.cyberclass2077.stores.UserInfoStore;
import com.example.cyberclass2077.stores.UserStore;
import com.squareup.otto.Subscribe;

import java.io.File;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Fragment3 extends Fragment {

    ConstraintLayout to_setting;
    ConstraintLayout to_contribution;
    ConstraintLayout to_login;
    ConstraintLayout to_download;
    ConstraintLayout to_collection;
    ConstraintLayout to_followee;

    Button btn_Checkin;
    TextView txtUserName;
    TextView txtAccountnumber;
    ImageView imagePhoto;
    TextView txt_show_lv;


    private int mYear;
    private int mMonth;
    private int mDay;
    private Calendar cal;//显示当前日期




    //在这里声明其他引用变量
    private String[] lv_tag_content;//签到的等级tag
    private int check_in_day = 0;//连续签到的天数
    boolean is_check_in=false;//今日签到状态
    String lastCheckin_date = "";//上次签到日期


    private Dispatcher dispatcher;
    private ActionsCreator actionsCreator;
    private UserInfoStore userInfoStore;
    private UserInfo userInfo;

    private UserStore userStore;
    private User user;


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
    //得到用户信息
    @Subscribe
    public void onGetUserInfo(UserInfoStore.GetUserInfoEvent event) {

        Log.e("zifuchuan","进入is_check_in前");
        if(event.isGetUserInfoSuccessful) {
            userInfo = userInfoStore.getUserInfo();
            txtUserName.setText(userInfo.getNickName());
            check_in_day = userInfo.getCheckinTotalDays(); //获取总签到天数
            is_check_in(); //进行调用检测
            update_user_lv();
        }
    }

    //用户头像下载
    @Subscribe
    public void onGetPortraitt(UserInfoStore.GetPortraitEvent event) {
        Bitmap bitmap = event.portrait;
        ImageUtils.saveBitmap(bitmap, Constant.USERPHOTO_PATH + "/" + user.getUserName());
        if(event.isGetPortraitSuccessful) {
            Toast.makeText(getActivity(),
                    String.format("下载图像成功"),
                    Toast.LENGTH_SHORT
            ).show();
            imagePhoto.setImageBitmap(bitmap);
        }
    }

    //用户信息更新
    @Subscribe
    public void onUpdateUserInfo(UserInfoStore.UpdateUserInfoEvent event) {
        if(event.isUpdateUserInfoSuccessful) {
            Toast.makeText(getActivity(),
                    String.format("已更新签到信息"),
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
        userInfoStore = UserInfoStore.getInstance();
        //在调度者里注册 用户 数据仓库，若已注册，不会重复注册
        dispatcher.register(userInfoStore);
    }

    //控件的可见与不可见：GONE不可见不占位置，VISIBLE表示可见，INVISIBLE表示不可见但是占用空间
    void initWidget(View view) {
        to_setting = (ConstraintLayout) view.findViewById(R.id.to_setting); //跳转设置的控件
        to_contribution = (ConstraintLayout) view.findViewById(R.id.to_contribution);//跳转到贡献的控件
        to_login = (ConstraintLayout) view.findViewById(R.id.to_login); //跳转至用户登录的控件
        to_download = (ConstraintLayout) view.findViewById(R.id.to_download); // 跳转至用户下载界面的控件
        to_collection = (ConstraintLayout) view.findViewById(R.id.to_collection);//跳转到收藏界面的控件
        to_followee = (ConstraintLayout) view.findViewById(R.id.to_attention);//跳转到关注界面的控件
        btn_Checkin = (Button) view.findViewById(R.id.btn_user_layout_Check_in); //用来完成用户签到
        txtUserName = (TextView) view.findViewById(R.id.user_layout_username); //显示用户名即昵称
        txtAccountnumber = (TextView) view.findViewById(R.id.user_layout_account_number); //显示账号
        imagePhoto = (ImageView) view.findViewById(R.id.user_layout_user_image); //用户头像
        txt_show_lv = (TextView)view.findViewById(R.id.user_layout_show_lv);//展示等级称号
        lv_tag_content = getResources().getStringArray(R.array.check_in_tag);//等级称号的字符串数组
        cal = Calendar.getInstance();//初始化时间


        //跳转到设置界面的监听器
        to_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToNextActivity.to_NextActivity(getActivity(), Fragment3SettingActivity.class);
            }
        });

        //跳转到贡献界面的监听器
        to_contribution.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(user.isLoginState()) {   //登录才可以使用
                    ToNextActivity.to_NextActivity(getActivity(), Fragment3UploadActivity.class);
                }
                else {      //请登录
                    ToNextActivity.to_NextActivity(getActivity(), LoginActivity.class);
                }
            }
        });


        //跳转到收藏界面的监听器
        to_collection.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), Fragment3CollectionActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_slide_from_right, R.anim.anim_slide_from_right);

            }
        });

        //跳转到下载界面的监听器
        to_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.isLoginState()) {
                    ToNextActivity.to_NextActivity(getActivity(), Fragment3DownloadActivity.class);
                }
                else {
                    ToNextActivity.to_NextActivity(getActivity(), LoginActivity.class);
                }
            }
        });


        //跳转到关注界面的监听器
        to_followee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.isLoginState()) {
                    ToNextActivity.to_NextActivity(getActivity(), Fragment3FolloweeActivity.class);
                }
                else {
                    ToNextActivity.to_NextActivity(getActivity(), LoginActivity.class);
                }
            }
        });


    }

    void userLogin() {
        if (!user.isLoginState()) {
            //未登录状态的控件显示
            btn_Checkin.setVisibility(View.INVISIBLE);
            txt_show_lv.setVisibility(View.INVISIBLE);

            txtAccountnumber.setVisibility(View.GONE);
            txtUserName.setText("登录/注册");
            to_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToNextActivity.to_NextActivity(getActivity(), LoginActivity.class);
                }
            });
        }
        else {
            actionsCreator.getUserInfo(user.getUserName());
            btn_Checkin.setVisibility(View.VISIBLE);


            txt_show_lv.setVisibility(View.VISIBLE);
            txtAccountnumber.setVisibility(View.VISIBLE);
            txtUserName.setText("昵称");
            txtAccountnumber.setText(user.getUserName());

            //若本地有缓冲则设置头像
            String picturePath = Constant.USERPHOTO_PATH + "/" + user.getUserName() + ".jpg";
            File photoFile = new File(picturePath);
            if (photoFile.exists()) {
                imagePhoto.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            }
            //没有则去设置
            else {
                actionsCreator.getPortrait(user.getUserName());
            }

            //点击头像
            imagePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToNextActivity.to_NextActivity(getActivity(), UserDataSettingActivity.class);
                }
            });

            //未签到的情况下可用
            //点击签到按钮
            btn_Checkin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(is_check_in) {
                        return;
                    }
                    Log.d("姓名",userInfo.getUserName());
                    userInfo.setLastCheckinDate(getToadyDate()); //将今日签到日期传回服务端
                    userInfo.setCheckinTotalDays(check_in_day +1);

                    actionsCreator.updateUserInfo(userInfo); //签到更新

                    btn_Checkin.setText("已签到"); //更新用户已签到
                    is_check_in = true;
                    update_user_lv();//更新用户等级
                }
            });
        }
    }

    void update_user_lv(){

        if((check_in_day/3)>=0&&(check_in_day/3)<=(lv_tag_content.length-1))
        {
            txt_show_lv.setText(lv_tag_content[(check_in_day/3)]);
        }
        else if((check_in_day/3)>(lv_tag_content.length-1))
        {
            txt_show_lv.setText(lv_tag_content[(lv_tag_content.length-1)]);
        }
        else {
            txt_show_lv.setText(lv_tag_content[0]);
        }
    }


    public boolean is_check_in() {
        String lastCheckinDate = "";
        if(userInfo.getLastCheckinDate() != null) {
            lastCheckinDate = userInfo.getLastCheckinDate();//2019-01-07
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dt1 = sdf.parse(lastCheckinDate,new ParsePosition(0));
            Date dt2 = sdf.parse(getToadyDate(),new ParsePosition(0));
            if(dt2.compareTo(dt1) == 0) {
                btn_Checkin.setText("已签到");
                is_check_in = true;
            }
            else {
                btn_Checkin.setText("未签到");
                is_check_in = false;
            }
        }
        return is_check_in;
    }


    String getToadyDate() {
        mYear = cal.get(Calendar.YEAR);
        String sYear = "" + mYear;
        mMonth = cal.get(Calendar.MONTH) + 1;
        String sMonth = checkLessTen(mMonth);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        String sDay = checkLessTen(mDay);
        String sToday = sYear + "-" + sMonth + "-" + sDay;
        return sToday;
    }

    String checkLessTen(int num) {
        String sNum = "";
        if(num < 10) {
            sNum = "0" + num;
        }
        else {
            sNum = "" + num;
        }
        return sNum;
    }
}
