package com.example.cyberclass2077.actions;

import com.example.cyberclass2077.model.UserInfo;

public class UpdateUserInfoAction extends Action<UserInfo> {
    public static final String ACTION_UPDATE_USERINFO="update_userinfo";
    UpdateUserInfoAction(String type,UserInfo userInfo){super(type,userInfo);}
}
