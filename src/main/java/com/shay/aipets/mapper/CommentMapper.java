package com.shay.aipets.mapper;

import com.shay.aipets.dto.Comment;
import com.shay.aipets.dto.CommentPic;
import com.shay.aipets.dto.DataTablePost;
import com.shay.aipets.dto.PostPic;
import com.shay.aipets.entity.BBSPost;
import com.shay.aipets.entity.GetPostConditions;
import com.shay.aipets.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {
    int insert(Comment comment);
    List<Comment> query(Comment comment);
    List<Comment> queryById(@Param("userId") String userId, @Param("startNum") int startNum,@Param("endNum") int endNum );
    List<CommentPic> getPicListByCId(@Param("commentId") String commentId);
    int insertPic(CommentPic commentPic);

}
