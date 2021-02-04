package com.shay.aipets.mapper;

import com.shay.aipets.AipetsApplication;
import com.shay.aipets.dto.User;
import javafx.application.Application;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {AipetsApplication.class})
class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    void query() {
        User user = new User();
        user.setUserId("1234");
        List<User> query = userMapper.query(user);
        System.out.println(query.size());
    }
}