package com.example.cyberclass2077.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CourseFragmentAdapter extends FragmentPagerAdapter {

    private List<String> mTitle; //导航的标题

    private List<TabFragment> mFragments;


    public CourseFragmentAdapter(FragmentManager fm, List<TabFragment> fragments, List<String>title) {
        super(fm);
        // TODO Auto-generated constructor stub
        mFragments = fragments;
        mTitle = title;
    }

    public CourseFragmentAdapter(FragmentManager fm) {
        super(fm);
        this.mTitle = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int arg0) {
        Bundle bundle = new Bundle();
        bundle.putString("name", mTitle.get(arg0));
        TabFragment fragment = new TabFragment();
        fragment.setArguments(bundle);

        // TODO Auto-generated method stub
        //return mFragments.get(arg0);
        return fragment;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mTitle.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String plateName = mTitle.get(position);
        if (plateName == null) {
            plateName = "";
        } else if (plateName.length() > 15) {
            plateName = plateName.substring(0, 15) + "...";
        }
        return mTitle.get(position);
    }

    public void setList(List<String> datas) {
        this.mTitle.clear();
        this.mTitle.addAll(datas);
        notifyDataSetChanged();
    }
}
