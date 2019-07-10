package com.example.cyberclass2077.connection;

import com.alibaba.fastjson.JSON;
import com.example.cyberclass2077.config.AppConfig;
import com.example.cyberclass2077.stores.FileInfoStore;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;

import java.util.Map;

public class SendLikeConnect extends Connect<Map> {

    public void sendLikeRequest(Map map){
        String userName=(String) map.get("username");
        String entityType=(String)map.get("entityType");
        Integer entityId=(Integer)map.get("entityId");
        String Id=entityId.toString();
        Boolean isLikeOrNot=(Boolean)map.get("isLikeOrNot");
        String isLikeOrNotStr=isLikeOrNot.toString();
        RequestBody requestBody = new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addFormDataPart("username",userName)
                .addFormDataPart("entityType",entityType)
                .addFormDataPart("entityId",Id)
                .addFormDataPart("likeornot",isLikeOrNotStr)
                .build();
        postMulitiData(
                AppConfig.LIKE_SEND,
                requestBody
        );

    }
    @Override
    public void emitRequestResult() {
        Map sendResultMap = JSON.parseObject(getResponseStr(), Map.class);
        if(!sendResultMap.containsKey("isLikeSuccessful"))
            return;
        boolean isLikeSuccessful=(boolean)sendResultMap.get("isLikeSuccessful");
        FileInfoStore store=FileInfoStore.getInstance();
        store.setStoreChangeEvent(
                store.new LikeVideoEvent(
                        isLikeSuccessful
                )
        );
        store.emitStoreChange();
    }
}
