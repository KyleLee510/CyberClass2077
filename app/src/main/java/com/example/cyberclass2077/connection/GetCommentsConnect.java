package com.example.cyberclass2077.connection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.alibaba.fastjson.JSON;
import com.example.cyberclass2077.config.AppConfig;
import com.example.cyberclass2077.model.Comment;
import com.example.cyberclass2077.stores.CommentStore;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class GetCommentsConnect extends Connect<Integer> {
    public GetCommentsConnect(){}

    public void sendGetCommentsConnect(Integer dynamicId){
        postData(
                AppConfig.COMMENT_GET,
                requestBodyBuilder("dynamicId",dynamicId)
        );
    }
    @Override
    public void emitRequestResult(){
        Map resultMap = JSON.parseObject(getResponseStr(),Map.class);
        if(!resultMap.containsKey("isGetCommentsSuccessful"))
            return;
        boolean isGetCommentsSuccessful=(boolean)resultMap.get("isGetCommentsSuccessful");
        String commentListStr=(String)resultMap.get("main_comment_list");
        List<Comment> commentList=JSON.parseArray(commentListStr,Comment.class);
        String portraitsStr=(String)resultMap.get("portraitsList");
        List<String> comment_portraits_str=JSON.parseArray(portraitsStr,String.class);
        List<Bitmap> portraitsList=new ArrayList<Bitmap>();
        for (String portraitStr:comment_portraits_str
        ) {
            portraitsList.add(stringToBitmap(portraitStr));

        }

        CommentStore store=CommentStore.getInstance();
        store.setStoreChangeEvent(
                store.new GetCommentsEvent(
                        isGetCommentsSuccessful,
                        commentList,
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
