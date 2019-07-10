package com.example.cyberclass2077.model;

public class Comment {

    Integer commentId;

    String publisherName;

    Integer dynamicId;

    String content;

    String commentTime;

    String citedCommentId;

    public void setDynamicId(Integer dynamicId) {
        this.dynamicId = dynamicId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCitedCommentId(String citedCommentId) {
        this.citedCommentId = citedCommentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getContent() {
        return content;
    }

    public Integer getDynamicId() {
        return dynamicId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public String getCitedCommentId() {
        return citedCommentId;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public String getPublisherName() {
        return publisherName;
    }
}
