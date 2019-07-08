package com.example.cyberclass2077.adapter;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.cyberclass2077.views.Course.CourseFragmentAll;
import com.example.cyberclass2077.views.Course.CourseFragmentFavoirte;

import java.util.ArrayList;
import java.util.List;

public class CourseFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList=new ArrayList<>();
    private List<String>mTitles=new ArrayList<>();
    public CourseFragmentAdapter(FragmentManager fm)
    {
        super(fm);
        this.fragmentList.add(new CourseFragmentFavoirte());
        this.fragmentList.add(new CourseFragmentAll());
        mTitles.add("收藏");
        mTitles.add("推荐");
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("name", mTitles.get(position));
        Fragment fragment = fragmentList.get(position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
