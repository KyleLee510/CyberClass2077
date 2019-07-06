package com.example.cyberclass2077.connection;

import android.graphics.Bitmap;
//import android.util.Base64;
import java.util.Base64;
import com.alibaba.fastjson.JSON;
import com.example.cyberclass2077.config.AppConfig;
import com.example.cyberclass2077.stores.UserInfoStore;
import com.example.cyberclass2077.stores.UserStore;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public class UploadPicturePortraitConnect extends Connect<Bitmap> {
    public UploadPicturePortraitConnect(){}
    public void sendUploadPicturePortraitRequest(Bitmap bitmap){
        String userName= UserStore.getInstance().getUser().getUserName();
        String picStr=bitmapToString(bitmap);
        RequestBody requestBody = new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addFormDataPart("username",userName)
                .addFormDataPart("portrait",picStr)
                .build();
        postMulitiData(
                AppConfig.SETTINGS_UPLOAD_PICTURE_PORTRAIT,
                requestBody
        );
    }
    @Override
    public void emitRequestResult(){
        Map uploadResultMap = JSON.parseObject(getResponseStr(),Map.class);
        UserInfoStore store=UserInfoStore.getInstance();
        if (!uploadResultMap.containsKey("isSetPortraitSuccessful"))
            return;
        boolean isSetPortraitSuccessful=(boolean)uploadResultMap.get("isSetPortraitSuccessful");
        store.setStoreChangeEvent(
                store.new UploadPortraitEvent(
                        isSetPortraitSuccessful
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
        String datastring=Base64.getEncoder().encodeToString(data);
        return  datastring;
    }
}
