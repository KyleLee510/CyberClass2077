package com.example.cyberclass2077.stores;

import com.example.cyberclass2077.actions.Action;
import com.example.cyberclass2077.actions.UpdateUserInfoAction;
import com.example.cyberclass2077.connection.Connect;
import com.example.cyberclass2077.connection.UpdateUserInfoConnect;
import com.example.cyberclass2077.model.User;
import com.example.cyberclass2077.model.UserInfo;
import com.squareup.otto.Subscribe;

public class UserInfoStore extends Store {
    private static UserInfoStore instance;
    private Connect connect;
    private UserInfo userInfo=new UserInfo();
    private UserInfoStore(){
        super();
    }
    public static UserInfoStore getInstance(){
        if(instance==null){
            instance=new UserInfoStore();
        }
        return instance;
    }
    public UserInfo getUserInfo(){
        return userInfo.getUserInfo();
    }
    public void setUserInfo(UserInfo userInfo){
        this.userInfo=userInfo;
    }

    @Override
    @Subscribe
    public void onAction(Action action){
        switch (action.getType()){
            case UpdateUserInfoAction
                    .ACTION_UPDATE_USERINFO:
                connect=null;
                connect=new UpdateUserInfoConnect();
                ((UpdateUserInfoConnect)connect).sendUpdateUserInfoRequest((UserInfo)action.getData());
                break;

                default:
        }

    }
    public class UpdateUserInfoEvent extends StoreChangeEvent{
        boolean isUpdateUserInfoSuccessful=false;
        public UpdateUserInfoEvent(boolean isUpdateUserInfoSuccessful){
            this.isUpdateUserInfoSuccessful=isUpdateUserInfoSuccessful;
        }
    }
}
