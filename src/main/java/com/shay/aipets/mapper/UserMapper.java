package com.shay.aipets.mapper;

import com.shay.aipets.dto.Background;
import com.shay.aipets.dto.HeadImg;
import com.shay.aipets.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    int insert(User user);
    int queryNum(User user);
    List<User> query(User user);
    boolean update(User user);

    //图片
    String getHeadImgName(HeadImg headImg);
    boolean setHeadImgName(HeadImg headImg);
    String getBackGroundName(Background background);
    boolean setBackGroundName(Background background);
}
