package com.example.cyberclass2077.connection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.alibaba.fastjson.JSON;
import com.example.cyberclass2077.config.AppConfig;
import com.example.cyberclass2077.stores.UserInfoStore;

import java.util.HashMap;
import java.util.Map;

//获取用户头像
public class GetPicturePortraitConnect extends Connect<String> {
    public GetPicturePortraitConnect(){
    }
    public void sendGetPortraitRequest(String userName){
        postData(
                AppConfig.SETTINGS_GET_PICTURE_PORTRAIT,
                requestBodyBuilder(
                        "username",
                        userName
                )
        );
    }
    @Override
    public void emitRequestResult(){
        Map resultMap=new HashMap();
        resultMap=JSON.parseObject(getResponseStr(),Map.class);
        if(!(resultMap.containsKey("picStr")&&resultMap.containsKey("isGetPortraitSuccessful")))
            return;
        Bitmap bitmap= stringToBitmap((String) resultMap.get("picStr"));
        boolean isGetPortraitSuccessful=(boolean)resultMap.get("isGetPortraitSuccessful");
        UserInfoStore store=UserInfoStore.getInstance();
        store.setStoreChangeEvent(
                store.new GetPortraitEvent(
                        bitmap,
                        isGetPortraitSuccessful

                )
        );
        store.emitStoreChange();

    }

    public Bitmap stringToBitmap(String string) {
        // 将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
