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

    private Dispatcher dispatcher;
    private ActionsCreator actionsCreator;
    private DynamicStore dynamicStore;


    private DynamicRecycleAdapter recycleAdapter; //测试
    private RecyclerView recyclerView; //测试
    private static List<DynamicItem> dynamicItems = new ArrayList<>(); //保存动态
    private List<GetIDandBItmap> getIDandBItmaps = new ArrayList<>();
    private List<Integer> getNewDynamicIDs = new ArrayList<>();  //负责存储新动态的的ID
    private DynamicItem dynamicItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dynamic_recycleview_all_layout, container, false);//测试
        initDependencies();
        //initView(view);//测试
        if(ToNextActivity.ISLOGIN) {
            actionsCreator.getDynamics("square");   //先获取动态内容
            //等待数据加入
            new Handler().postDelayed(new Runnable(){
                public void run() {
                    //execute the task
//                    for(int i = 0; i < dynamicItems.size(); i++) {
//                        Log.d("进来了","");
//                        initView(view);//测试
//                        actionsCreator.getDynamicPicture(dynamicItems.get(i).int_dynamic); //通过提供动态ID来获取图片内容
//                    }
                    initView(view);//测试
                    for(int i = 0; i < getNewDynamicIDs.size(); i++) {
                        actionsCreator.getDynamicPicture(getNewDynamicIDs.get(i)); //通过提供动态ID来获取图片内容
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
    //获取动态
    @Subscribe
    public void onGetDynamics(DynamicStore.GetDynamicsEvent event) {
        if (dynamicItems.size() >0) {
            for(int i = 0; i <  event.dynamicList.size(); i++) {
                //通过判断本地首个动态的id大小，如果小于返回的数据id大小，则添加该动态至首行
                if (dynamicItems.get(0).int_dynamic < event.dynamicList.get(i).getDynamicId()) {
                    dynamicItem = new DynamicItem(event.dynamicList.get(i), event.portraitList.get(i));
                    dynamicItems.add(0,dynamicItem);
                    getNewDynamicIDs.add(dynamicItem.int_dynamic); //将新动态的ID加入进去
                }
                else {
                    break;
                }
            }
        }
        else {
            for(int i = 0;i < event.dynamicList.size();i++) {
                dynamicItem = new DynamicItem(event.dynamicList.get(i), event.portraitList.get(i));
                dynamicItems.add(dynamicItem);
                getNewDynamicIDs.add(dynamicItem.int_dynamic); //将新动态的ID加入进去
            }
        }
        if (event.isGetDynamicsSuccessful) {
            Toast.makeText(getActivity(),
                    String.format("已获取动态"),
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
    //获取动态图片
    @Subscribe
    public void onGetDynamicPictureEvent(DynamicStore.GetDynamicPictureEvent event) {
        //减少数据加载
        if(getNewDynamicIDs.size() > 0) {
            for(int i = 0; i < getNewDynamicIDs.size(); i++) {
                Log.d("if", "" + i);
                recycleAdapter.addPicture(i, event.dynamicId, event.bitmap);//传递
            }
            Toast.makeText(getActivity(),
                    String.format("已加载新图片"),
                    Toast.LENGTH_SHORT
            ).show();
        }
        else {
            for (int i = 0; i < dynamicItems.size(); i++) {
                //getIDandBItmaps.add(new GetIDandBItmap(event.dynamicId, event.bitmap));//获取动态图片
                Log.d("else", "" + i);
                recycleAdapter.addPicture(i, event.dynamicId, event.bitmap);//传递
            }
            Toast.makeText(getActivity(),
                    String.format("已加载旧图片"),
                    Toast.LENGTH_SHORT
            ).show();
        }
        if (event.isGetDynamicPicSuccessful) {
//            Toast.makeText(getActivity(),
//                    String.format("动态图片已加载"),
//                    Toast.LENGTH_SHORT
//            ).show();
        }
    }
}
