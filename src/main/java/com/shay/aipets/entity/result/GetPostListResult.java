package com.shay.aipets.entity.result;


import com.shay.aipets.entity.BBSPost;

import java.util.List;

public class GetPostListResult {
    List<BBSPost> bbsPostList;
    boolean hasMore;
    String errorMg = "";

    public List<BBSPost> getBbsPostList() {
        return bbsPostList;
    }

    public void setBbsPostList(List<BBSPost> bbsPostList) {
        this.bbsPostList = bbsPostList;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }
    public String getErrorMg() {
        return errorMg;
    }

    public void setErrorMg(String errorMg) {
        this.errorMg = errorMg;
    }
}
