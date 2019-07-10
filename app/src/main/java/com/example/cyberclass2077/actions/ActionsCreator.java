package com.example.cyberclass2077.actions;

import android.graphics.Bitmap;

import com.example.cyberclass2077.bean.DynamicPublishBean;
import com.example.cyberclass2077.dispatcher.Dispatcher;
import com.example.cyberclass2077.model.User;
import com.example.cyberclass2077.model.UserInfo;
import com.example.cyberclass2077.stores.UserStore;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
    //获取用户头像请求
    public void getPortrait(String userName){
        dispatcher.dispatch(
                new GetPictureAction(
                        GetPictureAction.ACTION_GET_PICTURE_PORTRAIT,
                        userName
                )
        );

    }
    //上传视频请求
    public void uploadVideo(String videoTitle, File videoFile,String tag,Bitmap bitmap){
        Map map=new HashMap();
        map.put("title",videoTitle);
        map.put("file",videoFile);
        map.put("tag",tag);
        map.put("bitmap",bitmap);
        dispatcher.dispatch(
                new UploadVideoAction(
                        UploadVideoAction.ACTION_UPLOAD_VIDEO,
                        map
                )
        );
    }
    //发送动态请求
    public void sendDynamic(DynamicPublishBean dynamic,Bitmap bitmap){
        Map map=new HashMap();
        map.put("dynamic",dynamic);
        map.put("bitmap",bitmap);
        dispatcher.dispatch(
                new SendDynamicAction(
                        SendDynamicAction.ACTION_SEND_DYNAMIC,
                        map
                )
        );

    }
    //获取动态列表的请求。参数type是指请求种类的参数
    //广场对应 "square"
    //我的对应 "my"
    //收藏对应 "like"
    public void getDynamics(String type){
        dispatcher.dispatch(
                new GetDynamicsAction(
                        GetDynamicsAction.ACTION_GET_DYNAMICS,
                        type
                )
        );

    }
    //获取动态图片请求
    //根据之前返回的List<DynamicPublishBean>中的的DynamicPublishBean的dynamicId来调用这个接口
    //返回的数据在DynamicStore的GetDynamicPictureEvent里
    //包含图片对应的动态ID,dynamicId以及图片的Bitmap形式
    public void getDynamicPicture(Integer dynamicId){
        Map map=new HashMap();
        map.put("id",dynamicId);
        map.put("type","dynamic");
        dispatcher.dispatch(
                new GetPictureByMapAction(
                        GetPictureByMapAction.ACTION_GET_PICTURE_BY_ID_DYNAMIC,
                        map
                )
        );
    }

    //获取视频列表的请求
    //返回值包括：
    //1.是否成功
    //2.List<FileInfo>
    //3.List<String> ，URL的list，其中每个String按序对应上面列表每个视频
    //4.List<Boolean>，作为参数的userName是否点赞了这个视频。
    //该接口的第一个参数为模式，收藏对应"like"，推荐对应"default"
    //该接口的第二个参数为选择的标签，默认为"notag"
    public void getVideos(String pattern,String tag){
        Map map=new HashMap();
        map.put("pattern",pattern);
        map.put("tag",tag);
        map.put("searchUserName","none");
        dispatcher.dispatch(
                new GetVideosAction(
                        GetVideosAction.ACTION_GET_VIDEOS,
                        map
                )
        );

    }
    //重载的上面的方法，增加了搜索名字段。
    public void getVideos(String pattern,String tag,String searchUserName){
        Map map=new HashMap();
        map.put("pattern",pattern);
        map.put("tag",tag);
        map.put("searchUserName",searchUserName);
        dispatcher.dispatch(
                new GetVideosAction(
                        GetVideosAction.ACTION_GET_VIDEOS,
                        map
                )
        );

    }

}
