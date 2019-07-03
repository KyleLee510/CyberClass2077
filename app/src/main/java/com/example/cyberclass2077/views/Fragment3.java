package com.example.cyberclass2077.views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cyberclass2077.R;


public class Fragment3 extends Fragment {

    ConstraintLayout to_setting;
    ConstraintLayout to_contribution;
    ConstraintLayout to_login;
    Boolean isLogin = false; //默认false未登录状态
    Button btnCheckin;
    TextView txtUserName;
    TextView txtAccountnumber;
    ImageView imagePhoto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_layout, container, false);

        initWidget(view);   //初始化控件
        userLogin();   //用户是否登录的界面设置

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
                getActivity().finish();
                //需要在finish和startActivity之后进行
                //第一个参数是需要打开的Activity进入时的动画，第二个是需要关闭的Activity离开时的动画
                getActivity().overridePendingTransition(R.anim.anim_slide_from_right, R.anim.anim_slide_from_right);
            }
        });

        // Inflate the layout for this fragment
        return view;
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
    }

    void userLogin() {
        if (!isLogin) {
            //未登录状态的控件显示
            btnCheckin.setVisibility(View.GONE);
            txtAccountnumber.setVisibility(View.GONE);
            txtUserName.setText("登录/注册");
            to_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                    //需要在finish和startActivity之后进行
                    //第一个参数是需要打开的Activity进入时的动画，第二个是需要关闭的Activity离开时的动画
                    getActivity().overridePendingTransition(R.anim.anim_slide_from_right, R.anim.anim_slide_from_right);

                }
            });
        }
        else {
            btnCheckin.setVisibility(View.VISIBLE);
            txtAccountnumber.setVisibility(View.VISIBLE);
            txtUserName.setText("昵称");
            //点击头像
            imagePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), UserDataSettingActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                    //需要在finish和startActivity之后进行
                    //第一个参数是需要打开的Activity进入时的动画，第二个是需要关闭的Activity离开时的动画
                    getActivity().overridePendingTransition(R.anim.anim_slide_from_right, R.anim.anim_slide_from_right);
                }
            });
        }
    }

}
