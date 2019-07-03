package com.example.cyberclass2077.stores;

import com.example.cyberclass2077.actions.Action;
import com.example.cyberclass2077.connection.Connect;
import com.squareup.otto.Bus;


public abstract class Store {
    private static final Bus bus=new Bus();
    private static StoreChangeEvent storeChangeEvent;


    protected Store(){

    }
    public void register(final Object view){
        this.bus.register(view);
    }
    public void unregister(final Object view){
        this.bus.unregister(view);
    }
    public void emitStoreChange(){
        this.bus.post(changeEvent());
    }
    public  StoreChangeEvent changeEvent(){
        return getStoreChangeEvent();
    }
    public abstract void onAction(Action action);
    public class StoreChangeEvent{}
    public void  setStoreChangeEvent(StoreChangeEvent event){
        storeChangeEvent=event;
    }
    public StoreChangeEvent getStoreChangeEvent(){
        if (storeChangeEvent!=null)
            return storeChangeEvent;
        else{
            return new StoreChangeEvent();
        }
    }
}
