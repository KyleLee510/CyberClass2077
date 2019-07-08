package com.example.cyberclass2077.model;

public class FileInfo {
    Integer fileId;
    String fileUrl;
    String fileType;
    Integer commentNum;
    String uploadUserName;
    String uploadTime;
    String examineAdminName;
    String examineTime;
    String examineResult;
    Integer likeNum;
    String fileTitle;

    public String getUploadUserName() {
        return uploadUserName;
    }

    public Integer getFileId() {
        return fileId;
    }

    public Integer getCommmentNum() {
        return commentNum;
    }

    public String getExamineAdminName() {
        return examineAdminName;
    }

    public String getFileType() {
        return fileType;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public String getExamineResult() {
        return examineResult;
    }

    public String getExamineTime() {
        return examineTime;
    }

    public String getFileTitle() {
        return fileTitle;
    }

    public void setUploadUserName(String uploadUserName) {
        this.uploadUserName = uploadUserName;
    }

    public void setCommmentNum(Integer commmentNum) {
        this.commentNum = commmentNum;
    }

    public void setExamineAdminName(String examineAdminName) {
        this.examineAdminName = examineAdminName;
    }

    public void setExamineTime(String examineTime) {
        this.examineTime = examineTime;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void setExamineResult(String examineResult) {
        this.examineResult = examineResult;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public void setFileTitle(String fileTitle) {
        this.fileTitle = fileTitle;
    }
}
