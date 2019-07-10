package com.example.cyberclass2077.connection;

import com.alibaba.fastjson.JSON;
import com.example.cyberclass2077.config.AppConfig;
import com.example.cyberclass2077.stores.CommentStore;

import java.util.HashMap;
import java.util.Map;

public class SendCommentConnect extends Connect<String> {
    public SendCommentConnect(){}

    public void sendCommentRequest(String commentStr){
        postData(
                AppConfig.COMMENT_SEND,
                requestBodyBuilder("comment",commentStr)
        );
    }

    @Override
    public void emitRequestResult() {
        Map resultMap= JSON.parseObject(getResponseStr(), Map.class);
        if(!resultMap.containsKey("isSendCommentSuccessful"))
            return;

        boolean isSendCommentSuccessful=(boolean)resultMap.get("isSendCommentSuccessful");
        CommentStore store=CommentStore.getInstance();
        store.setStoreChangeEvent(
                store.new SendCommentEvent(
                        isSendCommentSuccessful
                )
        );
        store.emitStoreChange();

    }
}
