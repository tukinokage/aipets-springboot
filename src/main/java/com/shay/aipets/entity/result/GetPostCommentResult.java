package com.shay.aipets.entity.result;

import com.shay.aipets.dto.Comment;

import java.util.List;

public class GetPostCommentResult {
    List<Comment> commentList;
    String errorMsg = "";

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }



    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
