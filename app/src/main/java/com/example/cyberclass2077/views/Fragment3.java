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

import com.example.cyberclass2077.R;


public class Fragment3 extends Fragment {

    ConstraintLayout to_setting;
    ConstraintLayout to_contribution;
    ConstraintLayout to_login;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_layout, container, false);


        //跳转到设置界面
        to_setting = (ConstraintLayout) view.findViewById(R.id.to_setting);
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

        //跳转到登录界面
        to_login = (ConstraintLayout) view.findViewById(R.id.to_login);
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

        //跳转到贡献界面
        to_contribution = (ConstraintLayout) view.findViewById(R.id.to_contribution);
        to_contribution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

}
