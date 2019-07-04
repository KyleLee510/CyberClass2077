package com.example.cyberclass2077.actions;

import com.example.cyberclass2077.dispatcher.Dispatcher;
import com.example.cyberclass2077.model.User;
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


}
