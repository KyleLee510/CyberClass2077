package com.example.cyberclass2077.bean;

import com.example.cyberclass2077.views.Dynamic.DynamicPublish;

public class DynamicBean {
    private Integer img_head;
    private String str_user_name; //用户名
    private String str_describe;   //
    private Integer img_dis;
    private Integer int_amount_favorite;
    private String str_first_comemnt;
    private Integer int_amount_comment;
    private String str_time;
    private boolean isLike;


    public  DynamicBean(){
        int_amount_favorite=20;
        isLike=false;
        str_time="2019-7-9 10:21";
        int_amount_comment=10;
    }
    public Integer getImg_head() {
        return img_head;
    }
    public void setImg_head(Integer img_head) {
        this.img_head = img_head;
    }

    public String getStr_user_name() {
        return str_user_name;
    }
    public void setStr_user_name(String str_user_name) {
        this.str_user_name = str_user_name;
    }

    public String getStr_describe() {
        return str_describe;
    }
    public void setStr_describe(String str_describe) {
        this.str_describe = str_describe;
    }

    public Integer getImg_dis() {
        return img_dis;
    }
    public void setImg_dis(Integer img_dis) {
        this.img_dis = img_dis;
    }

    public Integer getInt_amount_favorite() {
        return int_amount_favorite;
    }
    public void setInt_amount_favorite(Integer int_amount_favorite) {
        this.int_amount_favorite = int_amount_favorite;
    }

    public String getStr_first_comemnt() {
        return str_first_comemnt;
    }
    public void setStr_first_comemnt(String str_first_comemnt) {
        this.str_first_comemnt = str_first_comemnt;
    }

    public String getStr_time() {
        return str_time;
    }
    public void setStr_time(String str_time) {
        this.str_time = str_time;
    }

    public boolean getIsLike() {
        return isLike;
    }
    public void setIsLike(boolean isLike) {
        this.isLike = isLike;
    }

    public Integer getInt_amount_comment() {
        return int_amount_comment;
    }
    public void setInt_amount_comment(Integer int_amount_comment) {
        this.int_amount_comment = int_amount_comment;
    }
}
