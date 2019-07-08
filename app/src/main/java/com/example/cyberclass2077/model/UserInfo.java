package com.example.cyberclass2077.model;

public class UserInfo {

    private String userName;
    private String nickName;
    private String gender;
    private String birthDate;
    private Integer followeeNum;
    private Integer followerNum;
    private String lastCheckinDate;
    private boolean isTodayCheckin=false;
    private Integer checkinTotalDays=0;

    public UserInfo(){}

    public String getUserName() {
        return userName;
    }

    public String getNickName() {
        return nickName;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public Integer getFolloweeNum() {
        return followeeNum;
    }

    public Integer getFollowerNum() {
        return followerNum;
    }
    public String getLastCheckinDate() {
        return lastCheckinDate;
    }

    public boolean isTodayCheckin() {
        return isTodayCheckin;
    }
    public Integer getCheckinTotalDays() {
        return checkinTotalDays;
    }

    public void setCheckinTotalDays(Integer checkinTotalDays) {
        this.checkinTotalDays = checkinTotalDays;
    }

    public void setLastCheckinDate(String lastCheckinDate) {
        this.lastCheckinDate = lastCheckinDate;
    }

    public void setTodayCheckin(boolean todayCheckin) {
        isTodayCheckin = todayCheckin;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }


    public void setFolloweeNum(Integer followeeNum) {
        this.followeeNum = followeeNum;
    }

    public void setFollowerNum(Integer followerNum) {
        this.followerNum = followerNum;
    }
    public UserInfo getUserInfo(){
        return  this;
    }
    public void setUserInfo(UserInfo userInfo){
        if(userInfo!=null){
            this.userName=userInfo.getUserName();
            this.nickName=userInfo.getNickName();
            this.gender=userInfo.getGender();
            this.birthDate=userInfo.getBirthDate();
            this.followeeNum=userInfo.getFolloweeNum();
            this.followerNum=userInfo.getFollowerNum();

        }
    }
}
