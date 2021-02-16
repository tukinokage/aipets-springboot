package com.shay.aipets.mapper;

import com.shay.aipets.dto.DataTablePost;
import com.shay.aipets.dto.PostPic;
import com.shay.aipets.entity.BBSPost;
import com.shay.aipets.entity.GetPostConditions;
import com.shay.aipets.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PostMapper {
    int insert(DataTablePost dataTablePost);
    List<BBSPost> query(GetPostConditions conditions);
    List<Post> getPostListByPId(@Param("postList")String postId);

    //图片
    List<String> getPostPic(@Param("postId") String postId);
    boolean savePostPic(PostPic postPic);

}
