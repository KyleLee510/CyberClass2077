package com.example.cyberclass2077.connection;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.cyberclass2077.actions.SignupAction;
import com.example.cyberclass2077.config.AppConfig;
import com.example.cyberclass2077.model.User;
import com.example.cyberclass2077.stores.UserStore;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Map;

public class SignupConnect extends Connect<User>{
    //这个变量用来暂存界面那里发来的数据
    protected User userCache=new User();
    public SignupConnect(){}
    public void sendSignupRequest(User user){
        //存储UI界面发来的User数据
        userCache.setUser(user);
        postData(
                AppConfig.SIGNUP,
                requestBodyBuilder("user",user)
        );
    }
    @Override
    public void emitRequestResult(){
        Map signupResultMap =JSON.parseObject(getResponseStr(),Map.class);
        UserStore store =UserStore.getInstance();
        if(!signupResultMap.containsKey("isSignupSuccessful"))
            return;
        boolean isSignupSuccessful=(boolean)signupResultMap.get("isSignupSuccessful");
        if(isSignupSuccessful==true){
            //注册成功，向UserStore写入这个注册的用户的用户名和密码
            //但是设定登录状态为false，用户还需要回去再登录一次
            userCache.setLoginState(false);
            store.setUser(userCache);
        }
        else{
            //注册失败，这里暂时什么都不做
        }
        //设定发生了什么事件,以及初始化这个事件
        store.setStoreChangeEvent(
                 store.new SignupEvent(
                         isSignupSuccessful
                 )

        );
        //告诉Store发生了变更事件,Store会将这个事件发送到EventBus中，最后传递给Activity中的事件处理者解决。
        UserStore.getInstance().emitStoreChange();


    }
}
