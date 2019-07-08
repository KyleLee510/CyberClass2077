package com.example.cyberclass2077.stores;

import com.example.cyberclass2077.actions.Action;
import com.example.cyberclass2077.actions.UploadVideoAction;
import com.example.cyberclass2077.connection.Connect;
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
    public FileInfoStore(){
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
            case UploadVideoAction.ACTION_UPLOAD_VIDEO:
                connect=null;
                connect=new UploadVideoConnect();
                ((UploadVideoConnect)connect).sendUploadVideoRequest((Map)action.getData());
                break;
                default:


        }
    }

    //上传视频事件
    public class UploadVideoEvent extends StoreChangeEvent{
        boolean isUploadVideoSuccessful=false;
        public UploadVideoEvent(boolean isUploadVideoSuccessful){
            this.isUploadVideoSuccessful=isUploadVideoSuccessful;
        }
    }
}
