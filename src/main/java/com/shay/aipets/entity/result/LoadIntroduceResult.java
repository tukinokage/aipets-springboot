package com.shay.aipets.entity.result;


import com.shay.aipets.entity.PetIntroduce;
import org.apache.http.util.TextUtils;

import java.util.List;

public class LoadIntroduceResult {
        private PetIntroduce data;
        private String errorMsg = "";


    public boolean hasError(){
        if (TextUtils.isEmpty(errorMsg)){
            return false;
        }else {
            return true;
        }
    }
        public PetIntroduce getData() {
            return data;
        }

        public void setData(PetIntroduce data) {
            this.data = data;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }
}
