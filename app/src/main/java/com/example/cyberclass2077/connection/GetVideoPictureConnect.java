package com.example.cyberclass2077.connection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.alibaba.fastjson.JSON;
import com.example.cyberclass2077.config.AppConfig;
import com.example.cyberclass2077.stores.DynamicStore;
import com.example.cyberclass2077.stores.FileInfoStore;

import java.util.Base64;
import java.util.Map;

public class GetVideoPictureConnect extends Connect<Integer>  {
    public GetVideoPictureConnect(){}
    public void sendGetVideoPictureRequest(Integer fileId){
        postData(
                AppConfig.VIDEOS_GET_PICTURE,
                requestBodyBuilder(
                        "fileId",fileId
                )
        );
    }
    @Override
    public void emitRequestResult(){
        Map resultMap = JSON.parseObject(getResponseStr(),Map.class);
        if (!resultMap.containsKey("isGetVideoPicSuccessful"))
            return;
        String bitmapStr=(String) resultMap.get("bitmapStr");
        Bitmap bitmap =stringToBitmap(bitmapStr);
        boolean isGetVideoPicSuccessful=(boolean)resultMap.get("isGetVideoPicSuccessful");
        Integer fileId=(Integer)resultMap.get("fileId");

        FileInfoStore store=FileInfoStore.getInstance();
        store.setStoreChangeEvent(
                store.new GetVideoPictureEvent(
                        isGetVideoPicSuccessful,
                        bitmap,
                        fileId
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
