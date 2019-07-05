package com.example.cyberclass2077.stores;

import com.alibaba.fastjson.JSON;
import com.example.cyberclass2077.actions.Action;
import com.example.cyberclass2077.actions.LoginAction;
import com.example.cyberclass2077.actions.LogoutAction;
import com.example.cyberclass2077.actions.SignupAction;
import com.example.cyberclass2077.actions.UpdatePasswordAction;
import com.example.cyberclass2077.actions.UserNameCheckAction;
import com.example.cyberclass2077.connection.Connect;
import com.example.cyberclass2077.connection.LoginConnect;
import com.example.cyberclass2077.connection.LogoutConnect;
import com.example.cyberclass2077.connection.SignupConnect;
import com.example.cyberclass2077.connection.UpdatePasswordConnect;
import com.example.cyberclass2077.connection.UserNameCheckConnect;
import com.example.cyberclass2077.model.User;
import com.squareup.otto.Subscribe;

public class UserStore extends Store{
    private static UserStore instance;
    private Connect connect;
    private User user=new User();

    private UserStore(){
        super();


    }
    public static UserStore getInstance(){
        if(instance==null){
            instance=new UserStore();
        }
        return instance;
    }
    public User getUser(){
        return user.getUser();
    }
    public void setUser(User user){
        this.user=user;
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
                //得到请求结果后的操作在LoginConnect里的emitRequestResult()修改,Connection包的其他内容不需要改
                connect=null;
                connect=new LoginConnect();
                ((LoginConnect)connect).sendLoginRequest((User)action.getData());
                break;

                //不能在这里调用这个函数，因为OKHTTP用的异步方法，在主线程中，还没得到数据。
                //emitStoreChange();
            case SignupAction
                        .ACTION_SIGNUP:
                connect=null;
                connect=new SignupConnect();
                ((SignupConnect)connect).sendSignupRequest((User)action.getData());
                break;
            case UserNameCheckAction
                        .ACTION_USERNAMECHECK:
                connect=null;
                connect=new UserNameCheckConnect();
                ((UserNameCheckConnect)connect).sendUserNameCheckRequest((String)action.getData());
                break;


            case LogoutAction
                        .ACTION_LOGOUT:
                connect=null;
                connect=new LogoutConnect();
                ((LogoutConnect)connect).sendLogoutRequest((String)action.getData());
                break;
            case UpdatePasswordAction
                    .ACTION_UPDATE_PASSWORD:
                connect=null;
                connect=new UpdatePasswordConnect();
                ((UpdatePasswordConnect)connect).sendUpdatePasswordRequest((User)action.getData());
                break;
            default:

        }

    }
    //登录事件
    public class LoginStateChangeEvent extends StoreChangeEvent{
        public  boolean isLoginSuccessful=false;
        public boolean isAlreadyLogin=false;
        public LoginStateChangeEvent(boolean isLoginSuccessful,boolean isAlreadyLogin){
            this.isLoginSuccessful=isLoginSuccessful;
            this.isAlreadyLogin=isAlreadyLogin;
        }
    }
    //注册事件
    public class SignupEvent extends StoreChangeEvent{
        public boolean isSignupSuccessful=false;
        public SignupEvent(boolean isSignupSuccessful){
            this.isSignupSuccessful=isSignupSuccessful;
        }
    }
    //用户名查重事件
    public class NameCheckEvent extends StoreChangeEvent{
        public boolean isNameExist=false;
        public NameCheckEvent(boolean isNameExist){
            this.isNameExist=isNameExist;
        }
    }
    //用户登出事件
    public class LogoutEvent extends StoreChangeEvent{
        public boolean isLogoutSuccessful=false;
        public LogoutEvent(boolean isLogoutSuccessful){this.isLogoutSuccessful=isLogoutSuccessful;}
    }
    //更新（修改）密码事件
    public class UpdatePasswordEvent extends StoreChangeEvent{
        public boolean isUpdatePasswordSuccessful=false;
        public UpdatePasswordEvent(boolean isUpdatePasswordSuccessful){this.isUpdatePasswordSuccessful=isUpdatePasswordSuccessful;}
    }
}
