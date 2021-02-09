package com.shay.aipets.entity.response;


import com.shay.aipets.dto.Comment;

import java.util.List;

public class GetCommentResponse {
    List<Comment> commentsList;

    public List<Comment> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comment> commentsList) {
        this.commentsList = commentsList;
    }
}
