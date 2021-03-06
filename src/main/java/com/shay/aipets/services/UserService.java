package com.shay.aipets.services;

import com.shay.aipets.dto.Pet;
import com.shay.aipets.dto.User;
import com.shay.aipets.entity.UserInfo;
import com.shay.aipets.entity.responsedata.CheckPhoneRepData;
import com.shay.aipets.entity.responsedata.LoginResponseData;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    LoginResponseData login(String name, String psw) throws Exception;
    String getIdByToken(String token) throws Exception;

    String getPhoneByPhoneToken(String phoneToken) throws Exception;
    CheckPhoneRepData regByPhone(String phonenum) throws Exception;
    CheckPhoneRepData getUserByPhone(String phonenum) throws Exception;
    boolean isPhoneRg(String phoneToken) throws Exception;
    String getPhoneToken(String phoneNum, String code) throws Exception;
    boolean checkToken(String userId, String token) throws Exception;

    void sendMsg(String phoneNum) throws Exception;
    User getUserById(String id) throws Exception;
    UserInfo getUserInfoById(String id) throws Exception;
    User getUserByName(String name) throws Exception;

    boolean isExistUserByName(String name) throws Exception;
    boolean updateInfoById(User updaterInfo) throws Exception;

    boolean updateHeadImg(String userIdString, String headImgName) throws Exception;
    String uploadHeadImg(MultipartFile file, String userId) throws Exception;
    boolean updateBgImg(String userIdString, String bgImgName) throws Exception;
    String uploadBgImg(MultipartFile file, String userId) throws Exception;

    String getHeadImgName(String userId) throws Exception;
    String getBgImgName(String userId) throws Exception;

    boolean isStarPet(String petId, String userId) throws Exception;
    boolean starPet(String petId, String userId) throws Exception;
    boolean unStarPet(String petId, String userId) throws Exception;
    List<Pet> getStartPet(String userId, int perPagerNum, int currentPagerNum) throws Exception;

}
