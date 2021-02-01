package com.shay.aipets.services;

import com.shay.aipets.dto.User;
import com.shay.aipets.entity.responsedata.LoginResponseData;

public interface UserService {
    LoginResponseData login(String name, String psw) throws Exception;
    String getIdByToken(String token) throws Exception;

    String getPhoneByPhoneToken(String phoneToken) throws Exception;
    User regByPhone(String phonenum) throws Exception;
    boolean isPhoneRg(String phoneToken) throws Exception;
    String getPhoneToken(String phoneNum, String code) throws Exception;

    void sendMsg(String phoneNum) throws Exception;
    User getUserById(String id) throws Exception;
    User getUserByName(String name) throws Exception;

    boolean isExistUserByName(String name) throws Exception;
    boolean updateInfoById(User updaterInfo) throws Exception;

    boolean updateHeadImg(String userIdString, String headImgName) throws Exception;
    boolean updateBgImg(String userIdString, String bgImgName) throws Exception;

    String getHeadImgName(String userId) throws Exception;
    String getBgImgName(String userId) throws Exception;

}
