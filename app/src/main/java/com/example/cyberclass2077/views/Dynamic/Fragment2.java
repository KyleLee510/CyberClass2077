package com.example.cyberclass2077.views.Dynamic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cyberclass2077.R;
import com.example.cyberclass2077.actions.ActionsCreator;
import com.example.cyberclass2077.adapter.CommentFragmentAdapter;
import com.example.cyberclass2077.adapter.CourseFragmentAdapter;
import com.example.cyberclass2077.adapter.DynamicAdapter;
import com.example.cyberclass2077.bean.DynamicPublishBean;
import com.example.cyberclass2077.controllers.ToNextActivity;
import com.example.cyberclass2077.dispatcher.Dispatcher;
import com.example.cyberclass2077.model.User;
import com.example.cyberclass2077.model.UserInfo;
import com.example.cyberclass2077.stores.DynamicStore;
import com.example.cyberclass2077.stores.UserInfoStore;
import com.example.cyberclass2077.stores.UserStore;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;


public class Fragment2 extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.social_layout, container, false);
        ViewPager pager = view.findViewById(R.id.comment_top_page);

        CommentFragmentAdapter commentFragmentAdapter=new CommentFragmentAdapter(getChildFragmentManager());
        pager.setAdapter(commentFragmentAdapter);


        TabLayout tabLayout = view.findViewById(R.id.comment_top_tab_layout);

        tabLayout.setupWithViewPager(pager);//与ViewPage建立关系

        //添加发布动态跳转事件
        final ImageButton btn_publish=view.findViewById(R.id.id_btn_publish);
        btn_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), DynamicPublish.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
