package com.shay.aipets.controller;

import com.shay.aipets.dto.User;
import com.shay.aipets.entity.response.BaseResponse;
import com.shay.aipets.entity.responsedata.LoginResponseData;
import com.shay.aipets.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ls")
public class LoginAndRegisterController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login")
    @ResponseBody
    public BaseResponse<LoginResponseData> Login(@RequestParam(value = "name")String name, @RequestParam(value = "password") String password){
        System.out.println(name + password);
        BaseResponse<LoginResponseData> response = new BaseResponse<>();
        //JSONObject jsonObject = (JSONObject) JSONObject.parse(json);

        try {


        }catch (Exception e){
            e.printStackTrace();
            response.setErrorMsg("服务器端处理出错");
        }

        //return new Gson().toJson(response);
        return response;
    }


    /**@return username ""则失效;
     * erroMsg为""、userName不为""为登陆成功
     *
    * */
        @RequestMapping(value = "/loginToken")
        @ResponseBody
        public String Register(@RequestBody String json){
            System.out.println(json);
            JSONObject jsonObject = (JSONObject) JSONObject.parse(json);
            JSONObject resultJSONObject = new JSONObject();
            resultJSONObject.put("errorMSg", "");
            resultJSONObject.put("username", "");

            String tokenStr =  jsonObject.getString("token");
            String userName = userService.loginByToken(tokenStr);
            if("".equals(userName) || userName == null){
                resultJSONObject.put("errorMSg", "登陆过期，请重新登陆");
            }else {
                resultJSONObject.put("username", userName);
            }

            System.out.println("userName:" + userName);
            return resultJSONObject.toJSONString();
    }

}
