package com.shay.aipets.entity.params;

public class GetUserPetParam {
    String userId;
    String perPagerCount;
    String currentPager;

    public String getPerPagerCount() {
        return perPagerCount;
    }

    public void setPerPagerCount(String perPagerCount) {
        this.perPagerCount = perPagerCount;
    }

    public String getCurrentPager() {
        return currentPager;
    }

    public void setCurrentPager(String currentPager) {
        this.currentPager = currentPager;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
