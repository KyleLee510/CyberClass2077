package com.example.cyberclass2077.actions;

import java.io.File;
import java.util.Map;

public class UploadVideoAction extends Action<Map> {
    public static final String ACTION_UPLOAD_VIDEO="upload_video";
    public UploadVideoAction(String type,Map map){
        super(type,map);
    }
}
