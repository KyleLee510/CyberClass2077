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
        /*
        listView=(ListView)view.findViewById(R.id.id_course_list);
        courseBeanList=new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            //CourseBean courseBean=new CourseBean();
            //courseBeanList.add(courseBean);
        }

        adapter=new CourseAdapter(getActivity(),courseBeanList);
        listView.setAdapter(adapter);
        */
        return view;
    }
}
