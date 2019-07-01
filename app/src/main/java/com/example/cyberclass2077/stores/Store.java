package com.example.cyberclass2077.stores;

import com.example.cyberclass2077.actions.Action;
import com.example.cyberclass2077.connection.Connect;
import com.squareup.otto.Bus;


public abstract class Store {
    private static final Bus bus=new Bus();


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
    public abstract StoreChangeEvent changeEvent();
    public abstract void onAction(Action action);
    public class StoreChangeEvent{}
}
