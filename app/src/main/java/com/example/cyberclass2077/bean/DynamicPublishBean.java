package com.example.cyberclass2077.bean;

import android.graphics.Bitmap;

import java.util.Date;

public class DynamicPublishBean {
    private String userName;
    private String content;
    private String date;
    private Integer commentNum;
//    public Bitmap bitmap;

    public void setUserName(String userName){this.userName=userName;}
    public String getUserName(){return userName;}

    public void setContent(String content){this.content=content;}
    public String getContent(){return content;}

    public void setDate(String date){this.date=date;}
    public String getDate(){return date;}

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    //    public void setBitmap(Bitmap bitmap){this.bitmap=bitmap;}
//    public Bitmap getBitmap(){return bitmap;}
}
