package com.example.cyberclass2077.model;

public class User {
    private String userId="";
    private String userName="";
    private String passWord="";
    private boolean loginState=false;

    public User(){
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord=passWord;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setLoginState(boolean loginState) {
        this.loginState = loginState;
    }

    public boolean isLoginState() {
        return loginState;
    }
    public User getUser(){
        return this;
    }
    public void setUser(User user){
        if(user!=null){
            this.userId=user.getUserId();
            this.userName=user.getUserName();
            this.passWord= user.getPassWord();
            this.loginState=user.isLoginState();
        }

    }
}
