package com.example.cyberclass2077.connection;

import com.alibaba.fastjson.JSON;
import com.example.cyberclass2077.config.AppConfig;
import com.example.cyberclass2077.model.FileInfo;
import com.example.cyberclass2077.stores.FileInfoStore;
import com.example.cyberclass2077.stores.UserStore;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;

import java.util.List;
import java.util.Map;

public class GetVideosConnect extends Connect<Map> {
    public GetVideosConnect(){}
    public void sendGetVideosRequest(Map map){
        String userName= UserStore.getInstance().getUser().getUserName();
        if(userName.equals("")){
            userName="visitor";
        }
        String pattern=(String)map.get("pattern");
        String tag=(String)map.get("tag");
        String searchUserName=(String)map.get("searchUserName");
        RequestBody requestBody = new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addFormDataPart("username",userName)
                .addFormDataPart("pattern",pattern)
                .addFormDataPart("tag",tag)
                .addFormDataPart("searchusername",searchUserName)
                .build();
        postMulitiData(
                AppConfig.VIDEOS_GET,
                requestBody
        );

    }
    @Override
    public void emitRequestResult() {
        Map resultMap = JSON.parseObject(getResponseStr(), Map.class);
        if(!resultMap.containsKey("isGetVideosSuccessful"))
            return;
        boolean isGetVideosSuccessful=(boolean)resultMap.get("isGetVideosSuccessful");
        String video_list_str=(String)resultMap.get("videoList");
        String video_url_list_str=(String)resultMap.get("videoUrlList");
        String video_like_list_str=(String)resultMap.get("videoLikeList");
        List<FileInfo> video_list=JSON.parseArray(video_list_str,FileInfo.class);
        List<String> video_url_list=JSON.parseArray(video_url_list_str,String.class);
        List<Boolean>video_like_list=JSON.parseArray(video_like_list_str,Boolean.class);

        FileInfoStore store=FileInfoStore.getInstance();
        store.setStoreChangeEvent(
                store.new GetVideosEvent(
                        isGetVideosSuccessful,
                        video_list,
                        video_url_list,
                        video_like_list
                )
        );
        store.emitStoreChange();

    }
}
