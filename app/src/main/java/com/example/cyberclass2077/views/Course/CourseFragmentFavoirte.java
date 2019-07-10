package com.example.cyberclass2077.views.Course;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class CourseFragmentFavoirte extends Fragment {

    List<CourseBean> courseBeanList=new ArrayList<>();
    ListView listView;
    CourseAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.course_list, container, false);
        listView=(ListView)view.findViewById(R.id.id_course_list);
       courseBeanList=new ArrayList<>();
        for(int i=0;i<2;i++)
        {
            CourseBean courseBean=new CourseBean();
            courseBeanList.add(courseBean);
        }

//        View view1=inflater.inflate(R.layout.course_top_menu_layout,container,false);
//        SearchView searchView=view1.findViewById(R.id.course_search);
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
//                adapter=new CourseAdapter(getActivity(),courseBeanList);
//                adapter.notifyDataSetChanged();
//                listView.setAdapter(adapter);
//
//                return false;
//            }
//        });
        adapter=new CourseAdapter(getActivity(),courseBeanList);
        listView.setAdapter(adapter);
        return view;
    }
}
