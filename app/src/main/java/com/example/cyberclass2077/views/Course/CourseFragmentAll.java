package com.example.cyberclass2077.views.Course;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.cyberclass2077.R;
import com.example.cyberclass2077.adapter.CourseAdapter;
import com.example.cyberclass2077.bean.CourseBean;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class CourseFragmentAll extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private List<CourseBean> courseBeanList=new ArrayList<>();
    private ListView listView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.course_list, container, false);
        listView = (ListView) view.findViewById(R.id.id_course_list);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_course_layout);
        for(int i=0;i<2;i++)
        {
            CourseBean courseBean=new CourseBean();
            courseBeanList.add(courseBean);
        }
        listView.setAdapter(new CourseAdapter(getActivity(),courseBeanList));

        SearchView searchView=view.findViewById(R.id.course_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
//        View view1=inflater.inflate(R.layout.course_top_menu_layout,container,false);
//        final SearchView searchView=view1.findViewById(R.id.course_search);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                Log.e(TAG, "onQueryTextSubmit: " );
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                Log.e(TAG, "onQueryTextChange: " );
//                courseBeanList = new ArrayList<>();
//                for(int i=0;i<18;i++) {
//
//                    courseBeanList.add(new CourseBean());
//                }
//                listView.setAdapter(new CourseAdapter(getActivity(),courseBeanList));
//                return false;
//            }
//        });
//        View view1=inflater.inflate(R.layout.course_top_menu_layout,container,false);
//        SearchView searchView=view1.findViewById(R.id.course_search);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                Log.e(TAG, "onQueryTextSubmit: " );
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                Log.e(TAG, "onQueryTextChange: " );
//                return false;
//            }
//        });

//        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light, android.R.color.holo_orange_light);
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
