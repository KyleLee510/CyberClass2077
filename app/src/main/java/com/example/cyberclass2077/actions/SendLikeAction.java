package com.example.cyberclass2077.actions;

import java.util.Map;

public class SendLikeAction extends Action<Map> {
    public static final String ACTION_SEND_LIKE="like";
    public SendLikeAction(String type,Map map){
        super(type,map);
    }
}
