package com.example.cyberclass2077.views;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cyberclass2077.R;
import com.example.cyberclass2077.adapter.CourseFragmentAdapter;

import java.util.ArrayList;
import java.util.List;


public class Fragment2 extends Fragment{


    private ViewPager pager;
    private CourseFragmentAdapter fragmentAdapter;
    private TabLayout tabLayout;
    private List<String> mTitles;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.social_layout, container, false);

        pager = view.findViewById(R.id.comment_top_page);
        tabLayout = view.findViewById(R.id.comment_top_tab_layout);

        fragmentAdapter = new CourseFragmentAdapter(getChildFragmentManager());

        pager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(pager);//与ViewPage建立关系


        initData();
        fragmentAdapter.setList(mTitles);
        return view;
//        ListView listView=(ListView)view.findViewById(R.id.);
//
//        List<DynamicBean> dynamicBeanList=new ArrayList<>();
//        for(int i=0;i<10;i++)
//        {
//            DynamicBean dynamicBean=new DynamicBean();
//            dynamicBeanList.add(dynamicBean);
//        }
//
//
//        listView.setAdapter(new DynamicAdapter(getActivity(),dynamicBeanList));
//        return view;

    }

    private void initData(){
        mTitles = new ArrayList<>();
        mTitles.add("广场");
        mTitles.add("关注");
    }



}
