package com.shay.aipets.entity.result;


import org.apache.http.util.TextUtils;

import java.util.List;

public class LoadPetPicNameResult extends TestAbsClass<String>{
        private List<String> data;
        private String errorMsg = "";


        public boolean hasError(){
            if (TextUtils.isEmpty(errorMsg)){
                return false;
            }else {
                return true;
            }
        }

        public List<String> getData() {
            return data;
        }

        public void setData(List<String> data) {
            this.data = data;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }
}
