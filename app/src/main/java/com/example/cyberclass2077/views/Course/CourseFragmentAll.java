package com.example.cyberclass2077.views.Course;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.cyberclass2077.R;
import com.example.cyberclass2077.adapter.CourseAdapter;
import com.example.cyberclass2077.bean.CourseBean;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class CourseFragmentAll extends Fragment{

    private SearchView searchView;
    List<CourseBean> courseBeanList=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.course_list, container, false);
        final ListView listView=(ListView)view.findViewById(R.id.id_course_list);

        for(int i=0;i<10;i++)
        {
            CourseBean courseBean=new CourseBean();
            courseBeanList.add(courseBean);
        }



        listView.setAdapter(new CourseAdapter(getActivity(),courseBeanList));
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
    }
}
