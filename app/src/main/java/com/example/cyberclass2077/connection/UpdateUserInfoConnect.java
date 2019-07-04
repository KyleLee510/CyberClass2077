package com.example.cyberclass2077.connection;

import com.alibaba.fastjson.JSON;
import com.example.cyberclass2077.config.AppConfig;
import com.example.cyberclass2077.model.UserInfo;
import com.example.cyberclass2077.stores.UserInfoStore;

import java.util.Map;

public class UpdateUserInfoConnect extends Connect<UserInfo> {
    private UserInfo userInfo=new UserInfo();
    public UpdateUserInfoConnect(){}
    public void sendUpdateUserInfoRequest(UserInfo userInfo){
        this.userInfo.setUserInfo(userInfo);
        postData(
                AppConfig.SETTINGS_UPDATE_USERINFO,
                requestBodyBuilder("userinfo",userInfo)
        );

    }
    @Override
    public void emitRequestResult(){
        Map resultMap= JSON.parseObject(getResponseStr(),Map.class);
        UserInfoStore store=UserInfoStore.getInstance();
        if(!resultMap.containsKey("isUpdateUserInfoSuccessful"))
            return;
        boolean isUpdateUserInfoSuccessful=(boolean)resultMap.get("isUpdateUserInfoSuccessful");
        if (isUpdateUserInfoSuccessful==true){
            //修改成功
            store.setUserInfo(userInfo);
        }
        else {
            //修改失败，什么都不做
        }
        store.setStoreChangeEvent(
                store.new UpdateUserInfoEvent(
                        isUpdateUserInfoSuccessful
                )
        );

        store.emitStoreChange();

    }
}
