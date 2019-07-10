package com.example.cyberclass2077.bean;

public class CourseBean {
     Integer CourseID;        //课程ID
      String videoTitle;      //视频标题
     String userNickName;     //视频上传者名称
     String uploadTime;              //视频上传时间
    public  String tag;                     //视频tag

     String videoURL;                   //视频URL
     boolean isFavorite;                //是否已收藏
    public CourseBean(){};

    public CourseBean(Integer courseID,String videoTitle,String userNickName,String uploadTime,String tag,String videoURL,boolean isFavorite){
        this.CourseID =courseID;
        this.videoTitle =videoTitle;
        this.userNickName =userNickName;
        this.uploadTime = uploadTime;
        this.tag = tag;
        this.videoURL =videoURL;
        this.isFavorite =isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public void setCourseID(Integer courseID) {
        CourseID = courseID;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public Integer getCourseID() {
        return CourseID;
    }

    public String getTag() {
        return tag;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public String getVideoURL() {
        return videoURL;
    }
    public boolean getfavrot(){
        return isFavorite;
    }
}
