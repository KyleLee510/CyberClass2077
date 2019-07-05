package com.example.cyberclass2077.actions;

import com.example.cyberclass2077.model.UserInfo;

public class GetUserInfoAction extends Action<String> {
    public static final String ACTION_GET_USERINFO="get_userinfo";
    GetUserInfoAction(String type,String userName){
        super(type,userName);
    }
}
