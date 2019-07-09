package com.example.cyberclass2077.bean;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Date;

public class DynamicPublishBean implements Serializable {
    private Integer dynamicId; //动态ID
    private String userName; //用户名
    private String content; //文本内容
    private String date;    //日期
    private Integer commentNum; //评论数
    private Integer likeNum;    //点赞数


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
