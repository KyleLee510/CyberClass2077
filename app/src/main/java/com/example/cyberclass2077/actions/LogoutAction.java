package com.example.cyberclass2077.actions;

import com.example.cyberclass2077.model.User;

public class LogoutAction extends Action<String>{
    public static final String ACTION_LOGOUT="logout";
    LogoutAction(String type, String userName){
        super(type,userName);
    }
}