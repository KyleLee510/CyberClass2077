package com.example.cyberclass2077.views.Dynamic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cyberclass2077.R;

public class DynamicMyFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dynamic_attention_layout, container, false);
        /*
        ListView listView=(ListView)view.findViewById(R.id.id_dynamic_attention);

        List<DynamicBean> dynamicBeanList=new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            DynamicBean dynamicBean=new DynamicBean();
            dynamicBeanList.add(dynamicBean);
        }
        */
        return view;
    }


}
