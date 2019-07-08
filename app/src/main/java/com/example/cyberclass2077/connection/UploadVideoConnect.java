package com.example.cyberclass2077.connection;

import com.alibaba.fastjson.JSON;
import com.example.cyberclass2077.config.AppConfig;
import com.example.cyberclass2077.stores.FileInfoStore;
import com.example.cyberclass2077.stores.UserStore;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

public class UploadVideoConnect extends Connect<File> {
    public UploadVideoConnect(){}
    public void sendUploadVideoRequest(Map map){
        File videoFile=(File)map.get("file");
        String fileTitle=(String)map.get("title");
        String userName= UserStore.getInstance().getUser().getUserName();
        String videoString=videoFile2String(videoFile);
        if (videoString.equals("")){
            return;
        }
        RequestBody requestBody = new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addFormDataPart("username",userName)
                .addFormDataPart("filetitle",fileTitle)
                .addFormDataPart("videostr",videoString)
                .build();
        postMulitiData(
                AppConfig.FILE_UPLOAD_VIDEO,
                requestBody
        );


    }
    @Override
    public void emitRequestResult(){
        Map uploadResultMap = JSON.parseObject(getResponseStr(),Map.class);
        if(!uploadResultMap.containsKey("isUploadFileSuccessful")){
            return;
        }
        boolean isUploadFileSuccessful=(boolean)uploadResultMap.get("isUploadFileSuccessful");
        FileInfoStore store=FileInfoStore.getInstance();
        store.setStoreChangeEvent(
                store.new UploadVideoEvent(
                        isUploadFileSuccessful
                )
        );
        store.emitStoreChange();
    }
    private String videoFile2String(File videoFile){
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream((int) videoFile.length());
            BufferedInputStream in = null;
            in = new BufferedInputStream(new FileInputStream(videoFile));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            String videoString = java.util.Base64.getEncoder().encodeToString(bos.toByteArray());
            in.close();
            bos.close();
            return videoString;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
