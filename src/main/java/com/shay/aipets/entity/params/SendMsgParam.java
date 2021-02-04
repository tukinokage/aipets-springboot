package com.shay.aipets.entity.params;

import java.io.Serializable;

public class SendMsgParam implements Serializable {
    String phoneNum;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
