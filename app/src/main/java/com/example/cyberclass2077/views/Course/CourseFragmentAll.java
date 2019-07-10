package com.example.cyberclass2077.views.Course;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cyberclass2077.R;
import com.example.cyberclass2077.actions.ActionsCreator;
import com.example.cyberclass2077.adapter.CourseAdapter;
import com.example.cyberclass2077.bean.CourseBean;
import com.example.cyberclass2077.bean.DynamicItem;
import com.example.cyberclass2077.controllers.ToNextActivity;
import com.example.cyberclass2077.dispatcher.Dispatcher;
import com.example.cyberclass2077.stores.FileInfoStore;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class CourseFragmentAll extends Fragment {

    private ListView listView;
    private CourseAdapter courseAdapter;

    private Dispatcher dispatcher;
    private ActionsCreator actionsCreator;
    private FileInfoStore fileInfoStore;
    private static List<CourseBean> courseBeanList = new ArrayList<>();
    private CourseBean courseBean;
    private List<Integer> getNewCourseIDs = new ArrayList<>();

    private SwipeRefreshLayout swipeRefreshLayout;


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
        final View view=inflater.inflate(R.layout.course_list, container, false);
        listView = (ListView) view.findViewById(R.id.id_course_list);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_course_layout);
        initDependencies();

        actionsCreator.getVideos("default","notag");

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //结束后停止刷新
                        if(ToNextActivity.ISLOGIN) {
                            actionsCreator.getVideos("default","notag");
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });

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
    public void onGetVideoList(FileInfoStore.GetVideosEventDefault event){
        if(ToNextActivity.ISLOGIN) {
            if (courseBeanList.size() >0) {
                for(int i = 0; i <  event.video_list.size(); i++) {
                    //通过判断本地首个动态的id大小，如果小于返回的数据id大小，则添加该动态至首行
                    if (courseBeanList.get(0).getCourseID() < event.video_list.get(i).getFileId()) {
                        CourseBean courseBean = new CourseBean();
                        courseBean.setCourseID(event.video_list.get(i).getFileId());//设置视频ID
                        courseBean.setFavorite(event.video_like_list.get(i));   //设置最爱
                        courseBean.setTag(event.video_list.get(i).getTag());    //设置标签
                        courseBean.setUploadTime(event.video_list.get(i).getUploadTime()); //设置上传时间
                        courseBean.setUserNickName(event.video_list.get(i).getUploadUserName());    //设置上传者用户名
                        courseBean.setVideoTitle(event.video_list.get(i).getFileTitle());   //设置上传文件标题
                        courseBean.setVideoURL(event.video_url_list.get(i));    //设置URL
                        courseBean.img_user = event.video_portrait_list.get(i); //设置视频上传者头像
                        courseBeanList.add(0,courseBean);
                        getNewCourseIDs.add(courseBean.getCourseID()); //将新动态的ID加入进去
                    }
                    else {
                        break;
                    }
                }
            }
            else {
                for(int i = 0; i < event.video_list.size(); i++) {
                    CourseBean courseBean = new CourseBean();
                    courseBean.setCourseID(event.video_list.get(i).getFileId());
                    courseBean.setFavorite(event.video_like_list.get(i));
                    courseBean.setTag(event.video_list.get(i).getTag());
                    courseBean.setUploadTime(event.video_list.get(i).getUploadTime());
                    courseBean.setUserNickName(event.video_list.get(i).getUploadUserName());
                    courseBean.setVideoTitle(event.video_list.get(i).getFileTitle());
                    courseBean.setVideoURL(event.video_url_list.get(i));
                    courseBean.img_user = event.video_portrait_list.get(i); //设置视频上传者封面
                    courseBeanList.add(0,courseBean);
                    getNewCourseIDs.add(courseBean.getCourseID()); //将新动态的ID加入进去
                }
            }

            new Handler().postDelayed(new Runnable(){
                public void run() {
                    //execute the task
                    courseAdapter = new CourseAdapter(getActivity(), courseBeanList);
                    listView.setAdapter(courseAdapter);
                    for(int i = 0; i < getNewCourseIDs.size(); i++) {
                        actionsCreator.getVideoPicture(getNewCourseIDs.get(i)); //通过提供动态ID来获取图片内容
                    }
                }
            }, 2000);

            if(event.isGetVideosSuccessful==true)
            {
                Toast.makeText(getActivity(),
                        String.format("已获取在线视频"),
                        Toast.LENGTH_SHORT
                ).show();
            }
        }

    }

    @Subscribe
    public void onGetVideoPictureEvent(FileInfoStore.GetVideoPictureEvent event) {
        if(getNewCourseIDs.size() > 0) {
            for(int i = 0; i < getNewCourseIDs.size(); i++) {
                courseAdapter.addPicture(i, event.fileId, event.bitmap);//传递
            }
        }
        else {
            for (int i = 0; i < courseBeanList.size(); i++) {
                courseAdapter.addPicture(i, event.fileId, event.bitmap);//传递
            }
        }
        if (event.isGetVideoPicSuccessful) {
            Toast.makeText(getActivity(),
                    String.format("已加载封面"),
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

}
