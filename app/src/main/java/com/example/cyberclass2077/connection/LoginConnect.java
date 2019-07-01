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

public class LoginConnect extends Connect<User>{
    public LoginConnect(){}
    public void sendLoginRequest(User user){
        postData(
                AppConfig.LOGIN,
                requestBodyBuilder("user",user)
        );


    }

    void emitRequestResult(){
        User userEntity=JSON.parseObject(getResponseStr(),User.class);
        //根据返回的结果修改了Store的内容
        UserStore.getInstance().setUser(userEntity);
        System.out.println("查看响应字符串： "+getResponseStr());

        System.out.println("查看用户名： "+userEntity.getUserName());
        //告诉Store发生了变更事件
        UserStore.getInstance().emitStoreChange();

    }

}
