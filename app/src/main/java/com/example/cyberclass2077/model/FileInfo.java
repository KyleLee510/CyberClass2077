package com.example.cyberclass2077.model;

public class FileInfo {
    Integer fileId;//文件ID
    String fileUrl;
    String fileType;
    Integer commentNum;
    String uploadUserName;//视频上传者名称
    String uploadTime;//视频上传时间
    String examineAdminName;
    String examineTime;
    String examineResult;
    Integer likeNum;
    String fileTitle;//视频标题
    String tag;//视频tag

    public String getUploadUserName() {
        return uploadUserName;
    }

    public Integer getFileId() {
        return fileId;
    }

    public Integer getCommentNum() {
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

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }


    public void setUploadUserName(String uploadUserName) {
        this.uploadUserName = uploadUserName;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
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
