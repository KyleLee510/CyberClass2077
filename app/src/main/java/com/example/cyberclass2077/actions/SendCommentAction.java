package com.example.cyberclass2077.actions;

import java.util.Map;

public class SendCommentAction extends Action<String> {
    public static final String ACTION_SEND_COMMENT_LEVEL_ONE="send_level_one_comment";
    public static final String ACTION_SEND_COMMENT_LEVEL_TWO="send_level_two_comment";
    public SendCommentAction(String type,String commentStr){super(type,commentStr);}
}
