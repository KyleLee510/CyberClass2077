package com.example.cyberclass2077.views.Dynamic;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cyberclass2077.R;
import com.example.cyberclass2077.actions.ActionsCreator;
import com.example.cyberclass2077.adapter.DynamicAdapter;
import com.example.cyberclass2077.bean.DynamicBean;
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

    private ListView listView;

    private Dispatcher dispatcher;
    private ActionsCreator actionsCreator;
    private DynamicStore dynamicStore;
    private UserInfoStore userInfoStore;
    private UserStore userStore;
    private UserInfo userInfo;
    private User user;

    private List<DynamicPublishBean> dynamicPublishBeanList = new ArrayList<>();
    private List<Bitmap> portraitList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dynamic_all_layout, container, false);
        initDependencies();
        Log.e("asdf","onGetDynamics1");
        listView = (ListView)view.findViewById(R.id.id_dynamic_all_layout);
        if(ToNextActivity.ISLOGIN) {
            actionsCreator.getDynamics("square");
        }
        return view;
    }

    private void initDependencies() {
        //获取调度者单例
        dispatcher = Dispatcher.get();
        //获取动作创建者单例
        actionsCreator = ActionsCreator.get(dispatcher);
        //获取 用户 数据仓库单例
        dynamicStore = DynamicStore.getInstance();
        //在调度者里注册 用户 数据仓库，若已注册，不会重复注册
        dispatcher.register(dynamicStore);
    }

    @Subscribe
    public void onGetDynamics(DynamicStore.GetDynamicsEvent event) {
        String.format("ssssss");
        for(int i = 0;i < event.dynamicList.size();i++) {
            dynamicPublishBeanList.add(event.dynamicList.get(i));
            Log.e("asdf","onGetDynamics");
            portraitList.add(event.portraitList.get(i));
        }
        listView.setAdapter(new DynamicAdapter(getActivity(), dynamicPublishBeanList, portraitList));
        if (event.isGetDynamicsSuccessful) {
            Toast.makeText(getActivity(),
                    String.format("已获取动态"),
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

}
