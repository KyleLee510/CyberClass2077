package com.example.cyberclass2077.stores;

import android.graphics.Bitmap;

import com.example.cyberclass2077.actions.Action;
import com.example.cyberclass2077.actions.GetDynamicsAction;
import com.example.cyberclass2077.actions.SendDynamicAction;
import com.example.cyberclass2077.bean.DynamicPublishBean;
import com.example.cyberclass2077.connection.Connect;
import com.example.cyberclass2077.connection.GetDynamicsConnect;
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
            case GetDynamicsAction
                        .ACTION_GET_DYNAMICS:
                connect=null;
                connect=new GetDynamicsConnect();
                ((GetDynamicsConnect)connect).sendGetDynamicsRequest((String)action.getType());
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
    public class GetDynamicsEvent extends StoreChangeEvent{
        public boolean isGetDynamicsSuccessful=false;
        public List<DynamicPublishBean> dynamicList;
       // public List<Bitmap>dynamicPicList;
        public List<Bitmap>portraitList;
        public GetDynamicsEvent(
                boolean isGetDynamicsSuccessful,
                List<DynamicPublishBean> dynamicList,
             //   List<Bitmap>dynamicPicList,
                List<Bitmap>portraitList
        ){
            this.isGetDynamicsSuccessful=isGetDynamicsSuccessful;
            this.dynamicList=dynamicList;
       //     this.dynamicPicList=dynamicPicList;
            this.portraitList=portraitList;
        }
    }
}
