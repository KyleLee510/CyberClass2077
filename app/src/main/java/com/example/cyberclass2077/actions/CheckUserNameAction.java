package com.example.cyberclass2077.actions;

public class CheckUserNameAction extends Action<String>{
    public static final String ACTION_LOGOUT="logout";
    CheckUserNameAction(String type, String userName){
        super(type,userName);
    }
}
