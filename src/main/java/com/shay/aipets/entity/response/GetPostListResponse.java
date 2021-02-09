package com.shay.aipets.entity.response;


import com.shay.aipets.entity.BBSPost;

import java.util.List;

public class GetPostListResponse {
    List<BBSPost> bbsPostList;


    public List<BBSPost> getBbsPostList() {
        return bbsPostList;
    }

    public void setBbsPostList(List<BBSPost> bbsPostList) {
        this.bbsPostList = bbsPostList;
    }

    boolean hasMore;
    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

}
