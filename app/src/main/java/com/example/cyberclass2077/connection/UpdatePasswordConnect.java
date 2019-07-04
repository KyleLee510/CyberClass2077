package com.example.cyberclass2077.connection;

import com.alibaba.fastjson.JSON;
import com.example.cyberclass2077.config.AppConfig;
import com.example.cyberclass2077.model.User;
import com.example.cyberclass2077.stores.UserStore;

import java.util.Map;

public class UpdatePasswordConnect extends Connect<User> {
    //这个变量用来暂存界面那里发来的数据
    protected User userCache=new User();
    public UpdatePasswordConnect(){}
    public void sendUpdatePasswordRequest(User user){
        //存储UI界面发来的User数据
        userCache.setUser(user);
        postData(
                AppConfig.SETTINGS_UPDATEPASSWORD,
                requestBodyBuilder("user",user)
        );
    }
    @Override
    public void emitRequestResult(){
        Map updateResultMap = JSON.parseObject(getResponseStr(),Map.class);
        UserStore store =UserStore.getInstance();
        if(!updateResultMap.containsKey("isUpdatePasswordSuccessful"))
            return;
        boolean isUpdatePasswordSuccessful=(boolean)updateResultMap.get("isUpdatePasswordSuccessful");
        if (isUpdatePasswordSuccessful==true){
            //修改密码成功
            store.setUser(userCache);
        }
        else {
            //修改密码失败，暂时什么都不做
        }
        //设定发生了什么Event
        store.setStoreChangeEvent(
                store.new UpdatePasswordEvent(
                        isUpdatePasswordSuccessful
                )
        );
        //告诉Store发生了变更事件,Store会将这个事件发送到EventBus中，最后传递给Activity中的事件处理者解决。
        store.emitStoreChange();
    }
}
