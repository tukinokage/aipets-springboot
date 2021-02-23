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
    List<String> getHeadImgName(HeadImg headImg);
    boolean setHeadImgName(HeadImg headImg);
    boolean insertHeadImgName(HeadImg headImg);
    List<String> getBackGroundName(Background background);
    boolean setBackGroundName(Background background);
    boolean insertBackGroundName(Background background);

    int queryStarPetNum(@Param("userId") String userId, @Param("petId") String petId);
    List<String> queryStarPetId(@Param("userId") String userId, @Param("startNum") int start, @Param("endNum") int end);
    boolean starPet(@Param("userId") String userId, @Param("petId") String petId);
    boolean unStarPet(@Param("userId") String userId, @Param("petId") String petId);
}
