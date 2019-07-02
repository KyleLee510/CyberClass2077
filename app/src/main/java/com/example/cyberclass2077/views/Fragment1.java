package com.example.cyberclass2077.views;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cyberclass2077.R;

import java.util.ArrayList;
import java.util.List;


public class Fragment1 extends Fragment {

    private ViewPager pager;
    private CourseFragmentAdapter fragmentAdapter;
    private List<TabFragment> fragmentList;
    private TabLayout tabLayout;
    private List<String> mTitles;
    private String [] title={"收藏","推荐"};


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initData();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View view=inflater.inflate(R.layout.course_top_menu_layout, container,false);
        pager = view.findViewById(R.id.page);
        tabLayout = view.findViewById(R.id.tab_layout);

        fragmentAdapter = new CourseFragmentAdapter(getChildFragmentManager());

        //fragmentAdapter=new CourseFragMentAdapter(getActivity().getSupportFragmentManager(),fragmentList,mTitles);
        pager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(pager);//与ViewPage建立关系

        initData();
        fragmentAdapter.setList(mTitles);
        return view;
    }


//    @Override
//    public void onActivityCreated(Bundle savedInstanceState){
//
//        super.onActivityCreated(savedInstanceState);
//
//        fragmentList = new ArrayList<>();
//        mTitles = new ArrayList<>();
//        for(int i = 0; i < title.length; i++){
//            mTitles.add(title[i]);
//            fragmentList.add(new TabFragment()); //添加新的Tab
//        }
//
//        fragmentAdapter = new CourseFragMentAdapter(getChildFragmentManager());
//
//        //fragmentAdapter=new CourseFragMentAdapter(getActivity().getSupportFragmentManager(),fragmentList,mTitles);
//        pager.setAdapter(fragmentAdapter);
//        tabLayout.setupWithViewPager(pager);//与ViewPage建立关系
//
//    }


    private void initData() {
        //fragmentList = new ArrayList<>();
        mTitles = new ArrayList<>();
        mTitles.add("收藏");
        mTitles.add("推荐");
    }

}
