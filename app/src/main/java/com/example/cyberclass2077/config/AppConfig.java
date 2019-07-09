package com.example.cyberclass2077.config;

public class AppConfig {
    public final static String BASE_URL_PATH = "http://47.100.99.130:8089";
    public final static String LOGIN=BASE_URL_PATH.concat("/login");
    public final static String SIGNUP_NAME_CHECK=BASE_URL_PATH.concat("/signup_name_check");
    public final static String SIGNUP=BASE_URL_PATH.concat("/signup");
    public final static String SETTINGS_LOGOUT=BASE_URL_PATH.concat("/logout");
    public final static String SETTINGS_UPDATE_PASSWORD =BASE_URL_PATH.concat("/update_password");
    public final static String SETTINGS_UPDATE_USERINFO=BASE_URL_PATH.concat("/update_userinfo");
    public final static String SETTINGS_GET_USERINFO=BASE_URL_PATH.concat("/get_userinfo");
    public final static String SETTINGS_UPLOAD_PICTURE_PORTRAIT=BASE_URL_PATH.concat("/set_portrait_picture");
    public final static String SETTINGS_GET_PICTURE_PORTRAIT=BASE_URL_PATH.concat("/get_portrait_picture");
    public final static String FILE_UPLOAD_VIDEO=BASE_URL_PATH.concat("/upload_video");
    public final static String DYNAMIC_SEND=BASE_URL_PATH.concat("/send_dynamic");

}
