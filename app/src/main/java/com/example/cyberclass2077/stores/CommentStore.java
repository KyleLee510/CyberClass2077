package com.example.cyberclass2077.stores;

import com.example.cyberclass2077.actions.Action;
import com.example.cyberclass2077.actions.SendCommentAction;
import com.example.cyberclass2077.connection.Connect;
import com.example.cyberclass2077.connection.SendCommentConnect;
import com.example.cyberclass2077.model.Comment;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class CommentStore extends Store {
    private static CommentStore instance;
    private Connect connect;
    private List<Comment> commentList;
    private CommentStore(){}
    public static CommentStore getInstance(){
        if(instance==null)
            instance=new CommentStore();
        return instance;
    }
    public boolean addComment(Comment comment){
        if (commentList==null){
            commentList=new ArrayList<Comment>();
        }
        if (comment==null){
            return false;
        }
        commentList.add(comment);
        return true;

    }
    @Override
    @Subscribe
    public void onAction(Action action){
        switch (action.getType()){
            case SendCommentAction
                    .ACTION_SEND_COMMENT_LEVEL_ONE:{
                connect=null;
                connect=new SendCommentConnect();
                ((SendCommentConnect)connect).sendCommentRequest((String)action.getData());
                break;
            }
            default:{
            }
        }

    }

    public class SendCommentEvent extends StoreChangeEvent{
        public boolean isSendCommentSuccessful=false;
        public SendCommentEvent(
                boolean isSendCommentSuccessful
        ){
            this.isSendCommentSuccessful=isSendCommentSuccessful;
        }
    }
}
