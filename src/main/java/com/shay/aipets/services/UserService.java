package com.shay.aipets.services;

import com.alibaba.fastjson.JSONObject;
import com.example.chaterserver.entity.User;
import com.shay.aipets.dto.User;
import com.shay.aipets.entity.responsedata.LoginResponseData;
import com.shay.aipets.entity.responsedata.PhoneReponseData;

public interface UserService {
    LoginResponseData login(String name, String psw) throws Exception;
    LoginResponseData loginByToken(String token) throws Exception;

    User loginByPhoneToken(String phoneToken) throws Exception;
    User regByPhoneToken(String phoneToken) throws Exception;
    boolean isPhoneRg(String phoneToken) throws Exception;
    String getPhoneToken(String phoneNum, String code) throws Exception;

    String sendMsg(String phoneNum) throws Exception;
    User getUserById(String id) throws Exception;
    User getUserByName(String name) throws Exception;

    boolean isExistUserByName(String name) throws Exception;

}
