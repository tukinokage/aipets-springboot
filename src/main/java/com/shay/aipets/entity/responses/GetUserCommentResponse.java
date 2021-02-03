package com.shay.aipets.entity.responses;

import com.shay.aipets.entity.UserCommentItem;

import java.util.List;

public class GetUserCommentResponse {

    private List<UserCommentItem> commentItemList;
    boolean hasMore;
    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }
    public List<UserCommentItem> getCommentItemList() {
        return commentItemList;
    }

    public void setCommentItemList(List<UserCommentItem> commentItemList) {
        this.commentItemList = commentItemList;
    }
}
