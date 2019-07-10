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
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.cyberclass2077.R;
import com.example.cyberclass2077.actions.ActionsCreator;
import com.example.cyberclass2077.adapter.CourseAdapter;
import com.example.cyberclass2077.bean.CourseBean;
import com.example.cyberclass2077.dispatcher.Dispatcher;
import com.example.cyberclass2077.stores.FileInfoStore;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class CourseFragmentAll extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private List<CourseBean> courseBeanList_old=new ArrayList<>();
    private ListView listView;



    private Dispatcher dispatcher;
    private ActionsCreator actionsCreator;
    private FileInfoStore fileInfoStore;
    private List<CourseBean> courseBeanList = new ArrayList<>();;

    @Override
    public void onResume() {
        super.onResume();
        fileInfoStore.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        fileInfoStore.unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.course_list, container, false);
        listView = (ListView) view.findViewById(R.id.id_course_list);


        initDependencies();


//        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_course_layout);
//        for(int i=0;i<2;i++)
//        {
//            CourseBean courseBean_old=new CourseBean();
//            courseBeanList_old.add(courseBean_old);
//        }
//        listView.setAdapter(new CourseAdapter(getActivity(),courseBeanList_old));

//        SearchView searchView=view.findViewById(R.id.course_search);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
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
        Log.e("get_video_test","actionCreator前");
        actionsCreator.getVideos("default","notag");
        return view;
    }

    private void initDependencies() {
        //获取调度者单例
        dispatcher = Dispatcher.get();
        //获取动作创建者单例
        actionsCreator = ActionsCreator.get(dispatcher);
        //获取 文件 数据仓库
        fileInfoStore = FileInfoStore.getInstance();
        //在调度者里注册 文件 数据仓库
        dispatcher.register(fileInfoStore);
        // fileInfoStore.register(this);
    }




    @Subscribe
    public void getVideoList(FileInfoStore.GetVideosEvent event){
        Log.e("get_video_test","there");
        Log.e("get_video_test",String.valueOf(event.isGetVideosSuccessful));
        if(event.isGetVideosSuccessful==true)
        {
            for(int i = 0;i < event.video_list.size();i++) {
                CourseBean courseBean = new CourseBean();
                courseBean.setCourseID(event.video_list.get(i).getFileId());
                Log.e("get_video_test",String.valueOf(courseBean.getCourseID()));
                courseBean.setFavorite(event.video_like_list.get(i));
                Log.e("get_video_test",String.valueOf(courseBean.getfavrot()));
                courseBean.setTag(event.video_list.get(i).getTag());
                Log.e("get_video_test",courseBean.getTag());
                courseBean.setUploadTime(event.video_list.get(i).getUploadTime());
                Log.e("get_video_test",courseBean.getUploadTime());
                courseBean.setUserNickName(event.video_list.get(i).getUploadUserName());
                Log.e("get_video_test",courseBean.getUserNickName());
                courseBean.setVideoTitle(event.video_list.get(i).getFileTitle());
                Log.e("get_video_test",courseBean.getVideoTitle());
                courseBean.setVideoURL(event.video_url_list.get(i));
                Log.e("get_video_test",courseBean.getVideoURL());

                courseBeanList.add(courseBean);
            }
            Log.e(TAG, "getVideoList: "+event.video_list.size() );
            CourseAdapter courseAdapter=new CourseAdapter(getActivity(),courseBeanList);
            listView.setAdapter(courseAdapter);

        }
        else{

        }
    }

}
