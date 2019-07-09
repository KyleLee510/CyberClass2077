package com.example.cyberclass2077.connection;

import android.graphics.Bitmap;

import com.alibaba.fastjson.JSON;
import com.example.cyberclass2077.bean.DynamicPublishBean;
import com.example.cyberclass2077.config.AppConfig;
import com.example.cyberclass2077.stores.DynamicStore;
import com.example.cyberclass2077.views.Dynamic.DynamicPublish;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Map;

public class SendDynamicConnect extends Connect<DynamicPublishBean>{
    private volatile DynamicPublishBean dynamic;
    public SendDynamicConnect(){}
    public void sendDynamicRequest(Map map){
        dynamic=(DynamicPublishBean) map.get("dynamic");
        Bitmap bitmap=(Bitmap)map.get("bitmap");
        String bitmapStr=bitmapToString(bitmap);
        String dynamicStr= JSON.toJSONString(dynamic);
        String userName=dynamic.getUserName();
        if(bitmapStr==null)
            return;

        RequestBody requestBody = new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addFormDataPart("username",userName)
                .addFormDataPart("dynamic",dynamicStr)
                .addFormDataPart("bitmap",bitmapStr)
                .build();
        postMulitiData(
                AppConfig.DYNAMIC_SEND,
                requestBody
        );


    }
    @Override
    public void emitRequestResult() {
        Map sendResultMap = JSON.parseObject(getResponseStr(), Map.class);
        DynamicStore store=DynamicStore.getInstance();
        if (!sendResultMap.containsKey("isSendDynamicSuccessful"))
            return;
        boolean isSendDynamicSuccessful=(boolean)sendResultMap.get("isSendDynamicSuccessful");
        Integer dynamicId=(Integer)sendResultMap.get("dynamicId");
        //store.addDynamic(dynamic);
        store.setStoreChangeEvent(
                store.new SendDynamicEvent(
                        isSendDynamicSuccessful
                )
        );
        store.emitStoreChange();


    }

    public String bitmapToString(Bitmap bitmap) {
        //将Bitmap转换成字符串
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] data = baos.toByteArray();
        //String datastring = Base64.encodeToString(data, Base64.DEFAULT);
        String datastring= Base64.getEncoder().encodeToString(data);
        return  datastring;
    }

}
