package com.shay.aipets.entity.result;


import com.shay.aipets.entity.Post;

public class GetPostInfoResult {
    Post post;
    String errorMsg = "";

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
