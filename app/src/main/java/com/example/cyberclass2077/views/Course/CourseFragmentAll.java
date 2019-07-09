package com.example.cyberclass2077.views.Course;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.cyberclass2077.R;
import com.example.cyberclass2077.adapter.CourseAdapter;
import com.example.cyberclass2077.bean.CourseBean;

import java.util.ArrayList;
import java.util.List;

public class CourseFragmentAll extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.course_list, container, false);
        ListView listView = (ListView) view.findViewById(R.id.id_course_list);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_course_layout);
        List<CourseBean> courseBeanList=new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            CourseBean courseBean=new CourseBean();
            courseBeanList.add(courseBean);
        }
        listView.setAdapter(new CourseAdapter(getActivity(),courseBeanList));

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light);
        /*
        //给swipeRefreshLayout绑定刷新监听
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //设置2秒的时间来执行以下事件
            }
        });
        */
        return view;
    }
}
