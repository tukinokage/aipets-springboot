package com.shay.aipets.entity.responses;

import com.shay.aipets.entity.UserInfo;

public class GetUserInfoResponse {
    private UserInfo userInfo;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
