package com.shay.aipets.dto;

public class HeadImg {
    String userId;
    String headImgName;

    public HeadImg() {
    }

    public HeadImg(String userId, String headImgName) {
        this.userId = userId;
        this.headImgName = headImgName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHeadImgName() {
        return headImgName;
    }

    public void setHeadImgName(String headImgName) {
        this.headImgName = headImgName;
    }
}
