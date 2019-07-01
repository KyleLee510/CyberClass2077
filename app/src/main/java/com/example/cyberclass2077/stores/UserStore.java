package com.example.cyberclass2077.stores;

import com.alibaba.fastjson.JSON;
import com.example.cyberclass2077.actions.Action;
import com.example.cyberclass2077.actions.LoginAction;
import com.example.cyberclass2077.connection.LoginConnect;
import com.example.cyberclass2077.model.User;
import com.squareup.otto.Subscribe;

public class UserStore extends Store{
    private static UserStore instance;
    private LoginConnect connect;

    private User user=new User();

    private UserStore(){
        super();
        connect=new LoginConnect();

    }
    public static UserStore getInstance(){
        if(instance==null){
            instance=new UserStore();
        }
        return instance;
    }
    public User getUser(){return user.getUser();}
    public void setUser(User user){
        this.user=user;
        emitStoreChange();
    }
    @Override
    @Subscribe
    public void onAction(Action action){
        switch (action.getType()){
            case LoginAction
                    .ACTION_LOGIN:
                //在这里调用业务逻辑层网络连接工具类的接口
                //发送给服务器的user对象只包含用户名和密码
                //从服务器返回的user对象包含用户名密码,用户ID以及登录状态（布尔）
                //主线程到这里终止，OKHTTP发出异步请求，得到结果后在回调里调用handler回到主线程，通知Store

                connect.sendLoginRequest((User)action.getData());

                //emitStoreChange();

            break;
            default:

        }

    }
    @Override
    public StoreChangeEvent changeEvent(){return new StoreChangeEvent();}

}
