package com.example.cyberclass2077.connection;

import com.alibaba.fastjson.JSON;
import com.example.cyberclass2077.actions.GetUserInfoAction;
import com.example.cyberclass2077.config.AppConfig;
import com.example.cyberclass2077.model.UserInfo;
import com.example.cyberclass2077.stores.UserInfoStore;

public class GetUserInfoConnect extends Connect<String> {
    public GetUserInfoConnect (){}
    public void sendGetUserInfoRequest(String userName){
        postData(
                AppConfig.SETTINGS_GET_USERINFO,
                requestBodyBuilder(
                        "username",
                        userName
                )

        );

    }
    @Override
    public void emitRequestResult(){
        UserInfo userInfo= JSON.parseObject(getResponseStr(),UserInfo.class);
        UserInfoStore store=UserInfoStore.getInstance();
        store.setUserInfo(userInfo);
        //暂时没有失败情况，服务器直接返回的UserInfo。
        boolean isGetUserinfoSuccessful=true;
        store.setStoreChangeEvent(
                store.new GetUserInfoEvent(isGetUserinfoSuccessful)
        );
        store.emitStoreChange();

    }
}
