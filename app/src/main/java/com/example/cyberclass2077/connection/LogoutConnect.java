package com.example.cyberclass2077.connection;

import com.alibaba.fastjson.JSON;
import com.example.cyberclass2077.config.AppConfig;
import com.example.cyberclass2077.stores.UserStore;

import java.util.Map;

public class LogoutConnect extends Connect<String> {

    public LogoutConnect(){}
    public void sendLogoutRequest(String userName){

        postData(
                AppConfig.SETTINGS_LOGOUT,
                requestBodyBuilder("username",userName)
        );
    }
    @Override
    public void emitRequestResult(){
        UserStore store=UserStore.getInstance();
        Map logoutResultMap = JSON.parseObject(getResponseStr(),Map.class);
        if(!logoutResultMap.containsKey("isLogoutSuccessful"))
            return;
        boolean isLogoutSuccessful=(boolean)logoutResultMap.get("isLogoutSuccessful");
        //对Store数据的操作
        if (isLogoutSuccessful==true){
            //登出成功
            store.getUser().setLoginState(false);

        }
        else {
            //登出失败
        }

        //Store事件设定
        store.setStoreChangeEvent(
                store.new LogoutEvent(
                        isLogoutSuccessful
                )
        );
        //通知Store发生事件
        store.emitStoreChange();



    }
}
