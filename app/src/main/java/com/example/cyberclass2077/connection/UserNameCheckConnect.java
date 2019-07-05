package com.example.cyberclass2077.connection;

import com.alibaba.fastjson.JSON;
import com.example.cyberclass2077.actions.UserNameCheckAction;
import com.example.cyberclass2077.config.AppConfig;
import com.example.cyberclass2077.stores.UserStore;

import java.util.Map;

public class UserNameCheckConnect extends Connect<String> {
    public UserNameCheckConnect(){}
    public void sendUserNameCheckRequest(String userName){
        postData(
                AppConfig.SIGNUP_NAME_CHECK,
                requestBodyBuilder("username",userName)
        );
    }
    @Override
    public void emitRequestResult(){
        Map checkResultMap = JSON.parseObject(getResponseStr(),Map.class);
        UserStore store =UserStore.getInstance();
        if(!checkResultMap.containsKey("isNameExist"))
            return;
        boolean isNameExist=(boolean)checkResultMap.get("isNameExist");
        //这里没什么要Store修改的东西

        //设定事件
        store.setStoreChangeEvent(
                store.new NameCheckEvent(
                        isNameExist
                )
        );
        //通知事件
        store.emitStoreChange();
    }

}
