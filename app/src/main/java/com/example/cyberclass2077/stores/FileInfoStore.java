package com.example.cyberclass2077.stores;

import android.graphics.Bitmap;

import com.example.cyberclass2077.actions.Action;
import com.example.cyberclass2077.actions.GetPictureByMapAction;
import com.example.cyberclass2077.actions.GetVideosAction;
import com.example.cyberclass2077.actions.UploadVideoAction;
import com.example.cyberclass2077.connection.Connect;
import com.example.cyberclass2077.connection.GetVideoPictureConnect;
import com.example.cyberclass2077.connection.GetVideosConnect;
import com.example.cyberclass2077.connection.UploadVideoConnect;
import com.example.cyberclass2077.model.FileInfo;
import com.squareup.otto.Subscribe;

import java.io.File;
import java.util.List;
import java.util.Map;

public class FileInfoStore  extends  Store{
    private static FileInfoStore instance;
    private Connect connect;
    private List<FileInfo> fileInfos;
    private FileInfoStore(){
        super();
    }
    public static FileInfoStore getInstance() {
        if (instance==null){
            instance=new FileInfoStore();
        }
        return instance;
    }
    public boolean addFileInfo(FileInfo fileInfo){
        if (fileInfo==null){
            return false;
        }
        this.fileInfos.add(fileInfo);
        return true;
    }

    @Override
    @Subscribe
    public void onAction(Action action){
        switch (action.getType()){
            case UploadVideoAction
                    .ACTION_UPLOAD_VIDEO:
                connect=null;
                connect=new UploadVideoConnect();
                ((UploadVideoConnect)connect).sendUploadVideoRequest((Map)action.getData());
                break;
            case GetVideosAction
                        .ACTION_GET_VIDEOS:
                connect=null;
                connect=new GetVideosConnect();
                ((GetVideosConnect)connect).sendGetVideosRequest((Map)action.getData());
                break;
            case GetPictureByMapAction
                        .ACTION_GET_PICTURE_BY_ID_VIDEO:
                connect=null;
                connect=new GetVideoPictureConnect();
                Integer id=(Integer) ((Map)action.getData()).get("id");
                ((GetVideoPictureConnect)connect).sendGetVideoPictureRequest(id);
                break;
            default:


        }
    }

    //上传视频事件
    public class UploadVideoEvent extends StoreChangeEvent{
        public boolean isUploadVideoSuccessful=false;
        public UploadVideoEvent(boolean isUploadVideoSuccessful){
            this.isUploadVideoSuccessful=isUploadVideoSuccessful;
        }
    }
    //获取视频列表事件
    public class GetVideosEvent extends StoreChangeEvent{
        public boolean isGetVideosSuccessful=false;
        public List<FileInfo> video_list;
        public  List<String>video_url_list;
        public List<Boolean>video_like_list;
        public GetVideosEvent(
                boolean isGetVideosSuccessful,
                List<FileInfo> video_list,
                List<String>video_url_list,
                List<Boolean>video_like_list
        ){
            this.isGetVideosSuccessful=isGetVideosSuccessful;
            this.video_list=video_list;
            this.video_url_list=video_url_list;
            this.video_like_list=video_like_list;
        }
    }
    //获取视频封面图片事件
    public class GetVideoPictureEvent extends StoreChangeEvent{
        public boolean isGetVideoPicSuccessful=false;
        Bitmap bitmap;
        Integer fileId;
        public GetVideoPictureEvent(
                boolean isGetVideoPicSuccessful,
                Bitmap bitmap,
                Integer fileId
        ){
            this.isGetVideoPicSuccessful=isGetVideoPicSuccessful;
            this.bitmap=bitmap;
            this.fileId=fileId;
        }
    }
}
