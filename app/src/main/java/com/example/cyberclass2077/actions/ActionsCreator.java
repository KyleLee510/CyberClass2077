package com.example.cyberclass2077.actions;

import android.graphics.Bitmap;

import com.example.cyberclass2077.dispatcher.Dispatcher;
import com.example.cyberclass2077.model.User;
import com.example.cyberclass2077.model.UserInfo;
import com.example.cyberclass2077.stores.UserStore;

public class ActionsCreator {
    private static ActionsCreator instance;
    final Dispatcher dispatcher;
    ActionsCreator(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }
    public static ActionsCreator get(Dispatcher dispatcher) {
        if (instance == null) {
            instance = new ActionsCreator(dispatcher);
        }
        return instance;
    }

    //登录请求
    public void login(String userName,String passWord){
        User user = new User();
        user.setUserName(userName);
        user.setPassWord(passWord);

        dispatcher.dispatch(new LoginAction(LoginAction.ACTION_LOGIN,user));
    }
    //注册请求
    public void signup(String userName,String passWord){
        User user = new User();
        user.setUserName(userName);
        user.setPassWord(passWord);
        dispatcher.dispatch(new SignupAction(SignupAction.ACTION_SIGNUP,user));
    }
    //用户名查重请求
    public void nameCheck(String userName){
        dispatcher.dispatch(new UserNameCheckAction(UserNameCheckAction.ACTION_USERNAMECHECK,userName));
    }
    //登出请求
    public void logout(String userName){
        dispatcher.dispatch(new LogoutAction(LogoutAction.ACTION_LOGOUT,userName));
    }
    //更新（修改）密码请求
    public void updatePassword(String userName,String passWord){
        User user = new User();
        user.setUserName(userName);
        user.setPassWord(passWord);
        dispatcher.dispatch(new UpdatePasswordAction(UpdatePasswordAction.ACTION_UPDATE_PASSWORD,user));
    }

    //更新（修改）用户信息请求
    //New 一个UserInfo,从控件中获取值来用这个类的set方法设定成员变量，注意转换格式
    public void updateUserInfo(UserInfo userInfo){
        dispatcher.dispatch(new UpdateUserInfoAction(
                UpdateUserInfoAction.ACTION_UPDATE_USERINFO,
                userInfo
        ));
    }
    //获取用户信息请求
    //用户进入用户设置界面后就调用这个接口
    //结果存储在UserinfoStore.getInstance.getUserInfo里
    public void getUserInfo(String userName){
        dispatcher.dispatch(new GetUserInfoAction(
                GetUserInfoAction.ACTION_GET_USERINFO,
                userName
        ));
    }
    //上传用户头像请求
    public void uploadPortrait(Bitmap bitmap){
        dispatcher.dispatch(new UploadPictureAction(
        UploadPictureAction.ACTION_SEND_PICTURE_PORTRAIT,
        bitmap
        ));
    }


}
