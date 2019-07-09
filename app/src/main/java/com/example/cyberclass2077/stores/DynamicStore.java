package com.example.cyberclass2077.stores;

import com.example.cyberclass2077.actions.Action;
import com.example.cyberclass2077.actions.SendDynamicAction;
import com.example.cyberclass2077.bean.DynamicPublishBean;
import com.example.cyberclass2077.connection.Connect;
import com.example.cyberclass2077.connection.SendDynamicConnect;
import com.squareup.otto.Subscribe;

import java.util.List;
import java.util.Map;

public class DynamicStore  extends Store{
    private static DynamicStore instance;
    private Connect connect;
    private List<DynamicPublishBean> dynamics;
    private DynamicStore (){}
    public static DynamicStore getInstance(){
        if(instance==null)
            instance=new DynamicStore();
        return instance;
    }
    public boolean addDynamic(DynamicPublishBean dynamic){
        if (dynamic==null)
            return false;
        dynamics.add(dynamic);
        return  true;
    }
    @Override
    @Subscribe
    public void onAction(Action action){
        switch (action.getType()){
            case SendDynamicAction.ACTION_SEND_DYNAMIC:
                connect=null;
                connect= new SendDynamicConnect();
                ((SendDynamicConnect)connect).sendDynamicRequest((Map)action.getData());
                break;
                default:

        }

    }
    public class SendDynamicEvent extends StoreChangeEvent{
        public boolean isSendDynamicSuccessful=false;
        public SendDynamicEvent(boolean isSendDynamicSuccessful){
            this.isSendDynamicSuccessful=isSendDynamicSuccessful;
        }
    }
}
