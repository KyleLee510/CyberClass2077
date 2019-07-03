package com.example.cyberclass2077.dispatcher;

import com.example.cyberclass2077.actions.Action;
import com.example.cyberclass2077.stores.Store;

import java.util.ArrayList;
import java.util.List;

public class Dispatcher {
    private static Dispatcher instance;
    private final List<Store> stores = new ArrayList<>();

    public static Dispatcher get() {
        if (instance == null) {
            instance = new Dispatcher();
        }
        return instance;
    }

    private Dispatcher() {
    }

    public void register(final Store store) {
        if(!stores.contains(store))
            stores.add(store);
    }

    public void unregister(final Store store) {
        stores.remove(store);
    }

    public void dispatch(Action action) {
        post(action);
    }

    private void post(final Action action) {
        for (Store store : stores) {
            store.onAction(action);
        }
    }
}
