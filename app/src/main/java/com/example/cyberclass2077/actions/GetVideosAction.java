package com.example.cyberclass2077.actions;

import java.util.Map;

public class GetVideosAction extends Action<Map> {
    public static final String ACTION_GET_VIDEOS="get_videos";
    public GetVideosAction(String type,Map map){
        super(type,map);
    }
}
