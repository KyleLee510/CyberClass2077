package com.example.cyberclass2077.connection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.alibaba.fastjson.JSON;
import com.example.cyberclass2077.config.AppConfig;
import com.example.cyberclass2077.model.FileInfo;
import com.example.cyberclass2077.stores.FileInfoStore;
import com.example.cyberclass2077.stores.UserStore;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class GetVideosConnect extends Connect<Map> {
    public GetVideosConnect(){}
    public void sendGetVideosRequest(Map map){
        String userName= UserStore.getInstance().getUser().getUserName();
        if(userName.equals("")){
            userName="visitor";
        }
        System.out.println("this is print");
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
        String portraitsStr=(String)resultMap.get("portraitsList");
        List<String> video_portraits_str=JSON.parseArray(portraitsStr,String.class);
        List<Bitmap> portraitsList=new ArrayList<Bitmap>();
        for (String portraitStr:video_portraits_str
        ) {
            portraitsList.add(stringToBitmap(portraitStr));

        }
        List<FileInfo> video_list=JSON.parseArray(video_list_str,FileInfo.class);
        List<String> video_url_list=JSON.parseArray(video_url_list_str,String.class);
        List<Boolean>video_like_list=JSON.parseArray(video_like_list_str,Boolean.class);

        FileInfoStore store=FileInfoStore.getInstance();
        store.setStoreChangeEvent(
                store.new GetVideosEvent(
                        isGetVideosSuccessful,
                        video_list,
                        video_url_list,
                        video_like_list,
                        portraitsList
                )
        );
        store.emitStoreChange();

    }
    public Bitmap stringToBitmap(String string) {
        // 将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            //bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmapArray= Base64.getDecoder().decode(string);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
