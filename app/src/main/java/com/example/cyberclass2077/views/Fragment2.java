package com.example.cyberclass2077.views;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.cyberclass2077.R;
import com.example.cyberclass2077.adapter.DynamicAdapter;
import com.example.cyberclass2077.bean.DynamicBean;

import java.util.ArrayList;
import java.util.List;


public class Fragment2 extends Fragment{




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.social_layout, container, false);
        ListView listView=(ListView)view.findViewById(R.id.id_dynamic_lv);

        List<DynamicBean> dynamicBeanList=new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            DynamicBean dynamicBean=new DynamicBean();
            dynamicBeanList.add(dynamicBean);
        }


        listView.setAdapter(new DynamicAdapter(getActivity(),dynamicBeanList));
        return view;
    }


}
