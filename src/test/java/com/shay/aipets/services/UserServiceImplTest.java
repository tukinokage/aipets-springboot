package com.shay.aipets.services;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserService userService;
    @Test
    void getIdByToken() throws Exception {
        System.out.println(userService.getIdByToken("d8f448ee406a4a4dae19f231a222b18e"));
    }
}