package com.example.cyberclass2077.actions;

import java.util.Map;

public class SendDynamicAction extends Action<Map> {
    public static  final String ACTION_SEND_DYNAMIC="send_dynamic";
    public SendDynamicAction(String type,Map map){
        super(type,map);
    }
}
