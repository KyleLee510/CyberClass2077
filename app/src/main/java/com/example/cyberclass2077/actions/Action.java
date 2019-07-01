package com.example.cyberclass2077.actions;

public class Action<T> {
    private final String type;
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