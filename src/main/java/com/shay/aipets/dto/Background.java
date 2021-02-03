package com.shay.aipets.dto;

public class Background {
    String userId;
    String bgImgName;

    public Background(String userId, String bgImgName) {
        this.userId = userId;
        this.bgImgName = bgImgName;
    }

    public Background() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBgImgName() {
        return bgImgName;
    }

    public void setBgImgName(String bgImgName) {
        this.bgImgName = bgImgName;
    }
}
