package com.shay.aipets.mapper;


import com.example.chaterserver.entity.User;
import com.shay.aipets.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    int insert(User user);

    int queryNum(User user);
    List<User> query(User user);
}
