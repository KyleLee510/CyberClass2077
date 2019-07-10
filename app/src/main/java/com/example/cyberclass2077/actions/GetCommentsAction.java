package com.example.cyberclass2077.actions;

public class GetCommentsAction extends Action<Integer> {
    public static final String ACTION_GET_COMMENTS="get_comments";
    public GetCommentsAction(String type,Integer dynamicId){
        super(type,dynamicId);
    }
}
