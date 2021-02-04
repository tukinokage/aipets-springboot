package com.shay.aipets.controller;


import com.shay.aipets.dto.User;
import com.shay.aipets.entity.params.ConfrimPhoneNumParam;
import com.shay.aipets.entity.params.LoginParam;
import com.shay.aipets.entity.params.SendMsgParam;
import com.shay.aipets.entity.response.BaseResponse;
import com.shay.aipets.entity.responsedata.LoginResponseData;
import com.shay.aipets.entity.responsedata.PhoneReponseData;
import com.shay.aipets.entity.responsedata.SmsResponse;
import com.shay.aipets.myexceptions.MyException;
import com.shay.aipets.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/phone")
public class PhoneController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/sendMsg")
    @ResponseBody
    /**
     *
     * */
    public BaseResponse<SmsResponse> sendMsg(SendMsgParam sendMsgParam){
        System.out.println(sendMsgParam.getPhoneNum());
        BaseResponse<SmsResponse> response = new BaseResponse<>();

        if(sendMsgParam == null ){
            response.setErrorMsg("服务没有收到请求数据");
            return response;
        }

        try {
            String phone = sendMsgParam.getPhoneNum();
            userService.sendMsg(phone);
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


    @RequestMapping(value = "/commitPhoneCode")
    @ResponseBody
    /**
     *
     * */
    public BaseResponse<PhoneReponseData> commitPhone(ConfrimPhoneNumParam confrimPhoneNumParam){
        System.out.println(confrimPhoneNumParam.getPhoneNum());
        BaseResponse<PhoneReponseData> response = new BaseResponse<>();
        PhoneReponseData phoneReponseData = new PhoneReponseData();

        if(confrimPhoneNumParam == null){
            response.setErrorMsg("服务没有收到请求数据");
            return response;
        }

        try {
            String phone = confrimPhoneNumParam.getPhoneNum();
            String code = confrimPhoneNumParam.getCode();
            String phoneToken = userService.getPhoneToken(phone, code);
            phoneReponseData.setPhoneToken(phoneToken);
            System.out.println(phoneToken);
        }catch (MyException e){
            response.setErrorMsg(e.getMessage());
        }
        catch (Exception e){
            e.printStackTrace();
            response.setErrorMsg("服务器端处理出错");
        }finally {
            response.setData(phoneReponseData);
            return response;
        }

    }



}
