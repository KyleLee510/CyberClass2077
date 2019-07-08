package com.example.cyberclass2077.views.Dynamic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

public class DynamicAllFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dynamic_all_layout, container, false);
        ListView listView=(ListView)view.findViewById(R.id.id_dynamic_all_layout);

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
