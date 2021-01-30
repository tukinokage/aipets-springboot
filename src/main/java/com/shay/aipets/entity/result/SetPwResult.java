package com.shay.aipets.entity.result;

import java.io.Serializable;

public class SetPwResult implements Serializable {
    private String errorMsg;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
