package com.shay.aipets.entity;

import java.io.Serializable;

public class TestUser implements Serializable {
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTestP() {
        return TestP;
    }

    public void setTestP(String testP) {
        TestP = testP;
    }

    private String TestP;
}
