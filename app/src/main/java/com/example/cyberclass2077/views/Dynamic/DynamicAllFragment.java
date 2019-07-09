package com.example.cyberclass2077.views.Dynamic;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.cyberclass2077.adapter.DynamicAdapter;
import com.example.cyberclass2077.adapter.DynamicRecycleAdapter;
import com.example.cyberclass2077.bean.DynamicBean;
import com.example.cyberclass2077.bean.DynamicItem;
import com.example.cyberclass2077.bean.DynamicPublishBean;
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

public class DynamicAllFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;

    private Dispatcher dispatcher;
    private ActionsCreator actionsCreator;
    private DynamicStore dynamicStore;
    private UserInfoStore userInfoStore;
    private UserStore userStore;
    private UserInfo userInfo;
    private User user;

    //private DynamicAdapter adapter;

    private DynamicRecycleAdapter recycleAdapter; //测试
    private RecyclerView recyclerView; //测试
    private List<DynamicItem> dynamicItems = new ArrayList<>(); //测试
    private DynamicItem dynamicItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View view=inflater.inflate(R.layout.dynamic_all_layout, container, false);
        View view=inflater.inflate(R.layout.dynamic_recycleview_all_layout, container, false);//测试
        initDependencies();
        initView(view);//测试
        //swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_square_layout);
        if(ToNextActivity.ISLOGIN) {
            actionsCreator.getDynamics("square");
        }

        /*
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light);

        //给swipeRefreshLayout绑定刷新监听
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //设置2秒的时间来执行以下事件
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        actionsCreator.getDynamics("square");
                        //data.add(0, "刷新后新增的item");
                        //dynamicAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        */


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
            //dynamicItems.add(dynamicItem);
            recycleAdapter.addData(0, dynamicItem); //测试

        }
        if (event.isGetDynamicsSuccessful) {
            Toast.makeText(getActivity(),
                    String.format("已获取动态"),
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

}
