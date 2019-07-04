package com.example.cyberclass2077.connection;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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

public class LoginConnect extends Connect<User>{
    //这个变量用来暂存界面那里发来的数据
    protected User userCache=new User();
    public LoginConnect(){}

    public void sendLoginRequest(User user){
        //存储UI界面发来的User数据
        userCache.setUser(user);
        postData(
                AppConfig.LOGIN,
                requestBodyBuilder("user",user)
        );


    }
    @Override
    public void emitRequestResult(){
        //服务器返回的结果已经转为Json字符串，调用getResponseStr()可以获取
        //下面这个方法可以将Json字符串转换为指定的类，注意不是什么类都行，不然会报错
        //将服务器返回的包含登录结果信息的字符串转为Map对象(就是一种key:value数据结构，类似字典)
        //Map中包含的key就是LoginStateChangeEvent的成员变量
        Map loginResultMap =JSON.parseObject(getResponseStr(),Map.class);
        //获取UserStore的实例
        UserStore store =UserStore.getInstance();
        //安全检测，防止程序崩溃
        if(!(loginResultMap.containsKey("isLoginSuccessful")&&loginResultMap.containsKey("isAlreadyLogin")))
            return;
        //根据返回的结果修改Store的内容
        boolean isLoginSuccessful=(boolean)loginResultMap.get("isLoginSuccessful");
        boolean isAlreadyLogin=(boolean)loginResultMap.get("isAlreadyLogin");
        if(isLoginSuccessful==true){
            userCache.setLoginState(true);
        }
        else {
            userCache.setLoginState(false);
        }
        store.setUser(userCache);


        //设定发生了什么事件,以及初始化这个事件
        store.setStoreChangeEvent(
                store.new LoginStateChangeEvent(
                        isLoginSuccessful,
                        isAlreadyLogin)
        );
        //告诉Store发生了变更事件,Store会将这个事件发送到EventBus中，最后传递给Activity中的事件处理者解决。
        UserStore.getInstance().emitStoreChange();

        //之后在Activity中写对应事件的响应方法

    }

}
