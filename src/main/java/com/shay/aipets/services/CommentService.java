package com.shay.aipets.services;

import com.shay.aipets.dto.Comment;
import com.shay.aipets.entity.UserCommentItem;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CommentService {

    boolean insertComment(String userId, String postId, String contentText, List<String> picList) throws Exception;
    boolean insertCommentPic(String commentId, String picName) throws Exception;
    String uploadCommentPic(MultipartFile file) throws Exception;
    List<Comment> getCommentByPostId(String postId) throws Exception;
    List<UserCommentItem> getCommentByUserId(String userId, int perNum, int currentNum) throws Exception;
}
