package com.example.cyberclass2077.views.Dynamic;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cyberclass2077.R;
import com.example.cyberclass2077.actions.ActionsCreator;

import com.example.cyberclass2077.adapter.DynamicRecycleAdapter;
import com.example.cyberclass2077.bean.DynamicBean;
import com.example.cyberclass2077.bean.DynamicItem;

import com.example.cyberclass2077.bean.GetIDandBItmap;
import com.example.cyberclass2077.controllers.ToNextActivity;
import com.example.cyberclass2077.dispatcher.Dispatcher;
import com.example.cyberclass2077.model.User;
import com.example.cyberclass2077.model.UserInfo;
import com.example.cyberclass2077.stores.DynamicStore;
import com.example.cyberclass2077.stores.UserInfoStore;
import com.example.cyberclass2077.stores.UserStore;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DynamicAllFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;

    private Dispatcher dispatcher;
    private ActionsCreator actionsCreator;
    private DynamicStore dynamicStore;
    private UserInfoStore userInfoStore;
    private UserStore userStore;
    private UserInfo userInfo;
    private User user;


    private DynamicRecycleAdapter recycleAdapter; //测试
    private RecyclerView recyclerView; //测试
    private List<DynamicItem> dynamicItems = new ArrayList<>(); //测试
    private List<GetIDandBItmap> getIDandBItmaps = new ArrayList<>();
    private DynamicItem dynamicItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dynamic_recycleview_all_layout, container, false);//测试
        initDependencies();
        initView(view);//测试
        if(ToNextActivity.ISLOGIN) {
            actionsCreator.getDynamics("square");   //先获取动态内容
            new Handler().postDelayed(new Runnable(){
                public void run() {
                    //execute the task
                    for(int i = 0; i < dynamicItems.size(); i++) {
                        Log.d("进来了","");
                        actionsCreator.getDynamicPicture(dynamicItems.get(i).int_dynamic); //通过提供动态ID来获取图片内容
                    }
                }
            }, 2000);
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //dispatcher.unregister(userStore);
    }

    @Override
    public void onResume() {
        super.onResume();
        //dynamicStore.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        dynamicStore.unregister(this);
    }

    //更新UI
    void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.id_dynamic_recycle_all_layout);//测试
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        //设置添加或删除item时的动画，这里使用默认动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL)); //添加水平分割线
        recycleAdapter = new DynamicRecycleAdapter(dynamicItems, view.getContext());
        //设置适配器
        recyclerView.setAdapter(recycleAdapter);
    }

    private void initDependencies() {
        //获取调度者单例
        dispatcher = Dispatcher.get();
        //获取动作创建者单例
        actionsCreator = ActionsCreator.get(dispatcher);
        //获取 用户 数据仓库单例
        dynamicStore = DynamicStore.getInstance();
        dynamicStore.register(this);
        //在调度者里注册 用户 数据仓库，若已注册，不会重复注册
        dispatcher.register(dynamicStore);
    }

    @Subscribe
    public void onGetDynamics(DynamicStore.GetDynamicsEvent event) {
        for(int i = 0;i < event.dynamicList.size();i++) {
            dynamicItem = new DynamicItem(event.dynamicList.get(i), event.portraitList.get(i));
            dynamicItems.add(dynamicItem);
            //recycleAdapter.addData(0, dynamicItem); //测试

        }
        if (event.isGetDynamicsSuccessful) {
            Toast.makeText(getActivity(),
                    String.format("已获取动态"),
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    @Subscribe
    public void onGetDynamicPictureEvent(DynamicStore.GetDynamicPictureEvent event) {
        for(int i = 0; i < dynamicItems.size(); i++) {
            Log.d("测试", "" + dynamicItems.size());
            //getIDandBItmaps.add(new GetIDandBItmap(event.dynamicId, event.bitmap));//获取动态图片
            recycleAdapter.addPicture(i, event.dynamicId, event.bitmap);//传递
        }

        if (event.isGetDynamicPicSuccessful) {
            Toast.makeText(getActivity(),
                    String.format("动态图片已加载"),
                    Toast.LENGTH_SHORT
            ).show();
        }
    }


    public void tesetCon() {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //执行耗时操作
                try {
                    Thread.sleep(5000);
                    for(int i = 0; i < dynamicItems.size(); i++) {
                        Log.d("进来了","");
                        actionsCreator.getDynamicPicture(dynamicItems.get(i).int_dynamic); //通过提供动态ID来获取图片内容
                    }
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread() {
            public void run() {
                Looper.prepare();
                new Handler().post(runnable);//在子线程中直接去new 一个handler
                Looper.loop();  //这种情况下，Runnable对象是运行在子线程中的，可以进行联网操作，但是不能更新UI
            }
        }.start();
    }

    public void testUI(View view) {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //执行耗时操作
                try {
                    Thread.sleep(5000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread() {
            public void run() {
                new Handler(Looper.getMainLooper()).post(runnable);//在子线程中直接去new 一个handler
                //这种情况下，Runnable对象是运行在主线程中的，不可以进行联网操作，但是可以更新UI
            }
        }.start();
    }

}
