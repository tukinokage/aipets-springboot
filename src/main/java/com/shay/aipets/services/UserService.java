package com.shay.aipets.services;

import com.alibaba.fastjson.JSONObject;
import com.example.chaterserver.entity.User;

public interface UserService {
    JSONObject login(String name, String psw);

    JSONObject register(String name, String psw);

    String loginByToken(String token);

    User getUserById(String id);
    User getUserByName(String name);

    boolean isExistUserByName(String name);

}
