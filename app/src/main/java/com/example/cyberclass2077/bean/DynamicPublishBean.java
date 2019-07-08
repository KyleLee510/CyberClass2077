package com.example.cyberclass2077.bean;

import android.graphics.Bitmap;

import java.util.Date;

public class DynamicPublishBean {
    public String userName;
    public String content;
    public Date date;
//    public Bitmap bitmap;

    public void setUserName(String userName){this.userName=userName;}
    public String getUserName(){return userName;}

    public void setContent(String content){this.content=content;}
    public String getContent(){return content;}

    public void setDate(Date date){this.date=date;}
    public Date getDate(){return date;}

//    public void setBitmap(Bitmap bitmap){this.bitmap=bitmap;}
//    public Bitmap getBitmap(){return bitmap;}
}
