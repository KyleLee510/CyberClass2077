package com.example.cyberclass2077.actions;

public class Action<T> {
    //type主要是对于Action包含的数据贴一个标签，因为一个Store可能会对若干个Action做出相应，
    // 这时候需要区分是哪个Action，就判断type
    //type可以通过Action子类的静态类变量比如ACTION_SIGNUP来直接设定。
    private final String type;

    //data是Action包含的数据，T是类型，大部分情况下对应model包中的一种实体类，
    //有时也可能直接放入String对象，Map对象之类的简单对象。
    private final T data;

    Action(String type, T data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return this.type;
    }

    public T getData() {
        return this.data;
    }
}