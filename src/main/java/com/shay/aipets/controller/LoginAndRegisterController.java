package com.shay.aipets.controller;


import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.shay.aipets.entity.params.LoginParam;
import com.shay.aipets.entity.response.BaseResponse;
import com.shay.aipets.entity.responsedata.LoginResponseData;
import com.shay.aipets.myexceptions.MyException;
import com.shay.aipets.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/usr")
public class LoginAndRegisterController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login")
    @ResponseBody
    /**
     *
     * */
    public BaseResponse<LoginResponseData> Login(LoginParam loginParam){
        System.out.println(loginParam.getName());
        BaseResponse<LoginResponseData> response = new BaseResponse<>();
        LoginResponseData loginResponseData = new LoginResponseData();
        response.setData(loginResponseData);
        if(loginParam == null ){
            response.setErrorMsg("服务没有请求数据");
            return response;
        }

        if (loginParam.getToken() == null){
            try {
                // String jsonString = JSONObject.toJSONString(params);
                loginResponseData.setToken();
            }catch (MyException e){
                response.setErrorMsg(e.getMessage());
            }
            catch (Exception e){
                e.printStackTrace();
                response.setErrorMsg("服务器端处理出错");
            }
        }else {

        }

        //JSONObject jsonObject = (JSONObject) JSONObject.parse(json);


        return response;
    }


    /**@return ;
     * erroMsg为""、userName不为""为登陆成功
     *
    * */
        @RequestMapping(value = "/loginToken")
        @ResponseBody
        public BaseResponse<LoginResponseData> Register(@RequestParam String json){
            System.out.println(json);

            BaseResponse<LoginResponseData> response = new BaseResponse<>();
            LoginResponseData loginResponseData = new LoginResponseData();
            response.setData(loginResponseData);


            if("".equals(userName) || userName == null){
                resultJSONObject.put("errorMSg", "登陆过期，请重新登陆");
            }else {
                resultJSONObject.put("username", userName);
            }

            System.out.println("userName:" + userName);
            return resultJSONObject.toJSONString();
    }

}
