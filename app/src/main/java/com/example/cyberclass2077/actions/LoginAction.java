package com.example.cyberclass2077.actions;

import com.example.cyberclass2077.model.User;

public class LoginAction extends Action<User>{
    public static final String ACTION_LOGIN="login";
    LoginAction(String type, User user){
        super(type,user);
    }
}
