package com.shay.aipets.entity.params;


import com.shay.aipets.entity.Picture;

import java.util.List;

public class PostParam {
    String token;
    String userId;
    String contentText;
    String title;
    List<Picture> picList;
    String type;

    public List<Picture> getPicList() {
        return picList;
    }

    public void setPicList(List<Picture> picList) {
        this.picList = picList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
