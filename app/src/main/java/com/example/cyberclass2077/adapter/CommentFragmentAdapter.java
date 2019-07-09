package com.example.cyberclass2077.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.example.cyberclass2077.views.Dynamic.DynamicAllFragment;
import com.example.cyberclass2077.views.Dynamic.DynamicAttentionFragment;
import com.example.cyberclass2077.views.TabFragment;

import java.util.ArrayList;
import java.util.List;

public class CommentFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList=new ArrayList<>();
    private List<String> mTitles=new ArrayList<>();
    public CommentFragmentAdapter(FragmentManager fm)
    {
        super(fm);
        this.fragmentList.add(new DynamicAllFragment());
        this.fragmentList.add(new DynamicAttentionFragment());
        mTitles.add("广场");
        mTitles.add("关注");
    }

    @Override
    public Fragment getItem(int position){
        Bundle bundle = new Bundle();
        bundle.putString("name", mTitles.get(position));
        Fragment fragment = fragmentList.get(position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String plateName = mTitles.get(position);
        if (plateName == null) {
            plateName = "";
        } else if (plateName.length() > 15) {
            plateName = plateName.substring(0, 15) + "...";
        }
        return mTitles.get(position);
    }

    @Override
    public int getCount(){
        return fragmentList.size();
    }

}
