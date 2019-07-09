package com.example.cyberclass2077.connection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.alibaba.fastjson.JSON;
import com.example.cyberclass2077.bean.DynamicPublishBean;
import com.example.cyberclass2077.config.AppConfig;
import com.example.cyberclass2077.stores.DynamicStore;
import com.example.cyberclass2077.stores.UserStore;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class GetDynamicsConnect extends Connect<String> {
    public GetDynamicsConnect(){ }
    public void sendGetDynamicsRequest(String getType){
        RequestBody requestBody = new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addFormDataPart("username", UserStore.getInstance().getUser().getUserName())
                .addFormDataPart("gettype",getType)
                .build();
        postMulitiData(
                AppConfig.DYNAMIC_GET,
                requestBody
        );

    }
    @Override
    public void emitRequestResult() {
        Map resultMap = JSON.parseObject(getResponseStr(), Map.class);
        if(!resultMap.containsKey("isGetDynamicsSuccessful"))
            return;
        boolean isGetDynamicsSuccessful=(boolean)resultMap.get("isGetDynamicsSuccessful");
        String dynamicsStr=(String)resultMap.get("dynamics");
        String portraitsStr=(String)resultMap.get("portraits");
        List<DynamicPublishBean> dynamicList=JSON.parseArray(dynamicsStr,DynamicPublishBean.class);
        List<String> portraitsStrList=JSON.parseArray(portraitsStr,String.class);
        List<Bitmap> portraitsList=new ArrayList<Bitmap>();
        for (String portraitStr:portraitsStrList
             ) {
            portraitsList.add(stringToBitmap(portraitStr));

        }
        DynamicStore store=DynamicStore.getInstance();
        store.setStoreChangeEvent(
                store.new GetDynamicsEvent(
                        isGetDynamicsSuccessful,
                        dynamicList,
                        portraitsList
                )
        );
        System.out.println("Already emit");
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
