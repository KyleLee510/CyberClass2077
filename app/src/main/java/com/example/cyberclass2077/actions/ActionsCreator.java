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
    public void login(String userName,String passWord){
        User user = new User();
        user.setUserName(userName);
        user.setPassWord(passWord);

        dispatcher.dispatch(new LoginAction(LoginAction.ACTION_LOGIN,user));
    }
    public void signup(String userName,String passWord){
        User user = new User();
        user.setUserName(userName);
        user.setPassWord(passWord);
        dispatcher.dispatch(new SignupAction(SignupAction.ACTION_SIGNUP,user));
    }
    public void logout(String userName){
        dispatcher.dispatch(new LogoutAction(LogoutAction.ACTION_LOGOUT,userName));
    }
    public void updatePassword(String userName,String passWord){
        User user = new User();
        user.setUserName(userName);
        user.setPassWord(passWord);
        dispatcher.dispatch(new UpdatePasswordAction(UpdatePasswordAction.ACTION_UPDATE_PASSWORD,user));
    }

}
