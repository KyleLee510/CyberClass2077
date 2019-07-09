package com.example.cyberclass2077.bean;

import android.graphics.Bitmap;

import java.util.Date;

public class DynamicPublishBean {
    private Integer dynamicId;
    private String userName;
    private String content;
    private String date;
    private Integer commentNum;
    private Integer likeNum;
//    public Bitmap bitmap;

    public void setUserName(String userName){this.userName=userName;}
    public String getUserName(){return userName;}

    public void setContent(String content){this.content=content;}
    public String getContent(){return content;}

    public void setDate(String date){this.date=date;}
    public String getDate(){return date;}

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getCommentNum() {
        return commentNum;
    }


    public Integer getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(Integer dynamicId) {
        this.dynamicId = dynamicId;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }
}
