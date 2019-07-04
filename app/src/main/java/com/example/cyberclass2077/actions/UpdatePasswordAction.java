package com.example.cyberclass2077.actions;

import com.example.cyberclass2077.model.User;

public class UpdatePasswordAction extends Action<User>{
    public static final String ACTION_UPDATE_PASSWORD="update_password";
    UpdatePasswordAction(String type,User user){
        super(type,user);
    }
}
