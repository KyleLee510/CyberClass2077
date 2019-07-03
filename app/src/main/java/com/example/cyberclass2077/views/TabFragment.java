package com.example.cyberclass2077.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cyberclass2077.R;

public class TabFragment extends Fragment {
    private TextView titleTv;

    private String mTitle;

    public TabFragment(){
        mTitle = "404";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        mTitle = bundle.getString("name");
        if (mTitle == null) {
            mTitle = "参数非法";
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View view=inflater.inflate(R.layout.course_layout,container,false);
        titleTv=view.findViewById(R.id.tv_title);
        titleTv.setText(mTitle);
        return view;
    }
}
