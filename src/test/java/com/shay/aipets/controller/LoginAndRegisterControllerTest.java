package com.shay.aipets.controller;

import com.shay.aipets.AipetsApplication;
import com.shay.aipets.entity.params.CheckPhExistParam;
import com.shay.aipets.entity.response.BaseResponse;
import com.shay.aipets.entity.responsedata.CheckPhoneRepData;
import com.shay.aipets.services.UserService;
import javafx.application.Application;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {AipetsApplication.class})
class LoginAndRegisterControllerTest {

    @Autowired
    UserService userService;

    @Autowired
    LoginAndRegisterController controller;

    @Test
    public void testCheckPhone(){
        CheckPhExistParam checkPhExistParam = new CheckPhExistParam();
        checkPhExistParam.setPhoneToken("47ca2c4f554046f");
        BaseResponse<CheckPhoneRepData> checkPhoneRepDataBaseResponse = controller.checkPhone(checkPhExistParam);
        System.out.println(checkPhoneRepDataBaseResponse.getData().getUserName());
    }

}