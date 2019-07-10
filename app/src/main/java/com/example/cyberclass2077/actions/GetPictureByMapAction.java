package com.example.cyberclass2077.actions;

import java.util.Map;

public class GetPictureByMapAction extends Action<Map> {
    public static final String ACTION_GET_PICTURE_BY_ID_DYNAMIC="get_picture_by_id_dynamic";
    public static final String ACTION_GET_PICTURE_BY_ID_VIDEO="get_picture_by_id_video";
    public GetPictureByMapAction(String type, Map map){
        super(type,map);
    }
}
