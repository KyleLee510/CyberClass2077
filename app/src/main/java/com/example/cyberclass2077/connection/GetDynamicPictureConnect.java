package com.example.cyberclass2077.connection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.alibaba.fastjson.JSON;
import com.example.cyberclass2077.config.AppConfig;
import com.example.cyberclass2077.stores.DynamicStore;

import java.util.Base64;
import java.util.Map;

public class GetDynamicPictureConnect extends Connect<Integer> {
    public GetDynamicPictureConnect(){}

    public void sendGetDynamicPictureRequest(Integer dynamicId){
        postData(
                AppConfig.DYNAMIC_GET_PICTURE,
                requestBodyBuilder(
                        "dynamicId",dynamicId
                )
        );
    }
    @Override
    public void emitRequestResult(){
        Map resultMap = JSON.parseObject(getResponseStr(),Map.class);
        if (!resultMap.containsKey("isGetDynamicPicSuccessful"))
            return;
        String bitmapStr=(String) resultMap.get("bitmapStr");
        Bitmap bitmap =stringToBitmap(bitmapStr);
        boolean isGetDynamicPicSuccessful=(boolean)resultMap.get("isGetDynamicPicSuccessful");
        Integer dynamicId=(Integer)resultMap.get("dynamicId");

        DynamicStore store=DynamicStore.getInstance();
        store.setStoreChangeEvent(
                store.new GetDynamicPictureEvent(
                        isGetDynamicPicSuccessful,
                        bitmap,
                        dynamicId
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
