package com.shay.aipets.mapper;

import com.shay.aipets.dto.DataTablePost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PostMapper {
    int insert(DataTablePost dataTablePost);
    int queryNum(DataTablePost dataTablePost);
    List<DataTablePost> query(DataTablePost dataTablePost);
    List<DataTablePost> queryByCondition(@Param("condition") String condition);

    //图片
    List<String> getPostPic(@Param("postId") String postId);

}
