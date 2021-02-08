package com.shay.aipets.entity.result;


import com.shay.aipets.entity.Store;
import org.apache.http.util.TextUtils;

import java.util.List;

public class LoadStoreResult {
    private List<Store> data;
    private String errorMsg = "";

    public boolean hasError(){
        if (TextUtils.isEmpty(errorMsg)){
            return false;
        }else {
            return true;
        }
    }

    public List<Store> getData() {
        return data;
    }

    public void setData(List<Store> data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
