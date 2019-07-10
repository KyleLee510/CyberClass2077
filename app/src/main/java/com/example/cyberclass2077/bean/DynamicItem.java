package com.example.cyberclass2077.bean;


import android.graphics.Bitmap;


public class DynamicItem {
    public Bitmap bit_user_portrait;  //头像
    public String str_user_name; //用户名
    public String str_describe;   //描述内容
    public Bitmap img_dis;  //图片
    //public Integer int_amount_favorite;  //喜欢的总数
    //public String str_first_comemnt;  //第一条评论
    //public Integer int_amount_comment;
    public String str_time;
    //public boolean isLike;
    public int int_dynamic;
    public  DynamicItem(DynamicPublishBean dynamicPublishBean, Bitmap bitmap){
        bit_user_portrait = bitmap;
        str_user_name = dynamicPublishBean.getUserName();
        str_describe = dynamicPublishBean.getContent();
        str_time = dynamicPublishBean.getDate();
        int_dynamic = dynamicPublishBean.getDynamicId();
    }
}

