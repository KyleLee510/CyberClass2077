package com.example.cyberclass2077.actions;

import com.example.cyberclass2077.model.User;

public class SignupAction extends Action<User> {
    public static final String ACTION_SIGNUP="signup";
    SignupAction(String type, User user){
        super(type,user);
    }
}
