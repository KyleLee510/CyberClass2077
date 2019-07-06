package com.example.cyberclass2077.stores;

import android.graphics.Bitmap;

import com.example.cyberclass2077.actions.Action;
import com.example.cyberclass2077.actions.GetPictureAction;
import com.example.cyberclass2077.actions.GetUserInfoAction;
import com.example.cyberclass2077.actions.UpdateUserInfoAction;
import com.example.cyberclass2077.actions.UploadPictureAction;
import com.example.cyberclass2077.connection.Connect;
import com.example.cyberclass2077.connection.GetPicturePortraitConnect;
import com.example.cyberclass2077.connection.GetUserInfoConnect;
import com.example.cyberclass2077.connection.UpdateUserInfoConnect;
import com.example.cyberclass2077.connection.UploadPicturePortraitConnect;
import com.example.cyberclass2077.model.User;
import com.example.cyberclass2077.model.UserInfo;
import com.squareup.otto.Subscribe;

public class UserInfoStore extends Store {
    private static UserInfoStore instance;
    private Connect connect;
    private UserInfo userInfo=new UserInfo();
    private UserInfoStore(){
        super();
    }
    public static UserInfoStore getInstance(){
        if(instance==null){
            instance=new UserInfoStore();
        }
        return instance;
    }
    public UserInfo getUserInfo(){
        return userInfo.getUserInfo();
    }
    public void setUserInfo(UserInfo userInfo){
        this.userInfo=userInfo;
    }

    @Override
    @Subscribe
    public void onAction(Action action){
        switch (action.getType()){
            case UpdateUserInfoAction
                    .ACTION_UPDATE_USERINFO:
                connect=null;
                connect=new UpdateUserInfoConnect();
                ((UpdateUserInfoConnect)connect).sendUpdateUserInfoRequest((UserInfo)action.getData());
                break;
            case GetUserInfoAction
                        .ACTION_GET_USERINFO:
                connect=null;
                connect=new GetUserInfoConnect();
                ((GetUserInfoConnect)connect).sendGetUserInfoRequest((String)action.getData());
                break;
            case UploadPictureAction
                        .ACTION_SEND_PICTURE_PORTRAIT:{
                connect=null;
                connect=new UploadPicturePortraitConnect();
                ((UploadPicturePortraitConnect)connect).sendUploadPicturePortraitRequest(
                        (Bitmap)action.getData()
                );
                break;
            }
            case GetPictureAction.ACTION_GET_PICTURE_PORTRAIT:
            {
                connect=null;
                connect=new GetPicturePortraitConnect();
                ((GetPicturePortraitConnect)connect).sendGetPortraitRequest(
                        (String)action.getData()
                );
                break;
            }




                default:
        }

    }
    //更新用户信息事件
    public class UpdateUserInfoEvent extends StoreChangeEvent{
        public boolean isUpdateUserInfoSuccessful = false;
        public UpdateUserInfoEvent(boolean isUpdateUserInfoSuccessful){
            this.isUpdateUserInfoSuccessful = isUpdateUserInfoSuccessful;
        }
    }
    //获取用户信息事件
    public class GetUserInfoEvent extends StoreChangeEvent{
        public boolean isGetUserInfoSuccessful = false;
        public GetUserInfoEvent(boolean isGetUserInfoSuccessful){
            this.isGetUserInfoSuccessful=isGetUserInfoSuccessful;
        }
    }
    //设置用户头像事件
    public class UploadPortraitEvent extends StoreChangeEvent{
        public boolean isSetPortraitSuccessful=false;
        public UploadPortraitEvent (boolean isSetPortraitSuccessful){
            this.isSetPortraitSuccessful=isSetPortraitSuccessful;
        }
    }
    //获取用户头像事件
    public class GetPortraitEvent extends StoreChangeEvent{
        public Bitmap portrait=null;
        public boolean isGetPortraitSuccessful=false;
        public GetPortraitEvent(Bitmap portrait,boolean isGetPortraitSuccessful){
            this.portrait=portrait;
            this.isGetPortraitSuccessful=isGetPortraitSuccessful;
        }

    }
}
