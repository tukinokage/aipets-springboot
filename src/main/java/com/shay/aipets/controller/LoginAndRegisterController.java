package com.shay.aipets.controller;

import com.shay.aipets.dto.User;
import com.shay.aipets.entity.params.CheckPhExistParam;
import com.shay.aipets.entity.params.LoginParam;
import com.shay.aipets.entity.params.SetPwRequestParam;
import com.shay.aipets.entity.params.UpdateUserInfoParam;
import com.shay.aipets.entity.response.BaseResponse;
import com.shay.aipets.entity.responsedata.CheckPhoneRepData;
import com.shay.aipets.entity.responsedata.LoginResponseData;
import com.shay.aipets.entity.responsedata.PhoneReponseData;
import com.shay.aipets.entity.responsedata.SetPwResponseData;
import com.shay.aipets.entity.responses.UpdateUserInfoResponse;
import com.shay.aipets.myexceptions.MyException;
import com.shay.aipets.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


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

        if(loginParam == null ){
            response.setErrorMsg("服务没有请求数据");
            return response;
        }

            try {
                if (loginParam.getToken() == null){
                    loginResponseData = userService.login(loginParam.getName(), loginParam.getPassword());
                    response.setData(loginResponseData);
                }else {
                    String userId = userService.getIdByToken(loginResponseData.getToken());
                    User userById = userService.getUserById(userId);
                    loginResponseData.setUserId(userId);
                    loginResponseData.setToken(loginResponseData.getToken());
                    loginResponseData.setUserName(userById.getUserName());
                    response.setData(loginResponseData);
                }
            }catch (MyException e){
                response.setErrorMsg(e.getMessage());
            }
            catch (Exception e){
                e.printStackTrace();
                response.setErrorMsg("服务器端处理出错");
            }finally {
                return response;
            }

    }


    /**修改信息
     *
     * @return  UpdateUserInfoResponse;
     *   通过token检查缓存中id是否与请求id相符
     *
    * */
    @ResponseBody
    @RequestMapping(value = "/updateInfo")
    public BaseResponse<UpdateUserInfoResponse> updateUserInfo(UpdateUserInfoParam updateUserInfoParam){
        BaseResponse<UpdateUserInfoResponse> response = new BaseResponse<>();
        UpdateUserInfoResponse updateUserInfoResponse = new UpdateUserInfoResponse();
        try {
           String userId = userService.getIdByToken(updateUserInfoParam.getUserToken());
           if(userId.equals(updateUserInfoParam.getUserId())){
               User user = new User();
               user.setUserId(userId);
              // user.setSex(updateUserInfoParam.get);
               user.setUserName(updateUserInfoParam.getUserName());
               user.setSign(updateUserInfoParam.getSign());
               boolean b = userService.updateInfoById(user);
               if(!b) {
                   throw new MyException("修改失败");
               }
           }else {
               throw new MyException("非法操作");
           }
        }catch (MyException e){
            response.setErrorMsg(e.getMessage());
        }catch (Exception e){
            response.setErrorMsg("服务器出错");
        }finally {
            return response;
        }
    }

    /**修改pw
     *
     * @return  UpdateUserInfoResponse;
     *   通过token检查缓存中id是否与请求id相符
     *
    * */
    @ResponseBody
    @RequestMapping(value = "/updatePassword")
    public BaseResponse<SetPwResponseData> updatePassword(SetPwRequestParam setPwRequestParam){
        BaseResponse<SetPwResponseData> response = new BaseResponse<>();
        SetPwResponseData setPwResponseData = new SetPwResponseData();
        response.setData(setPwResponseData);
        try {
            String phoneToken = setPwRequestParam.getPhoneToken();
            String phone = userService.getPhoneByPhoneToken(phoneToken);
           if(userService.isPhoneRg(phone)){
               User user = new User();
               user.setPassWord(setPwRequestParam.getPassword());
               user.setUserId(setPwRequestParam.getUserId());
               boolean b = userService.updateInfoById(user);
               if(!b) {
                   throw new MyException("修改失败");
               }
           }else {
               throw new MyException("非法操作");
           }
        }catch (MyException e){
            response.setErrorMsg(e.getMessage());
        }catch (Exception e){
            response.setErrorMsg("服务器出错");
        }finally {
            return response;
        }
    }

    /**修改信息
     *
     * @return  UpdateUserInfoResponse;
     *   通过token检查缓存中id是否与请求id相符
     *
    * */
    @ResponseBody
    @RequestMapping(value = "/loginByPhone")
    public BaseResponse<CheckPhoneRepData> loginByPhone(CheckPhExistParam checkPhExistParam){
        BaseResponse<CheckPhoneRepData> response = new BaseResponse<>();
        CheckPhoneRepData checkPhoneRepData = new CheckPhoneRepData();
        try {
          String phone = userService.getPhoneByPhoneToken(checkPhExistParam.getPhoneToken());
           if(userService.isPhoneRg(checkPhExistParam.getPhoneToken())){
               checkPhoneRepData = userService.getUserByPhone(phone);
           }else {
              checkPhoneRepData = userService.regByPhone(phone);
           }
        }catch (MyException e){
            response.setErrorMsg(e.getMessage());
        }catch (Exception e){
            response.setErrorMsg("服务器出错");
        }finally {
            response.setData(checkPhoneRepData);
            return response;
        }
    }


}
